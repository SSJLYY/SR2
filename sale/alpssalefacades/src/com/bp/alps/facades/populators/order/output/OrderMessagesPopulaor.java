package com.bp.alps.facades.populators.order.output;

import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.order.OrderProcessRecordData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.ordercancel.model.OrderProcessRecordModel;
import de.hybris.platform.ordermodify.model.OrderModificationRecordModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class OrderMessagesPopulaor implements Populator<OrderModificationRecordModel, OrderProcessRecordData>
{
	@Override
	public void populate(OrderModificationRecordModel source, OrderProcessRecordData target) throws ConversionException
	{
		if(source instanceof OrderProcessRecordModel){
			OrderProcessRecordModel baseSource = (OrderProcessRecordModel)source;
			target.setMessage(baseSource.getProcessMessage());
			target.setCreationtime(DateFormatUtils.getDateString("datetime", baseSource.getCreationtime()));
		}
	}
}
