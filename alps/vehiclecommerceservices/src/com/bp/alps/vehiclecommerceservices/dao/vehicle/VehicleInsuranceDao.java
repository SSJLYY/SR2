package com.bp.alps.vehiclecommerceservices.dao.vehicle;

import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInsuranceInfoModel;

import java.util.List;


public interface VehicleInsuranceDao
{
	List<VehicleInsuranceInfoModel> getInsuranceByVehicle(VehicleInfoModel vehicleInfoModel);
}
