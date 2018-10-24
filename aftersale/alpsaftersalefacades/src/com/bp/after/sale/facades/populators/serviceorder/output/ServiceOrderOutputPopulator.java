package com.bp.after.sale.facades.populators.serviceorder.output;

import com.bp.after.sale.facades.data.ServiceOrderData;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;


public class ServiceOrderOutputPopulator implements Populator<ServiceOrderModel, ServiceOrderData>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private Converter serviceOrderEntryOutputConverter;

	@Override
	public void populate(ServiceOrderModel source, ServiceOrderData target) throws ConversionException
	{
		target.setMileageInFactory(source.getMileageInFactory());
		if(source.getEstimatedDeliveryTime()!=null) target.setEstimatedDeliveryTime(DateFormatUtils.getDateString("datetime", source.getEstimatedDeliveryTime()));
		if(source.getActualDeliveryTime()!=null) target.setActualDeliveryTime(DateFormatUtils.getDateString("datetime", source.getActualDeliveryTime()));
		if(source.getDispatchWorksTime()!=null) target.setDispatchWorksTime(DateFormatUtils.getDateString("datetime", source.getDispatchWorksTime()));
		if(source.getCompletedTime()!=null) target.setCompletedTime(DateFormatUtils.getDateString("datetime", source.getCompletedTime()));
		if(source.getCheckoutTime()!=null) target.setCheckoutTime(DateFormatUtils.getDateString("datetime", source.getCheckoutTime()));
		target.setTimeFee(source.getTimeFee());
		target.setOtherFee(source.getOtherFee());
		target.setAccessoriesFee(source.getAccessoriesFee());
		target.setManageFee(source.getManageFee());
		target.setSubTotal(source.getSubtotal());
		target.setTotal(source.getTotalPrice());
		if(CollectionUtils.isNotEmpty(source.getEntries())){
			target.setEntries(serviceOrderEntryOutputConverter.convertAll(source.getEntries()));
		}
	}
}
