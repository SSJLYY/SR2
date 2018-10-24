package com.bp.alps.vehiclecommercefacade.populators.customer.address;

import com.bp.alps.facades.data.order.CustomerInfoData;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;


public class CustomerAddressDataPopulaor implements Populator<CustomerInfoData,  AddressModel>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private RegionService regionService;

	public void populate(CustomerInfoData customerInfoData, AddressModel addressModel) throws ConversionException
	{
		if(StringUtils.isNotBlank(customerInfoData.getMobileNumber())) addressModel.setCellphone(customerInfoData.getMobileNumber());
		if(StringUtils.isNotBlank(customerInfoData.getIdentityType())) addressModel.setIdentityType(customerInfoData.getIdentityType());
		if(StringUtils.isNotBlank(customerInfoData.getIdentityNumber())) addressModel.setIdentityNumber(customerInfoData.getIdentityNumber());
		if(StringUtils.isNotBlank(customerInfoData.getOtherContactNumber())) addressModel.setPhone1(customerInfoData.getOtherContactNumber());
if(customerInfoData.getProvinceCode()!=null) addressModel.setProvinceRef(regionService.getProvinceByCode(customerInfoData.getProvinceCode()));
		if(customerInfoData.getCityCode()!=null) addressModel.setCityRef(regionService.getCityByCode(customerInfoData.getCityCode()));
		if(customerInfoData.getDistrictCode()!=null)  addressModel.setDistrictRef(regionService.getDistrictByCode(customerInfoData.getDistrictCode()));
		if(StringUtils.isNotBlank(customerInfoData.getAddress())) addressModel.setLine1(customerInfoData.getAddress());
		if(StringUtils.isNotBlank(customerInfoData.getName())) addressModel.setLastname(customerInfoData.getName());
	}
}
