package com.bp.alps.facades.populators.order.output;

import com.bp.alps.core.enums.OrderType;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.order.OrderInfoData;
import com.bp.alps.facades.data.vehicle.VehicleInfoData;
import de.hybris.platform.converters.Populator;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class VehicleInfoDataOutputPopulator implements Populator<VehicleOrderModel, OrderInfoData>
{
	@Resource
	private ProductService productService;

	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(VehicleOrderModel source, OrderInfoData target)
			throws ConversionException
	{
		if(source.getOrderType()!=null) target.setOrderType(source.getOrderType().getCode());
		if(source.getStatus()!=null) target.setStatus(enumerationService.getEnumerationName(source.getStatus()));
		VehicleInfoModel vehicleInfoModel = source.getVehicle();
		if(vehicleInfoModel!=null)
		{
			VehicleInfoData vehicleInfoData = new VehicleInfoData();
			vehicleInfoData.setCode(vehicleInfoModel.getCode());
			vehicleInfoData.setPurchaseYear(vehicleInfoModel.getPurchaseYear());
			vehicleInfoData.setVehicleBrand(vehicleInfoModel.getVehicleBrand().getName());
			vehicleInfoData.setVehicleCategory(vehicleInfoModel.getVehicleCategory().getName());
			vehicleInfoData.setVehicle(vehicleInfoModel.getVehicle().getName());
			vehicleInfoData.setLicensePlateNumber(vehicleInfoModel.getLicensePlateNumber());
			if (vehicleInfoModel.getCustomer() != null)
			{
				vehicleInfoData.setCustomerId(vehicleInfoModel.getCustomer().getUid());
				vehicleInfoData.setCustomerMobileNumber(vehicleInfoModel.getCustomer().getMobileNumber());
				vehicleInfoData.setCustomerName(vehicleInfoModel.getCustomer().getName());
			}
			vehicleInfoData.setCreationtime(DateFormatUtils.getDateString("datetime", vehicleInfoModel.getCreationtime()));
			target.setVehicleInfo(vehicleInfoData);
		}
	}
}
