package com.bp.alps.vehiclecommerceservices.dao.vehicle.impl;

import com.bp.alps.vehiclecommerceservices.dao.vehicle.Vehicle2UserDao;
import com.bp.alps.vehiclecommerceservices.model.Vehicle2UserModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;


public class Vehicle2UserDaoImpl extends DefaultGenericDao<Vehicle2UserModel> implements Vehicle2UserDao
{
	public Vehicle2UserDaoImpl(String typeCode){
		super(typeCode);
	}

	public List<Vehicle2UserModel> getUserByVehice(VehicleInfoModel vehicleInfoModel){
		return find(Collections.singletonMap(Vehicle2UserModel.VEHICLEINFO, vehicleInfoModel));
	}
}
