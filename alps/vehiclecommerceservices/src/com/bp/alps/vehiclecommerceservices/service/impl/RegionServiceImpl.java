package com.bp.alps.vehiclecommerceservices.service.impl;

import com.bp.alps.vehiclecommerceservices.dao.region.CityDao;
import com.bp.alps.vehiclecommerceservices.dao.region.DistrictDao;
import com.bp.alps.vehiclecommerceservices.dao.region.ProvinceDao;
import com.bp.alps.vehiclecommerceservices.model.type.CityModel;
import com.bp.alps.vehiclecommerceservices.model.type.DistrictModel;
import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;
import com.bp.alps.vehiclecommerceservices.service.RegionService;

import javax.annotation.Resource;
import java.util.List;


public class RegionServiceImpl implements RegionService
{
	@Resource
	private CityDao cityDao;

	@Resource
	private DistrictDao districtDao;

	@Resource
	private ProvinceDao provinceDao;

	public CityModel getCityByCode(String code){
		return cityDao.getCityByCode(code);
	}

	public DistrictModel getDistrictByCode(String code){
		return districtDao.getDistrictByCode(code);
	}

	public ProvinceModel getProvinceByCode(String proviceCode){
		return provinceDao.getProvinceByCode(proviceCode);
	}

	public List<ProvinceModel> getProvinceList(){
		return provinceDao.getProvinceList();
	}

	public List<DistrictModel> getDistrictByCity(CityModel cityModel){
		return districtDao.getDistrictByCity(cityModel);
	}

	public List<CityModel> getCityByProvince(ProvinceModel provinceModel){
		return cityDao.getCityByProvince(provinceModel);
	}
}
