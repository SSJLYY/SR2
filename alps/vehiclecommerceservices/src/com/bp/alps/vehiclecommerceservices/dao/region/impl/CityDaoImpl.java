package com.bp.alps.vehiclecommerceservices.dao.region.impl;

import com.bp.alps.vehiclecommerceservices.dao.region.CityDao;
import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;
import com.bp.alps.vehiclecommerceservices.model.type.CityModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;


public class CityDaoImpl extends DefaultGenericDao<CityModel> implements CityDao
{
	public CityDaoImpl(String typecode){
		super(typecode);
	}

	@Override
	public CityModel getCityByCode(String proviceCode)
	{
		List<CityModel> cityModelList = find(Collections.singletonMap(CityModel.CODE,proviceCode));
		return cityModelList.size()>0?cityModelList.get(0):null;
	}

	public List<CityModel> getCityByProvince(ProvinceModel provinceModel)
	{
		List<CityModel> districtModelList = find(Collections.singletonMap(CityModel.PROVINCE, provinceModel));
		return districtModelList;
	}
}
