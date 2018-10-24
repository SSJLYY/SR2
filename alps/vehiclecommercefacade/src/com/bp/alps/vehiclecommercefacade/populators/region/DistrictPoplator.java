package com.bp.alps.vehiclecommercefacade.populators.region;

import com.bp.alps.facades.data.region.DistrictData;
import com.bp.alps.vehiclecommerceservices.model.type.DistrictModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class DistrictPoplator implements Populator<DistrictModel,DistrictData>
{


	@Override
	public void populate(DistrictModel districtModel, DistrictData districtData) throws ConversionException
	{
		districtData.setId(districtModel.getCode());
		districtData.setLabel(districtModel.getName());
	}
}
