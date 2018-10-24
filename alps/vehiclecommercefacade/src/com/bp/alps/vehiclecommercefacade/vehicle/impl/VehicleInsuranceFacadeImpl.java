package com.bp.alps.vehiclecommercefacade.vehicle.impl;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.vehicle.InsuranceData;
import com.bp.alps.vehiclecommercefacade.vehicle.VehicleInsuranceFacade;
import com.bp.alps.vehiclecommerceservices.model.VehicleInsuranceInfoModel;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class VehicleInsuranceFacadeImpl implements VehicleInsuranceFacade
{
	@Resource
	private Converter<InsuranceData, VehicleInsuranceInfoModel> vehicleInsuranceConverter;

	@Resource
	private VehicleService vehicleService;

	public DefaultResponse create(InsuranceData insuranceData){
		DefaultResponse defaultResponse = new DefaultResponse();
		VehicleInsuranceInfoModel vehicleInsuranceInfoModel = vehicleService.createVehicleInsurance();
		vehicleInsuranceConverter.convert(insuranceData, vehicleInsuranceInfoModel);
		vehicleService.saveVehicleInsurance(vehicleInsuranceInfoModel);
		defaultResponse.setSuccess(true);
		return defaultResponse;
	}

	public DefaultResponse delete(final List<String> pks){
		DefaultResponse defaultResponse = new DefaultResponse();
		if(vehicleService.deleteVehicleInsurancePks(pks)){
			defaultResponse.setSuccess(true);
			return defaultResponse;
		}
		defaultResponse.setSuccess(false);
		defaultResponse.setMessageCode("invalid.delete");
		return defaultResponse;
	}
}
