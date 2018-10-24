package com.bp.alps.vehiclecommercefacade.populators.region;

import com.bp.alps.facades.data.region.CityData;
import com.bp.alps.facades.data.region.ProvinceData;
import com.bp.alps.vehiclecommerceservices.model.type.CityModel;
import com.bp.alps.vehiclecommerceservices.model.type.ProvinceModel;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class ProvincePoplator implements Populator<ProvinceModel,ProvinceData>
{
	@Resource
	private RegionService regionService;

	@Resource
	private Converter<CityModel,CityData> cityConverter;

	@Override
	public void populate(ProvinceModel provinceModel, ProvinceData provinceData) throws ConversionException
	{
		provinceData.setId(provinceModel.getCode());
		provinceData.setLabel(provinceModel.getName());
		List<CityModel> cityModelList = regionService.getCityByProvince(provinceModel);
		provinceData.setCitys(cityConverter.convertAll(cityModelList));
	}
}
