package com.bp.alps.vehiclecommercefacade.populators.vehicle.output;

import com.bp.alps.facades.data.vehicle.VehicleInfoBaseData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class VehicleBaseOutputPoplator implements Populator<VehicleInfoModel, VehicleInfoBaseData>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(VehicleInfoModel vehicleInfoModel, VehicleInfoBaseData vehicleInfoBaseData)
			throws ConversionException
	{
		vehicleInfoBaseData.setCode(vehicleInfoModel.getCode());
		vehicleInfoBaseData.setCreationtime(DateFormatUtils.getDateString("datetime", vehicleInfoModel.getCreationtime()));
		CustomerModel customerModel = vehicleInfoModel.getCustomer();
		if(customerModel!=null)
		{
			vehicleInfoBaseData.setCustomerId(customerModel.getUid());
			vehicleInfoBaseData.setCustomerName(customerModel.getName());
			vehicleInfoBaseData.setCustomerMobileNumber(customerModel.getMobileNumber());
		}
		if(vehicleInfoModel.getVehicle()!=null)
		{
			vehicleInfoBaseData.setVehicle(vehicleInfoModel.getVehicle().getName());
			vehicleInfoBaseData.setVehicleCode(vehicleInfoModel.getVehicle().getCode());
		}
		if(vehicleInfoModel.getVehicleBrand()!=null)
		{
			vehicleInfoBaseData.setVehicleBrand(vehicleInfoModel.getVehicleBrand().getName());
			vehicleInfoBaseData.setVehicleBrandCode(vehicleInfoModel.getVehicleBrand().getCode());
		}
		if(vehicleInfoModel.getVehicleCategory()!=null)
		{
			vehicleInfoBaseData.setVehicleCategory(vehicleInfoModel.getVehicleCategory().getName());
			vehicleInfoBaseData.setVehicleCategoryCode(vehicleInfoModel.getVehicleCategory().getCode());
		}
		vehicleInfoBaseData.setVinNumber(vehicleInfoModel.getVinNumber());
		if(vehicleInfoModel.getStatus()!=null)
		{
			vehicleInfoBaseData.setStatus(vehicleInfoModel.getStatus().getCode());
			vehicleInfoBaseData.setStatusName(enumerationService.getEnumerationName(vehicleInfoModel.getStatus()));
		}
		vehicleInfoBaseData.setLicensePlateNumber(vehicleInfoModel.getLicensePlateNumber());
	}
}
