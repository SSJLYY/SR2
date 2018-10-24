package com.bp.alps.facades.quotation.impl;

import com.bp.alps.facades.constants.AlpssalefacadesConstants;
import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.vehiclecommercefacade.abstractOrder.AbstractOrderEntryOutputConverter;
import com.bp.alps.vehiclecommercefacade.abstractOrder.impl.AbstractOrderEntryOutputConverterImpl;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Arrays;


public class DefaultEntryOutputConverterImpl extends AbstractOrderEntryOutputConverterImpl<QuotationData> implements AbstractOrderEntryOutputConverter<QuotationData>
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultEntryOutputConverterImpl.class);

	private static final String ALPS_PARTS_CATEGORIES_STRING = Config.getString(AlpssalefacadesConstants.Product.ALPS_PARTS_CATEGORIES_KEY,"");

	private static final String ALPS_DECOR_CATEGORIES_STRING = Config.getString(AlpssalefacadesConstants.Product.ALPS_DECOR_CATEGORIES_KEY,"");

	private static final String ALPS_INSURANCE_CATEGORIES_STRING = Config.getString(AlpssalefacadesConstants.Product.ALPS_INSURANCE_CATEGORIES_KEY,"");

	private static final String ALPS_EXTENDED_WARRANTY_CATEGORIES_STRING = Config.getString(AlpssalefacadesConstants.Product.ALPS_EXTENDED_WARRANTY_CATEGORIES_KEY,"");

	private static final String ALPS_VOUCHER_CATEGORIES_STRING = Config.getString(AlpssalefacadesConstants.Product.ALPS_VOUCHER_CATEGORIES_KEY,"");

	@Resource
	private Populator vehicleProductDataOutputPopulator;

	@Override
	public void converter(AbstractOrderModel abstractOrderModel, QuotationData quotationData)
	{
		vehicleProductDataOutputPopulator.populate(abstractOrderModel, quotationData);
		populatorEntryProduct("MaterielProduct", Arrays.asList(ALPS_PARTS_CATEGORIES_STRING.split(",")), "optionalProduct", abstractOrderModel, quotationData);
		populatorEntryProduct("MaterielProduct", Arrays.asList(ALPS_DECOR_CATEGORIES_STRING.split(",")), "upholsteryProduct", abstractOrderModel, quotationData);
		populatorEntryProduct("InsuranceProduct", Arrays.asList(ALPS_INSURANCE_CATEGORIES_STRING.split(",")), "insuranceProduct", abstractOrderModel, quotationData);
		populatorEntryProduct("InsuranceProduct", Arrays.asList(ALPS_EXTENDED_WARRANTY_CATEGORIES_STRING.split(",")), "extendedWarrantyProduct", abstractOrderModel, quotationData);
		populatorEntryProduct("VoucherProduct", Arrays.asList(ALPS_VOUCHER_CATEGORIES_STRING.split(",")), "coupon", abstractOrderModel, quotationData);
	}
}
