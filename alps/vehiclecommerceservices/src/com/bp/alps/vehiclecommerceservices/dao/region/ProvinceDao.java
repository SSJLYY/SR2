package com.bp.alps.vehiclecommerceservices.dao.region;

import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;

import java.util.List;


public interface ProvinceDao
{
	ProvinceModel getProvinceByCode(String proviceCode);

	List<ProvinceModel> getProvinceList();
}
