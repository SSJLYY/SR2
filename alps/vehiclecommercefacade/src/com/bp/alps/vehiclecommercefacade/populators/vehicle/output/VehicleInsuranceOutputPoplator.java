package com.bp.alps.vehiclecommercefacade.populators.vehicle.output;

import com.bp.alps.facades.data.vehicle.InsuranceData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInsuranceInfoModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class VehicleInsuranceOutputPoplator implements Populator<VehicleInsuranceInfoModel, InsuranceData>
{
	@Override
	public void populate(VehicleInsuranceInfoModel vehicleInsuranceInfoModel, InsuranceData insuranceData)
			throws ConversionException
	{
		insuranceData.setPk(vehicleInsuranceInfoModel.getPk().toString());
		insuranceData.setNumber(vehicleInsuranceInfoModel.getNumber());
		insuranceData.setType(vehicleInsuranceInfoModel.getType());
		insuranceData.setCompany(vehicleInsuranceInfoModel.getCompany());
		if(vehicleInsuranceInfoModel.getStartTime()!=null)
		{
			insuranceData.setStartTime(DateFormatUtils.getDateString(DateFormatUtils.dateTimeFormat, vehicleInsuranceInfoModel.getStartTime()));
		}
		if(vehicleInsuranceInfoModel.getEndTime()!=null)
		{
			insuranceData.setEndTime(DateFormatUtils.getDateString(DateFormatUtils.dateTimeFormat, vehicleInsuranceInfoModel.getEndTime()));
		}
	}
}
