package com.bp.alps.vehiclecommercefacade.populators.region;

import com.bp.alps.facades.data.region.CityData;
import com.bp.alps.facades.data.region.DistrictData;
import com.bp.alps.vehiclecommerceservices.model.type.CityModel;
import com.bp.alps.vehiclecommerceservices.model.type.DistrictModel;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class CityPoplator implements Populator<CityModel,CityData>
{
	@Resource
	private RegionService regionService;

	@Resource
	private Converter<DistrictModel,DistrictData> districtConverter;

	@Override
	public void populate(CityModel cityModel , CityData cityData) throws ConversionException
	{
		cityData.setId(cityModel.getCode());
		cityData.setLabel(cityModel.getName());
		List<DistrictModel> districtModelList = regionService.getDistrictByCity(cityModel);
		cityData.setDistricts(districtConverter.convertAll(districtModelList));
	}
}
