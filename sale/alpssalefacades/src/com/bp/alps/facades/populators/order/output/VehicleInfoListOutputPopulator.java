package com.bp.alps.facades.populators.order.output;

import com.bp.alps.core.enums.OrderType;
import com.bp.alps.core.model.VehicleOrderModel;
import com.bp.alps.facades.data.order.OrderInfoListData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class VehicleInfoListOutputPopulator implements Populator<VehicleOrderModel, OrderInfoListData>
{
	@Resource
	private ProductService productService;

	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(VehicleOrderModel source, OrderInfoListData target)
			throws ConversionException
	{
		if(source.getOrderType().equals(OrderType.WHOLEVEHICLE)) return;
		VehicleInfoModel vehicleInfoModel = source.getVehicle();
		if(vehicleInfoModel!=null)
		{
			target.setVehicleBrand(vehicleInfoModel.getVehicleBrand().getCode());
			target.setVehicleBrandName(vehicleInfoModel.getVehicleBrand().getName());
			target.setCarModel(vehicleInfoModel.getVehicleCategory().getCode());
			target.setCarModelName(vehicleInfoModel.getVehicleCategory().getName());
			target.setVehicle(vehicleInfoModel.getVehicle().getCode());
			target.setVehicle(vehicleInfoModel.getVehicle().getName());
		}
	}
}
