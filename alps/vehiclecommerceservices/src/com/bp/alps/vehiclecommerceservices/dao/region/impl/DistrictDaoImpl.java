package com.bp.alps.vehiclecommerceservices.dao.region.impl;

import com.bp.alps.vehiclecommerceservices.dao.region.DistrictDao;
import com.bp.alps.vehiclecommerceservices.model.type.CityModel;
import com.bp.alps.vehiclecommerceservices.model.type.DistrictModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;


public class DistrictDaoImpl extends DefaultGenericDao<DistrictModel> implements DistrictDao
{
	public DistrictDaoImpl(String typecode){
		super(typecode);
	}

	@Override
	public DistrictModel getDistrictByCode(String proviceCode)
	{
		List<DistrictModel> districtModelList = find(Collections.singletonMap(DistrictModel.CODE,proviceCode));
		return districtModelList.size()>0?districtModelList.get(0):null;
	}

	public List<DistrictModel> getDistrictByCity(CityModel cityModel)
	{
		List<DistrictModel> districtModelList = find(Collections.singletonMap(DistrictModel.CITY,cityModel));
		return districtModelList;
	}
}
