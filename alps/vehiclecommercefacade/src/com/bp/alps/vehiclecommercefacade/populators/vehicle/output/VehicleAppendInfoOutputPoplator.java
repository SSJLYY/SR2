package com.bp.alps.vehiclecommercefacade.populators.vehicle.output;

import com.bp.alps.facades.data.vehicle.*;
import com.bp.alps.vehiclecommerceservices.model.*;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


public class VehicleAppendInfoOutputPoplator implements Populator<VehicleInfoModel, VehicleInfoData>
{
	@Resource
	private VehicleService vehicleService;

	@Resource
	private Converter<Vehicle2UserModel, Vehicle2User> vehicle2UserOutputConverter;

	@Resource
	private Converter<VehicleIncreaseInfoModel, IncreaseData> vehicleIncreaseOutputConverter;

	@Resource
	private Converter<VehicleInsuranceInfoModel, InsuranceData> vehicleInsuranceOutputConverter;

	@Resource
	private Converter<VehicleMaintainInfoModel, MaintainData> vehicleMaintainOutputConverter;

	@Override
	public void populate(VehicleInfoModel vehicleInfoModel, VehicleInfoData vehicleInfoData)
			throws ConversionException
	{
		List<Vehicle2UserModel> vehicle2UserList = vehicleService.getRelatedUserByVehice(vehicleInfoModel);
		if(CollectionUtils.isNotEmpty(vehicle2UserList)){
			vehicleInfoData.setVehicle2User(vehicle2UserOutputConverter.convertAll(vehicle2UserList));
		}

		List<VehicleIncreaseInfoModel> vehicleIncreaseList = vehicleService.getIncreaseByVehicle(vehicleInfoModel);
		if(CollectionUtils.isNotEmpty(vehicleIncreaseList)){
			vehicleInfoData.setIncrease(vehicleIncreaseOutputConverter.convertAll(vehicleIncreaseList));
		}

		List<VehicleInsuranceInfoModel> vehicleInsuranceList = vehicleService.getInsuranceByVehicle(vehicleInfoModel);
		if(CollectionUtils.isNotEmpty(vehicle2UserList)){
			vehicleInfoData.setInsurance(vehicleInsuranceOutputConverter.convertAll(vehicleInsuranceList));
		}

		List<VehicleMaintainInfoModel> vehicleMaintainList = vehicleService.getMaintainByVehicle(vehicleInfoModel);
		if(CollectionUtils.isNotEmpty(vehicleMaintainList)){
			vehicleInfoData.setMaintain(vehicleMaintainOutputConverter.convertAll(vehicleMaintainList));
		}
	}
}
