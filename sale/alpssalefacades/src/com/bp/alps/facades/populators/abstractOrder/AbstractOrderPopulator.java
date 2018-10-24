package com.bp.alps.facades.populators.abstractOrder;

import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.DeliveryStatus;
import de.hybris.platform.core.enums.PaymentStatus;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class AbstractOrderPopulator implements Populator<QuotationData, AbstractOrderModel>
{
	@Override
	public void populate(QuotationData quotationData, AbstractOrderModel abstractOrderModel) throws ConversionException
	{
		//abstractOrderModel.setCode(quotationData.getCode());
		abstractOrderModel.setTotalPrice(quotationData.getTotalPrice());
		abstractOrderModel.setSubtotal(quotationData.getSubtotal());
		abstractOrderModel.setOrderDescription(quotationData.getLineItemName());
		if(quotationData.getPaymentType()!=null) abstractOrderModel.setOtherPaymentMethod(quotationData.getPaymentType());
		if(quotationData.getDeliveryStatus()!=null) abstractOrderModel.setDeliveryStatus(DeliveryStatus.valueOf(quotationData.getDeliveryStatus()));
		if(quotationData.getPaymentStatus()!=null) abstractOrderModel.setPaymentStatus(PaymentStatus.valueOf(quotationData.getPaymentStatus()));
	}
}
