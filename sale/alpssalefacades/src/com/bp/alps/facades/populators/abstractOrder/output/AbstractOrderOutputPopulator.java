package com.bp.alps.facades.populators.abstractOrder.output;

import com.bp.alps.core.model.QuotationInfoModel;
import com.bp.alps.core.model.SalesOrderAttributeModel;
import com.bp.alps.core.model.VehicleOrderModel;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.DeliveryStatus;
import de.hybris.platform.core.enums.PaymentStatus;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;


public class AbstractOrderOutputPopulator implements Populator<AbstractOrderModel, QuotationData>
{
	@Resource
	private Converter<SalesOrderAttributeModel, QuotationData> salesOrderAttributeOutputConverter;

	@Override
	public void populate(AbstractOrderModel abstractOrderModel, QuotationData quotationData) throws ConversionException
	{
		quotationData.setCode(abstractOrderModel.getCode());
		quotationData.setTotalPrice(abstractOrderModel.getTotalPrice());
		quotationData.setSubtotal(abstractOrderModel.getSubtotal());

		if(abstractOrderModel.getDeliveryStatus()!=null) quotationData.setDeliveryStatus(abstractOrderModel.getDeliveryStatus().getCode());
		if(abstractOrderModel.getPaymentStatus()!=null) quotationData.setPaymentStatus(abstractOrderModel.getPaymentStatus().getCode());

		if(abstractOrderModel.getOtherPaymentMethod()!=null) quotationData.setPaymentType(abstractOrderModel.getOtherPaymentMethod());

		SalesOrderAttributeModel salesOrderAttribute = null;
		if(abstractOrderModel instanceof QuotationInfoModel){
			salesOrderAttribute = ((QuotationInfoModel)abstractOrderModel).getSalesAttribute();
		}else if(abstractOrderModel instanceof VehicleOrderModel){
			salesOrderAttribute = ((VehicleOrderModel)abstractOrderModel).getSalesAttribute();
		}
		if(salesOrderAttribute!=null){
			salesOrderAttributeOutputConverter.convert(salesOrderAttribute, quotationData);
		}
	}
}
