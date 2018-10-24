package com.bp.alps.vehiclecommercefacade.populators.vehicle;

import com.bp.alps.facades.data.vehicle.Vehicle2User;
import com.bp.alps.vehiclecommerceservices.enums.Vehicle2UserType;
import com.bp.alps.vehiclecommerceservices.model.Vehicle2UserModel;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;


public class Vehicle2UserPoplator implements Populator<Vehicle2User, Vehicle2UserModel>
{
	@Resource
	private UserService userService;

	@Resource
	private VehicleService vehicleService;

	@Override
	public void populate(Vehicle2User vehicle2User, Vehicle2UserModel vehicle2UserModel)
			throws ConversionException
	{
		if(vehicle2User.getUserType()!=null) vehicle2UserModel.setUserType(Vehicle2UserType.valueOf(vehicle2User.getUserType()));
		UserModel userModel = userService.getUserForUID(vehicle2User.getUid());
		if(userModel instanceof CustomerModel){
			vehicle2UserModel.setUser((CustomerModel)userModel);
		}
		vehicle2UserModel.setVehicleInfo(vehicleService.getVehicleByCode(vehicle2User.getVehicleCode()));
	}
}
