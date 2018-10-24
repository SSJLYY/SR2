package com.bp.alps.vehiclecommercefacade.region.impl;

import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import com.bp.alps.facades.data.region.ProvinceData;
import com.bp.alps.vehiclecommercefacade.region.RegionFacade;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.bp.alps.facades.data.RegionResponse;

import javax.annotation.Resource;
import java.util.List;


public class RegionFacadeImpl implements RegionFacade
{
	@Resource
	private Converter<ProvinceModel, ProvinceData> provinceConverter;

	@Resource
	private RegionService regionService;

	@Override
	public RegionResponse getProviceList()
	{
		RegionResponse regionResponse = new RegionResponse();
		List<ProvinceModel> provinceModelList = regionService.getProvinceList();
		regionResponse.setSuccess(true);
		regionResponse.setProvinceDataList(provinceConverter.convertAll(provinceModelList));
		return regionResponse;
	}
}
