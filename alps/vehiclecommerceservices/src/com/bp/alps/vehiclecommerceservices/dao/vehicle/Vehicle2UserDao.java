package com.bp.alps.vehiclecommerceservices.dao.vehicle;

import com.bp.alps.vehiclecommerceservices.model.Vehicle2UserModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;

import java.util.List;


public interface Vehicle2UserDao
{
	List<Vehicle2UserModel> getUserByVehice(VehicleInfoModel vehicleInfoModel);
}
