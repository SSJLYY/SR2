package com.bp.alps.vehiclecommerceservices.service;

import com.bp.alps.vehiclecommerceservices.model.type.CityModel;
import com.bp.alps.vehiclecommerceservices.model.type.DistrictModel;
import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;

import java.util.List;


public interface RegionService
{
	CityModel getCityByCode(String code);

	DistrictModel getDistrictByCode(String code);

	ProvinceModel getProvinceByCode(String proviceCode);

	List<ProvinceModel> getProvinceList();

	List<DistrictModel> getDistrictByCity(CityModel cityModel);

	List<CityModel> getCityByProvince(ProvinceModel provinceModel);
}
