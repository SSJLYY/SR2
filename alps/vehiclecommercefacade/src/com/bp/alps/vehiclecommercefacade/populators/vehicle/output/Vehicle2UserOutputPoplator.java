package com.bp.alps.vehiclecommercefacade.populators.vehicle.output;

import com.bp.alps.facades.data.vehicle.Vehicle2User;
import com.bp.alps.vehiclecommerceservices.model.Vehicle2UserModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class Vehicle2UserOutputPoplator implements Populator<Vehicle2UserModel, Vehicle2User>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(Vehicle2UserModel vehicle2UserModel, Vehicle2User vehicle2User)
			throws ConversionException
	{
		vehicle2User.setPk(vehicle2UserModel.getPk().toString());
		if(vehicle2UserModel.getUserType()!=null) vehicle2User.setUserType(enumerationService.getEnumerationName(vehicle2UserModel.getUserType()));
		if(vehicle2UserModel.getUser()!=null)
		{
			vehicle2User.setUid(vehicle2UserModel.getUser().getUid());
			vehicle2User.setName(vehicle2UserModel.getUser().getName());
			vehicle2User.setMobile(vehicle2UserModel.getUser().getMobileNumber());
		}
	}
}
