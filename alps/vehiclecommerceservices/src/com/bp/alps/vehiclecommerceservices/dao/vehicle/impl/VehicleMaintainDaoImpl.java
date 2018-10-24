package com.bp.alps.vehiclecommerceservices.dao.vehicle.impl;

import com.bp.alps.vehiclecommerceservices.dao.vehicle.VehicleMaintainDao;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleMaintainInfoModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;


public class VehicleMaintainDaoImpl extends DefaultGenericDao<VehicleMaintainInfoModel> implements VehicleMaintainDao
{
	public VehicleMaintainDaoImpl(String typeCode){
		super(typeCode);
	}

	public List<VehicleMaintainInfoModel> getMaintainByVehicle(VehicleInfoModel vehicleInfoModel){
		return find(Collections.singletonMap(VehicleMaintainInfoModel.VEHICLEINFO, vehicleInfoModel));
	}
}
