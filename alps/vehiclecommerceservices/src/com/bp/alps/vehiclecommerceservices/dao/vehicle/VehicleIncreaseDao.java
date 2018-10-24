package com.bp.alps.vehiclecommerceservices.dao.vehicle;

import com.bp.alps.vehiclecommerceservices.model.VehicleIncreaseInfoModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;

import java.util.List;


public interface VehicleIncreaseDao
{
	List<VehicleIncreaseInfoModel> getIncreaseByVehicle(VehicleInfoModel vehicleInfoModel);
}
