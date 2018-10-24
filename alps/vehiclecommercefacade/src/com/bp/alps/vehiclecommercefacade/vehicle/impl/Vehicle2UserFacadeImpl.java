package com.bp.alps.vehiclecommercefacade.vehicle.impl;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.vehicle.Vehicle2User;
import com.bp.alps.vehiclecommercefacade.vehicle.Vehicle2UserFacade;
import com.bp.alps.vehiclecommerceservices.model.Vehicle2UserModel;
import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class Vehicle2UserFacadeImpl implements Vehicle2UserFacade
{
	@Resource
	private Converter<Vehicle2User, Vehicle2UserModel> vehicle2UserConverter;

	@Resource
	private VehicleService vehicleService;

	@Resource
	private AlpsCustomerService alpsCustomerService;

	@Override
	public DefaultResponse create(Vehicle2User vehicle2User)
	{
		DefaultResponse defaultResponse = new DefaultResponse();
		CustomerModel customerModel = alpsCustomerService.getCustomerForUid(vehicle2User.getUid());
		if(customerModel==null){
			defaultResponse.setSuccess(false);
			defaultResponse.setMessageCode("invalid.user");
			return defaultResponse;
		}
		Vehicle2UserModel vehicle2UserModel = vehicleService.createVehicle2User();
		vehicle2UserConverter.convert(vehicle2User, vehicle2UserModel);
		vehicleService.saveVehicle2UserModel(vehicle2UserModel);
		defaultResponse.setSuccess(true);
		return defaultResponse;
	}

	public DefaultResponse delete(final List<String> pks){
		DefaultResponse defaultResponse = new DefaultResponse();
		if(vehicleService.deleteVehicle2UserByPks(pks)){
			defaultResponse.setSuccess(true);
			return defaultResponse;
		}
		defaultResponse.setSuccess(false);
		defaultResponse.setMessageCode("invalid.delete");
		return defaultResponse;
	}
}
