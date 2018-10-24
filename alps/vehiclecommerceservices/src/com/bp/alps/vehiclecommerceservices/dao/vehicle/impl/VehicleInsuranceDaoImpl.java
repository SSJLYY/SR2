package com.bp.alps.vehiclecommerceservices.dao.vehicle.impl;

import com.bp.alps.vehiclecommerceservices.dao.vehicle.VehicleInsuranceDao;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInsuranceInfoModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;


public class VehicleInsuranceDaoImpl extends DefaultGenericDao<VehicleInsuranceInfoModel> implements VehicleInsuranceDao
{
	public VehicleInsuranceDaoImpl(String typeCode){
		super(typeCode);
	}

	public List<VehicleInsuranceInfoModel> getInsuranceByVehicle(VehicleInfoModel vehicleInfoModel){
		return find(Collections.singletonMap(VehicleInsuranceInfoModel.VEHICLEINFO, vehicleInfoModel));
	}
}
