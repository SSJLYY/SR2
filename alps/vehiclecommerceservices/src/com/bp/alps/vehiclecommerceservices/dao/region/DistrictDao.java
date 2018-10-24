package com.bp.alps.vehiclecommerceservices.dao.region;


import com.bp.alps.vehiclecommerceservices.model.type.CityModel;
import com.bp.alps.vehiclecommerceservices.model.type.DistrictModel;

import java.util.List;


public interface DistrictDao
{
	DistrictModel getDistrictByCode(String code);

	List<DistrictModel> getDistrictByCity(CityModel cityModel);
}
