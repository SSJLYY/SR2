package com.bp.alps.vehiclecommercefacade.populators.vehicle;

import com.bp.alps.facades.data.vehicle.InsuranceData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInsuranceInfoModel;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class VehicleInsurancePoplator implements Populator<InsuranceData, VehicleInsuranceInfoModel>
{
	@Resource
	private VehicleService vehicleService;

	@Override
	public void populate(InsuranceData insuranceData, VehicleInsuranceInfoModel vehicleInsuranceInfoModel)
			throws ConversionException
	{
		vehicleInsuranceInfoModel.setNumber(insuranceData.getNumber());
		vehicleInsuranceInfoModel.setType(insuranceData.getType());
		vehicleInsuranceInfoModel.setCompany(insuranceData.getCompany());
		if(insuranceData.getStartTime()!=null)
		{
			vehicleInsuranceInfoModel.setStartTime(DateFormatUtils.getDate("date", insuranceData.getStartTime()));
		}
		if(insuranceData.getEndTime()!=null)
		{
			vehicleInsuranceInfoModel.setEndTime(DateFormatUtils.getDate("date", insuranceData.getEndTime()));
		}
		vehicleInsuranceInfoModel.setVehicleInfo(vehicleService.getVehicleByCode(insuranceData.getVehicleCode()));
	}
}
