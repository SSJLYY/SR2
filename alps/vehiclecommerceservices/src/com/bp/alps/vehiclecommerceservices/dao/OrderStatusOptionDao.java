package com.bp.alps.vehiclecommerceservices.dao;

import de.hybris.platform.core.enums.OrderStatus;

import java.util.List;


public interface OrderStatusOptionDao
{
	List<OrderStatus> getOrderStatusByType(String type);
}
