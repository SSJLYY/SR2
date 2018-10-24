package com.bp.alps.vehiclecommerceservices.service.impl;

import com.bp.alps.vehiclecommerceservices.dao.OrderStatusOptionDao;
import com.bp.alps.vehiclecommerceservices.service.OrderStatusService;
import de.hybris.platform.core.enums.OrderStatus;

import javax.annotation.Resource;
import java.util.List;


public class OrderStatusStatusServiceImpl implements OrderStatusService
{
	@Resource
	public OrderStatusOptionDao orderStatusOptionDao;

	public List<OrderStatus> getOrderStatusByType(final String type)
	{
		return orderStatusOptionDao.getOrderStatusByType(type);
	}
}
