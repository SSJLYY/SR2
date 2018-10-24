package com.bp.alps.vehiclecommercefacade.populators.vehicle.output;

import com.bp.alps.facades.data.vehicle.MaintainData;
import com.bp.alps.vehiclecommerceservices.model.VehicleMaintainInfoModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class VehicleMaintainOutputPoplator implements Populator<VehicleMaintainInfoModel, MaintainData>
{
	@Override
	public void populate(VehicleMaintainInfoModel vehicleMaintainInfoModel, MaintainData maintainData)
			throws ConversionException
	{
		maintainData.setNumber(vehicleMaintainInfoModel.getNumber());
		if(vehicleMaintainInfoModel.getStore()!=null) maintainData.setStore(vehicleMaintainInfoModel.getStore().getName());
		if(vehicleMaintainInfoModel.getContact()!=null)
		{
			maintainData.setContactName(vehicleMaintainInfoModel.getContact().getName());
		}
		if(vehicleMaintainInfoModel.getStartTime()!=null)
		{
			maintainData.setStartTime(DateFormatUtils.getDateString(DateFormatUtils.dateTimeFormat, vehicleMaintainInfoModel.getStartTime()));
		}
		if(vehicleMaintainInfoModel.getEndTime()!=null)
		{
			maintainData.setEndTime(DateFormatUtils.getDateString(DateFormatUtils.dateTimeFormat, vehicleMaintainInfoModel.getEndTime()));
		}
	}
}
