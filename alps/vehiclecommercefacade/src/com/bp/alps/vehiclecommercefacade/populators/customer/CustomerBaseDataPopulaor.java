package com.bp.alps.vehiclecommercefacade.populators.customer;

import com.bp.alps.vehiclecommerceservices.enums.BuyerCategory;
import com.bp.alps.vehiclecommerceservices.enums.CustomerRole;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import com.bp.alps.facades.data.order.CustomerInfoData;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;


public class CustomerBaseDataPopulaor implements Populator<CustomerInfoData,  CustomerModel>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private RegionService regionService;

	@Resource
	private Converter<CustomerInfoData, AddressModel> customerAddressDataConverter;

	public void populate(CustomerInfoData customerInfoData, CustomerModel customerModel) throws ConversionException
	{
		if(StringUtils.isNotBlank(customerInfoData.getName())) customerModel.setName(customerInfoData.getName());
		if(StringUtils.isNotBlank(customerInfoData.getMobileNumber())) customerModel.setMobileNumber(customerInfoData.getMobileNumber());
		if(StringUtils.isNotBlank(customerInfoData.getRole())) customerModel.setCustomerRole(CustomerRole.valueOf(customerInfoData.getRole()));
		if(StringUtils.isNotBlank(customerInfoData.getRoleCode())) customerModel.setCustomerRole(CustomerRole.valueOf(customerInfoData.getRoleCode()));
		if(StringUtils.isNotBlank(customerInfoData.getAttribute())) customerModel.setAttribute(BuyerCategory.valueOf(customerInfoData.getAttribute()));
		if(StringUtils.isNotBlank(customerInfoData.getAttributeCode())) customerModel.setAttribute(BuyerCategory.valueOf(customerInfoData.getAttributeCode()));
		if(StringUtils.isNotBlank(customerInfoData.getCustomerType())) customerModel.setType(CustomerType.valueOf(customerInfoData.getCustomerType()));
		if(StringUtils.isNotBlank(customerInfoData.getCustomerTypeCode())) customerModel.setType(CustomerType.valueOf(customerInfoData.getCustomerTypeCode()));
		AddressModel addressModel = customerAddressDataConverter.convert(customerInfoData);
		customerModel.setDefaultShipmentAddress(addressModel);
	}
}
