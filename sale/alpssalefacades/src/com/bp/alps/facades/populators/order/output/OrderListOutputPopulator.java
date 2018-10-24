package com.bp.alps.facades.populators.order.output;

import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.order.OrderInfoListData;
import de.hybris.platform.converters.Populator;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class OrderListOutputPopulator implements Populator<VehicleOrderModel, OrderInfoListData>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(VehicleOrderModel orderModel, OrderInfoListData orderInfoData) throws ConversionException
	{
		orderInfoData.setCode(orderModel.getCode());
		if(orderModel.getOrderType()!=null){
			orderInfoData.setOrderType(orderModel.getOrderType().getCode());
		}
		final UserModel userModel = orderModel.getUser();
		if(userModel instanceof CustomerModel){
			CustomerModel customerModel = (CustomerModel)userModel;
			orderInfoData.setCustomerMobileNumber(customerModel.getMobileNumber());
			orderInfoData.setCustomerName(customerModel.getName());
		}
		orderInfoData.setLineItemName(orderModel.getDescription());
		if(orderModel.getStatus()!=null) orderInfoData.setOrderStatus(enumerationService.getEnumerationName(orderModel.getStatus()));
		if(orderModel.getPaymentStatus()!=null) orderInfoData.setPaymentStatus(enumerationService.getEnumerationName(orderModel.getPaymentStatus()));
		if(orderModel.getDeliveryStatus()!=null) orderInfoData.setVehicleStatus(enumerationService.getEnumerationName(orderModel.getDeliveryStatus()));
		orderInfoData.setCreationtime(DateFormatUtils.getDateString("datetime", orderModel.getCreationtime()));
		orderInfoData.setTotalPrice(orderModel.getTotalPrice());
	}
}
