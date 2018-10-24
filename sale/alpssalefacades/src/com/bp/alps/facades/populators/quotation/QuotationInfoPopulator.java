package com.bp.alps.facades.populators.quotation;

import com.bp.alps.core.model.*;
import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.vehiclecommerceservices.utils.ValidateClassUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


public class QuotationInfoPopulator implements Populator<QuotationData, QuotationInfoModel>
{
	@Resource
	private Converter<QuotationData, AbstractOrderModel> abstractOrderConverter;

	@Resource
	private Converter<QuotationData, SalesOrderAttributeModel> salesOrderAttributeConverter;

	@Resource
	private ModelService modelService;

	@Override
	public void populate(QuotationData quotationData, QuotationInfoModel quotationInfoModel) throws ConversionException
	{
		quotationInfoModel.setDeposit(quotationData.getDeposit());
		quotationInfoModel.setDescription(quotationData.getLineItemName());
		abstractOrderConverter.convert(quotationData, quotationInfoModel);
		SalesOrderAttributeModel salesOrderAttribute = quotationInfoModel.getSalesAttribute();
		if(salesOrderAttribute == null) salesOrderAttribute = modelService.create(SalesOrderAttributeModel.class);

		salesOrderAttributeConverter.convert(quotationData, salesOrderAttribute);
		if(!ValidateClassUtils.isAllFieldNull(salesOrderAttribute)){
			quotationInfoModel.setSalesAttribute(salesOrderAttribute);
		}
	}
}
