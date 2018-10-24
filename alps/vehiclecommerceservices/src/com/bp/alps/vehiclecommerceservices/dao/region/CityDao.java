package com.bp.alps.vehiclecommerceservices.dao.region;

import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;
import com.bp.alps.vehiclecommerceservices.model.type.CityModel;

import java.util.List;


public interface CityDao
{
	CityModel getCityByCode(String code);

	List<CityModel> getCityByProvince(ProvinceModel provinceModel);
}
