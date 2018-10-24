package com.bp.alps.vehiclecommercefacade.populators.vehicle.output;

import com.bp.alps.vehiclecommerceservices.model.VehicleIncreaseInfoModel;
import com.bp.alps.facades.data.vehicle.IncreaseData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class VehicleIncreaseOutputPoplator implements Populator<VehicleIncreaseInfoModel, IncreaseData>
{
	@Override
	public void populate(VehicleIncreaseInfoModel vehicleIncreaseInfoModel, IncreaseData increaseData)
			throws ConversionException
	{
		increaseData.setNumber(vehicleIncreaseInfoModel.getNumber());
		if(vehicleIncreaseInfoModel.getPrice()!=null) increaseData.setPrice(vehicleIncreaseInfoModel.getPrice().intValue());
		if(vehicleIncreaseInfoModel.getStore()!=null) increaseData.setStore(vehicleIncreaseInfoModel.getStore().getName());
	}
}
