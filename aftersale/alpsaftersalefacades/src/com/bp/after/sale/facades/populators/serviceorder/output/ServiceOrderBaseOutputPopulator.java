package com.bp.after.sale.facades.populators.serviceorder.output;

import com.bp.after.sale.facades.data.ServiceOrderBaseData;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.facades.data.customer.CustomerBaseData;
import com.bp.alps.facades.data.vehicle.VehicleInfoBaseData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;


public class ServiceOrderBaseOutputPopulator implements Populator<ServiceOrderModel, ServiceOrderBaseData>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private Converter<VehicleInfoModel, VehicleInfoBaseData> vehicleBaseOutputConverter;

	@Resource
	private Converter<CustomerModel, CustomerBaseData> customerBaseDataOutputConverter;

	@Override
	public void populate(ServiceOrderModel source, ServiceOrderBaseData target) throws ConversionException
	{
		target.setCode(source.getCode());
		target.setCreationtime(DateFormatUtils.getDateString("datetime", source.getCreationtime()));
		if(source.getServiceType()!=null)
		{
			target.setServiceType(enumerationService.getEnumerationName(source.getServiceType()));
			target.setServiceTypeCode(source.getServiceType().getCode());
		}
		if(source.getVehicle()!=null)
		{
			target.setVehicle(vehicleBaseOutputConverter.convert(source.getVehicle()));
		}
		if(source.getUser()!=null && source.getUser() instanceof CustomerModel)
		{
			target.setSender(customerBaseDataOutputConverter.convert((CustomerModel)source.getUser()));
		}
		if(source.getConsultant()!=null)
		{
			target.setServiceConsultant(customerBaseDataOutputConverter.convert(source.getConsultant()));
		}
		if(source.getStatus()!=null)
		{
			target.setStatus(enumerationService.getEnumerationName(source.getStatus()));
			target.setStatusCode(source.getStatus().getCode());
		}
	}
}
