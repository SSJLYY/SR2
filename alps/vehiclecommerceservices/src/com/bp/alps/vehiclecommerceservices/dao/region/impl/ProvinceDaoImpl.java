package com.bp.alps.vehiclecommerceservices.dao.region.impl;

import com.bp.alps.vehiclecommerceservices.dao.region.ProvinceDao;
import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;

import java.util.Collections;
import java.util.List;


public class ProvinceDaoImpl extends DefaultGenericDao<ProvinceModel> implements ProvinceDao
{
	public ProvinceDaoImpl(String typecode){
		super(typecode);
	}

	@Override
	public ProvinceModel getProvinceByCode(String proviceCode)
	{
		List<ProvinceModel> provinceModelList = find(Collections.singletonMap(ProvinceModel.CODE,proviceCode));
		return provinceModelList.size()>0?provinceModelList.get(0):null;
	}

	public List<ProvinceModel> getProvinceList()
	{
		SortParameters sortParameters = new SortParameters();
		sortParameters.addSortParameter(ProvinceModel.NAME,SortParameters.SortOrder.ASCENDING);
		List<ProvinceModel> provinceModels = find(sortParameters);
		return provinceModels;
	}
}
