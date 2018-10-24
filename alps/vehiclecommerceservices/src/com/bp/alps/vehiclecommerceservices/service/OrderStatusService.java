package com.bp.alps.vehiclecommerceservices.service;

import de.hybris.platform.core.enums.OrderStatus;

import java.util.List;


public interface OrderStatusService
{
	List<OrderStatus> getOrderStatusByType(String type);
}
