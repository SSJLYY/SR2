package com.bp.alps.vehiclecommercefacade.populators.vehicle.output;

import com.bp.alps.facades.data.vehicle.VehicleInfoData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class VehicleOutputPoplator implements Populator<VehicleInfoModel, VehicleInfoData>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(VehicleInfoModel vehicleInfoModel, VehicleInfoData vehicleInfoData)
			throws ConversionException
	{
		if(vehicleInfoModel.getVehicleType()!=null)
		{
			vehicleInfoData.setVehicleType(enumerationService.getEnumerationName(vehicleInfoModel.getVehicleType()));
			vehicleInfoData.setVehicleTypeCode(enumerationService.getEnumerationName(vehicleInfoModel.getVehicleType()));
		}
		vehicleInfoData.setPurchaseYear(vehicleInfoModel.getPurchaseYear());
		vehicleInfoData.setMileage(vehicleInfoModel.getMileage());
		vehicleInfoData.setColor(vehicleInfoModel.getColor());
		if(vehicleInfoModel.getStore()!=null){
			vehicleInfoData.setStore(vehicleInfoModel.getStore().getName());
			vehicleInfoData.setStoreCode(vehicleInfoModel.getStore().getUid());
		}
		if(vehicleInfoModel.getCreator()!=null) vehicleInfoData.setCreator(vehicleInfoModel.getCreator().getName());
		if(vehicleInfoModel.getWarrantyLastDate()!=null)
		{
			vehicleInfoData.setWarrantyLastDate(DateFormatUtils.getDateString("date", vehicleInfoModel.getWarrantyLastDate()));
		}
		if(vehicleInfoModel.getWarrantyStartDate()!=null)
		{
			vehicleInfoData.setWarrantyStartDate(DateFormatUtils.getDateString("date", vehicleInfoModel.getWarrantyStartDate()));
		}
		if(vehicleInfoModel.getEnterFactoryDate()!=null)
		{
			vehicleInfoData.setEnterFactoryDate(DateFormatUtils.getDateString("date", vehicleInfoModel.getEnterFactoryDate()));
		}
		vehicleInfoData.setWarrantyCycle(vehicleInfoModel.getWarrantyCycle());
		vehicleInfoData.setWarrantyMileage(vehicleInfoModel.getWarrantyMileage());
      vehicleInfoData.setCreationtime(DateFormatUtils.getDateString("date", vehicleInfoModel.getCreationtime()));
	}
}
