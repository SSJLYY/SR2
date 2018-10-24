package com.bp.alps.after.sale.core.serviceorder.impl;

import com.bp.alps.after.sale.core.constants.AlpsaftersalecoreConstants;
import com.bp.alps.after.sale.core.model.ServiceOrderEntryModel;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.after.sale.core.serviceorder.ServiceOrderCalculationService;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.order.strategies.calculation.OrderRequiresCalculationStrategy;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.DiscountValue;
import de.hybris.platform.util.TaxValue;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


public class ServiceOrderCalculationServiceImpl extends DefaultCalculationService implements ServiceOrderCalculationService
{
	private static final String ALPS_PARTS_CATEGORIES_STRING = Config
			.getString(AlpsaftersalecoreConstants.Product.ALPS_PARTS_CATEGORIES_KEY,"");

	private static final String ALPS_MANHOURS_CATEGORIES_STRING = Config.getString(AlpsaftersalecoreConstants.Product.ALPS_MANHOURS_CATEGORIES_KEY,"");

	private OrderRequiresCalculationStrategy orderRequiresCalculationStrategy;

	private CommonI18NService commonI18NService;

	@Resource
	private ModelService modelService;

	public void calculateReminPrice(ServiceOrderModel serviceOrder, List<ServiceOrderModel> subOrders){
		final List<ServiceOrderEntryModel> parentOrderEntries;
		if(CollectionUtils.isNotEmpty(serviceOrder.getEntries()))
		{
			parentOrderEntries = serviceOrder.getEntries().stream()
					.filter(entry -> entry instanceof ServiceOrderEntryModel)
					.map(entry -> {
						((ServiceOrderEntryModel) entry).setRemainSum(entry.getTotalPrice());
						return (ServiceOrderEntryModel) entry;
					}).collect(Collectors.toList());

			subOrders.stream().forEach(subOrder -> {
				if(CollectionUtils.isNotEmpty(subOrder.getEntries())){
					subOrder.getEntries().stream().forEach(subEntry -> {
						final ProductModel productModel = subEntry.getProduct();
						Optional<ServiceOrderEntryModel> optional = parentOrderEntries.stream()
								.filter(parentOrderEntrie -> parentOrderEntrie.getProduct().getCode().equals(productModel.getCode()))
								.findAny();
						if(optional.isPresent()){
							ServiceOrderEntryModel parentEntryModel = optional.get();
							Double totalRemain = parentEntryModel.getRemainSum();
							totalRemain -= subEntry.getTotalPrice();
							parentEntryModel.setRemainSum(totalRemain);
						}
					});
				}
			});
		}
	}

	protected Map<TaxValue, Map<Set<TaxValue>, Double>> calculateSubtotal(final AbstractOrderModel order, final boolean recalculate)
	{
		if(!(order instanceof ServiceOrderModel))
		{
			return super.calculateSubtotal(order, recalculate);
		}

		final ServiceOrderModel serviceOrder = (ServiceOrderModel)order;

		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			double subtotal = 0.0;
			double timeFee = 0.0;
			double accessoriesFee = 0.0;

			final List<String> partsCategories = Arrays.asList(ALPS_PARTS_CATEGORIES_STRING.split(","));
			final List<String> manhoursCategories = Arrays.asList(ALPS_MANHOURS_CATEGORIES_STRING.split(","));

			// entry grouping via map { tax code -> Double }
			final List<AbstractOrderEntryModel> entries = order.getEntries();
			final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap = new LinkedHashMap<TaxValue, Map<Set<TaxValue>, Double>>(
					entries.size() * 2);

			for (final AbstractOrderEntryModel entry : entries)
			{
				calculateTotals(entry, recalculate);
				final double entryTotal = entry.getTotalPrice().doubleValue();
				subtotal += entryTotal;

				if(partsCategories.contains(entry.getProductType())){
					accessoriesFee += entryTotal;
				}

				if(manhoursCategories.contains(entry.getProductType())){
					timeFee += entryTotal;
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

			accessoriesFee = commonI18NService.roundCurrency(accessoriesFee, serviceOrder.getCurrency().getDigits().intValue());
			serviceOrder.setAccessoriesFee(accessoriesFee);

			timeFee = commonI18NService.roundCurrency(timeFee, serviceOrder.getCurrency().getDigits().intValue());
			serviceOrder.setTimeFee(timeFee);
			return taxValueMap;
		}
		return Collections.EMPTY_MAP;
	}

	public void calculateTotals(final AbstractOrderEntryModel entry, final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(entry))
		{
			final AbstractOrderModel order = entry.getOrder();
			List<AbstractOrderEntryModel> parentOrderEntries = null;
			if(order instanceof ServiceOrderModel)
			{
				ServiceOrderModel serviceOrder = (ServiceOrderModel)order;
				final ServiceOrderModel parentOrder = (ServiceOrderModel)serviceOrder.getParentOrder();
				if(parentOrder !=null)
				{
					parentOrderEntries = parentOrder.getEntries();
				}
			}

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
			if(CollectionUtils.isNotEmpty(parentOrderEntries))
			{
				Optional<ServiceOrderEntryModel> optional = parentOrderEntries.stream()
						.filter(parentOrderEntrie -> parentOrderEntrie instanceof ServiceOrderEntryModel)
						.filter(parentOrderEntrie -> parentOrderEntrie.getProduct().getCode().equals(entry.getProduct().getCode()))
						.map(parentOrderEntrie -> (ServiceOrderEntryModel)parentOrderEntrie)
						.findAny();
				ServiceOrderEntryModel parentEntryModel = optional.get();
				if(totalPrice > parentEntryModel.getRemainSum()){
					totalPrice = 0d;
				}
			}
			// set total price
			entry.setTotalPrice(Double.valueOf(totalPrice));

			// apply tax values too
			//YTODO : use CalculatinService methods to apply taxes
			calculateTotalTaxValues(entry);
			setCalculatedStatus(entry);
			if(entry instanceof ServiceOrderEntryModel){
				ServiceOrderEntryModel entryModel = (ServiceOrderEntryModel)entry;
				if(entryModel.getRemainSum() == null) entryModel.setRemainSum(Double.valueOf(totalPrice));
				getModelService().save(entryModel);
			}else{
				getModelService().save(entry);
			}
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
