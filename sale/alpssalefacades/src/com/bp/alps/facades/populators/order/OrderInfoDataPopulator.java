package com.bp.alps.facades.populators.order;

import com.bp.alps.core.enums.OrderType;
import com.bp.alps.core.model.SalesOrderAttributeModel;
import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import com.bp.alps.facades.data.order.OrderInfoData;
import com.bp.alps.facades.data.vehicle.VehicleInfoData;
import com.bp.alps.vehiclecommerceservices.utils.ValidateClassUtils;
import de.hybris.platform.converters.Populator;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;


public class OrderInfoDataPopulator implements Populator<OrderInfoData, VehicleOrderModel>
{
	@Resource
	private VehicleService vehicleService;

	@Resource
	private Converter<QuotationData, AbstractOrderModel> abstractOrderConverter;

	@Resource
	private Converter<QuotationData, SalesOrderAttributeModel> salesOrderAttributeConverter;

	@Resource
	private ModelService modelService;

	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(OrderInfoData orderInfoData, VehicleOrderModel orderModel) throws ConversionException
	{
		abstractOrderConverter.convert(orderInfoData, orderModel);
		orderModel.setDescription(orderInfoData.getLineItemName());
		if(orderInfoData.getOrderType()!=null) orderModel.setOrderType(OrderType.valueOf(orderInfoData.getOrderType()));
		SalesOrderAttributeModel salesOrderAttribute = orderModel.getSalesAttribute();
		if(salesOrderAttribute == null) salesOrderAttribute = modelService.create(SalesOrderAttributeModel.class);

		salesOrderAttributeConverter.convert(orderInfoData, salesOrderAttribute);
		if(!ValidateClassUtils.isAllFieldNull(salesOrderAttribute)){
			orderModel.setSalesAttribute(salesOrderAttribute);
		}

		VehicleInfoData vehicleInfoData = orderInfoData.getVehicleInfo();
		if(vehicleInfoData!=null && StringUtils.isNotBlank(vehicleInfoData.getCode())){
			VehicleInfoModel vehicleInfoModel = vehicleService.getVehicleByCode(vehicleInfoData.getCode());
			orderModel.setVehicle(vehicleInfoModel);
		}
	}
}
