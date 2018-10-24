package com.bp.alps.vehiclecommercefacade.populators.customer.output;

import com.bp.alps.facades.data.order.CustomerInfoData;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class CustomerOutputDataPopulaor implements Populator<CustomerModel, CustomerInfoData>
{
	@Resource
	private EnumerationService enumerationService;

	public void populate(CustomerModel customerModel, CustomerInfoData customerInfoData) throws ConversionException
	{
		AddressModel addressModel = customerModel.getDefaultShipmentAddress();
		if(addressModel!=null)
		{
			customerInfoData.setIdentityNumber(addressModel.getIdentityNumber());
			customerInfoData.setIdentityType(addressModel.getIdentityType());
			customerInfoData.setAddress(addressModel.getLine1());
			if(addressModel.getCityRef()!=null){
				customerInfoData.setCityCode(addressModel.getCityRef().getCode());
				customerInfoData.setCity(addressModel.getCityRef().getName());
			}
			if(addressModel.getProvinceRef()!=null){
				customerInfoData.setProvinceCode(addressModel.getProvinceRef().getCode());
				customerInfoData.setProvince(addressModel.getProvinceRef().getName());
			}
			if(addressModel.getDistrictRef()!=null){
				customerInfoData.setDistrictCode(addressModel.getDistrictRef().getCode());
				customerInfoData.setDistrict(addressModel.getDistrictRef().getName());
			}
			customerInfoData.setOtherContactNumber(addressModel.getPhone1());
		}
		customerInfoData.setCustomerSource(customerModel.getCustomerSource());
		ItemModel itemModel = customerModel.getOwner();
		if(itemModel!=null && itemModel instanceof CustomerModel){
			customerInfoData.setCreator(((CustomerModel) itemModel).getName());
		}
		customerInfoData.setCreationtime(DateFormatUtils.getDateString("datetime", customerModel.getCreationtime()));
	}
}
