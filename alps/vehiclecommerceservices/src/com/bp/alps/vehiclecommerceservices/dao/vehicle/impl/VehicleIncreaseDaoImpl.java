package com.bp.alps.vehiclecommerceservices.dao.vehicle.impl;

import com.bp.alps.vehiclecommerceservices.dao.vehicle.VehicleIncreaseDao;
import com.bp.alps.vehiclecommerceservices.model.VehicleIncreaseInfoModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;


public class VehicleIncreaseDaoImpl extends DefaultGenericDao<VehicleIncreaseInfoModel> implements VehicleIncreaseDao
{
	public VehicleIncreaseDaoImpl(String typeCode){
		super(typeCode);
	}

	public List<VehicleIncreaseInfoModel> getIncreaseByVehicle(VehicleInfoModel vehicleInfoModel){
		return find(Collections.singletonMap(VehicleIncreaseInfoModel.VEHICLEINFO, vehicleInfoModel));
	}
}
