package com.bp.alps.vehiclecommerceservices.dao.vehicle;

import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleMaintainInfoModel;

import java.util.List;


public interface VehicleMaintainDao
{
	List<VehicleMaintainInfoModel> getMaintainByVehicle(VehicleInfoModel vehicleInfoModel);
}
