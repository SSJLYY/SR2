package com.bp.alps.vehiclecommercefacade.populators.customer.output;

import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.customer.CustomerBaseData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class CustomerBaseDataOutputPopulaor implements Populator<CustomerModel, CustomerBaseData>
{
	@Resource
	private EnumerationService enumerationService;

	public void populate(CustomerModel customerModel, CustomerBaseData customerBaseData) throws ConversionException
	{
		customerBaseData.setName(customerModel.getName());
		customerBaseData.setMobileNumber(customerModel.getMobileNumber());
		if(customerModel.getAttribute()!=null){
			customerBaseData.setAttribute(enumerationService.getEnumerationName(customerModel.getAttribute()));
			customerBaseData.setAttributeCode(customerModel.getAttribute().toString());
		}
		if(customerModel.getType()!=null){
			customerBaseData.setCustomerType(enumerationService.getEnumerationName(customerModel.getType()));
			customerBaseData.setCustomerTypeCode(customerModel.getType().toString());
		}
		if(customerModel.getCustomerRole()!=null){
			customerBaseData.setRole(enumerationService.getEnumerationName(customerModel.getCustomerRole()));
			customerBaseData.setRoleCode(customerModel.getCustomerRole().toString());
		}
		if(customerModel.getStatus()!=null){
			customerBaseData.setStatus(enumerationService.getEnumerationName(customerModel.getStatus()));
			customerBaseData.setStatusCode(customerModel.getStatus().toString());
		}
		customerBaseData.setCreationtime(DateFormatUtils.getDateString("datetime", customerModel.getCreationtime()));
		customerBaseData.setUid(customerModel.getUid());
	}
}
