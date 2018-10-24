package com.bp.alps.core.abstractOrder;

import com.bp.alps.core.constants.AlpssalecoreConstants;
import com.bp.alps.core.model.LicensePlateInfoModel;
import com.bp.alps.core.model.QuotationInfoModel;
import com.bp.alps.core.model.SalesOrderAttributeModel;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.order.strategies.calculation.OrderRequiresCalculationStrategy;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.DiscountValue;
import de.hybris.platform.util.TaxValue;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.*;


public class AbstractOrderCalculationService extends DefaultCalculationService
{
	private static final String ALPS_PARTS_CATEGORIES_STRING = Config
			.getString(AlpssalecoreConstants.Product.ALPS_PARTS_CATEGORIES_KEY,"");

	private static final String ALPS_DECOR_CATEGORIES_STRING = Config.getString(AlpssalecoreConstants.Product.ALPS_DECOR_CATEGORIES_KEY,"");

	private static final String ALPS_INSURANCE_CATEGORIES_STRING = Config.getString(AlpssalecoreConstants.Product.ALPS_INSURANCE_CATEGORIES_KEY,"");

	private static final String ALPS_EXTENDED_WARRANTY_CATEGORIES_STRING = Config.getString(AlpssalecoreConstants.Product.ALPS_EXTENDED_WARRANTY_CATEGORIES_KEY,"");

	private static final String ALPS_VOUCHER_CATEGORIES_STRING = Config.getString(AlpssalecoreConstants.Product.ALPS_VOUCHER_CATEGORIES_KEY,"");

	private OrderRequiresCalculationStrategy orderRequiresCalculationStrategy;

	private CommonI18NService commonI18NService;

	@Resource
	private ModelService modelService;

	protected void calculateTotals(final AbstractOrderModel order, final boolean recalculate,
			final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap) throws CalculationException
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			final CurrencyModel curr = order.getCurrency();
			final int digits = curr.getDigits().intValue();
			// subtotal
			final double subtotal = order.getSubtotal().doubleValue();
			// discounts

			final double totalDiscounts = calculateDiscountValues(order, recalculate);
			final double roundedTotalDiscounts = commonI18NService.roundCurrency(totalDiscounts, digits);
			order.setTotalDiscounts(Double.valueOf(roundedTotalDiscounts));
			// set total
			double licensePlateTotalPrice = 0d;
			double usedcarPrice = 0d;
			double servicePrice = 0d;
			double otherPrice = 0d;

			SalesOrderAttributeModel salesOrderAttributeModel = null;
			if(order instanceof VehicleOrderModel){
				salesOrderAttributeModel = ((VehicleOrderModel)order).getSalesAttribute();
			}
			if(order instanceof QuotationInfoModel){
				salesOrderAttributeModel = ((QuotationInfoModel)order).getSalesAttribute();
			}
			if(salesOrderAttributeModel != null){
				LicensePlateInfoModel licensePlateInfoModel = salesOrderAttributeModel.getLicensePlateInfo();
				if(licensePlateInfoModel!=null){
					Double licenseTaxPrice = licensePlateInfoModel.getPurchaseTax()!=null ? licensePlateInfoModel.getPurchaseTax(): 0d;
					Double licensePrice = licensePlateInfoModel.getPrice()!=null ? licensePlateInfoModel.getPrice() : 0d;
					salesOrderAttributeModel.setLicensePlateTotalPrice(licenseTaxPrice+licensePrice);
				}
				if(salesOrderAttributeModel.getLicensePlateTotalPrice()!=null) licensePlateTotalPrice = salesOrderAttributeModel.getLicensePlateTotalPrice();
				if(salesOrderAttributeModel.getUsedcarPrice()!=null) usedcarPrice = salesOrderAttributeModel.getUsedcarPrice();
				if(salesOrderAttributeModel.getServicePrice()!=null) servicePrice = salesOrderAttributeModel.getServicePrice();
				if(salesOrderAttributeModel.getOtherPrice()!=null) otherPrice = salesOrderAttributeModel.getOtherPrice();
			}

			final double total = subtotal + order.getPaymentCost().doubleValue() + order.getDeliveryCost().doubleValue() + licensePlateTotalPrice
						+ servicePrice + otherPrice - usedcarPrice - roundedTotalDiscounts;
			final double totalRounded = commonI18NService.roundCurrency(total, digits);
			order.setTotalPrice(Double.valueOf(totalRounded));
			// taxes
			final double totalTaxes = calculateTotalTaxValues(//
					order, recalculate, //
					digits, //
					getTaxCorrectionFactor(taxValueMap, subtotal, total, order), //
					taxValueMap);//

			final double totalRoundedTaxes = commonI18NService.roundCurrency(totalTaxes, digits);
			order.setTotalTax(Double.valueOf(totalRoundedTaxes));
			setCalculatedStatus(order);
			saveOrder(order);
		}
	}

	protected Map<TaxValue, Map<Set<TaxValue>, Double>> calculateSubtotal(final AbstractOrderModel order, final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			double subtotal = 0.0;
			// entry grouping via map { tax code -> Double }
			final List<AbstractOrderEntryModel> entries = order.getEntries();
			final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap = new LinkedHashMap<TaxValue, Map<Set<TaxValue>, Double>>(
					entries.size() * 2);

			double partsTotal = 0.0;
			double decorTotal = 0.0;
			double insuranceTotal = 0.0;
			double extendedWarrantyTotal = 0.0;
			double voucherTotal = 0.0;
			final List<String> partsList = Arrays.asList(ALPS_PARTS_CATEGORIES_STRING.split(","));
			final List<String> decorList = Arrays.asList(ALPS_DECOR_CATEGORIES_STRING.split(","));
			final List<String> insuranceList = Arrays.asList(ALPS_INSURANCE_CATEGORIES_STRING.split(","));
			final List<String> extendedWarrantyList = Arrays.asList(ALPS_EXTENDED_WARRANTY_CATEGORIES_STRING.split(","));
			final List<String> voucherList = Arrays.asList(ALPS_VOUCHER_CATEGORIES_STRING.split(","));

			for (final AbstractOrderEntryModel entry : entries)
			{
				calculateTotals(entry, recalculate);
				final double entryTotal = entry.getTotalPrice().doubleValue();
				subtotal += entryTotal;
				if(entry.getProductType()==null){
					modelService.refresh(entry);
				}

				if(partsList.contains(entry.getProductType())){
					partsTotal += entryTotal;
				}

				if(decorList.contains(entry.getProductType())){
					decorTotal += entryTotal;
				}

				if(insuranceList.contains(entry.getProductType())){
					insuranceTotal += entryTotal;
				}

				if(extendedWarrantyList.contains(entry.getProductType())){
					extendedWarrantyTotal += entryTotal;
				}

				if(voucherList.contains(entry.getProductType())){
					voucherTotal += entryTotal;
				}
				// use un-applied version of tax values!!!
				final Collection<TaxValue> allTaxValues = entry.getTaxValues();
				final Set<TaxValue> relativeTaxGroupKey = getUnappliedRelativeTaxValues(allTaxValues);
				for (final TaxValue taxValue : allTaxValues)
				{
					if (taxValue.isAbsolute())
					{
						addAbsoluteEntryTaxValue(entry.getQuantity().longValue(), taxValue.unapply(), taxValueMap);
					}
					else
					{
						addRelativeEntryTaxValue(entryTotal, taxValue.unapply(), relativeTaxGroupKey, taxValueMap);
					}
				}
			}
			// store subtotal
			subtotal = commonI18NService.roundCurrency(subtotal, order.getCurrency().getDigits().intValue());
			order.setSubtotal(Double.valueOf(subtotal));
			partsTotal = commonI18NService.roundCurrency(partsTotal, order.getCurrency().getDigits().intValue());
			decorTotal = commonI18NService.roundCurrency(decorTotal, order.getCurrency().getDigits().intValue());
			insuranceTotal = commonI18NService.roundCurrency(insuranceTotal, order.getCurrency().getDigits().intValue());
			extendedWarrantyTotal = commonI18NService.roundCurrency(extendedWarrantyTotal, order.getCurrency().getDigits().intValue());
			voucherTotal = commonI18NService.roundCurrency(voucherTotal, order.getCurrency().getDigits().intValue());

			SalesOrderAttributeModel salesOrderAttributeModel = null;
			if(order instanceof VehicleOrderModel){
				salesOrderAttributeModel = ((VehicleOrderModel)order).getSalesAttribute();
			}
			if(order instanceof QuotationInfoModel){
				salesOrderAttributeModel = ((QuotationInfoModel)order).getSalesAttribute();
			}
			if(salesOrderAttributeModel != null){
				salesOrderAttributeModel.setOptionalProductTotalPrice(partsTotal);
				salesOrderAttributeModel.setDecorProductTotalPrice(decorTotal);
				salesOrderAttributeModel.setInsuranceProductTotalPrice(insuranceTotal);
				salesOrderAttributeModel.setExtendedWarrantyTotalPrice(extendedWarrantyTotal);
				salesOrderAttributeModel.setVoucherTotalPrice(voucherTotal);
				getModelService().save(salesOrderAttributeModel);
			}
			return taxValueMap;
		}
		return Collections.EMPTY_MAP;
	}

	@Override
	public void calculateTotals(final AbstractOrderEntryModel entry, final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(entry))
		{
			final AbstractOrderModel order = entry.getOrder();
			final CurrencyModel curr = order.getCurrency();
			final int digits = curr.getDigits().intValue();
			final double totalPriceWithoutDiscount = commonI18NService.roundCurrency(entry.getActualPrice().doubleValue()
					* entry.getQuantity().longValue(), digits);
			final double quantity = entry.getQuantity().doubleValue();
			/*
			 * apply discounts (will be rounded each) convert absolute discount values in case their currency doesn't match
			 * the order currency
			 */
			//YTODO : use CalculatinService methods to apply discounts
			final List appliedDiscounts = DiscountValue.apply(quantity, totalPriceWithoutDiscount, digits,
					convertDiscountValues(order, entry.getDiscountValues()), curr.getIsocode());
			entry.setDiscountValues(appliedDiscounts);
			double totalPrice = totalPriceWithoutDiscount;
			for (final Iterator it = appliedDiscounts.iterator(); it.hasNext();)
			{
				totalPrice -= ((DiscountValue) it.next()).getAppliedValue();
			}
			entry.setTotalPrice(Double.valueOf(totalPrice));
			calculateTotalTaxValues(entry);
			setCalculatedStatus(entry);
			getModelService().save(entry);

		}
	}

	@Required
	public void setOrderRequiresCalculationStrategy(final OrderRequiresCalculationStrategy orderRequiresCalculationStrategy)
	{
		super.setOrderRequiresCalculationStrategy(orderRequiresCalculationStrategy);
		this.orderRequiresCalculationStrategy = orderRequiresCalculationStrategy;
	}

	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		super.setCommonI18NService(commonI18NService);
		this.commonI18NService = commonI18NService;
	}
}
