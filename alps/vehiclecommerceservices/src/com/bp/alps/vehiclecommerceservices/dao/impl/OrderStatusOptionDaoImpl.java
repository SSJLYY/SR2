package com.bp.alps.vehiclecommerceservices.dao.impl;

import com.bp.alps.vehiclecommerceservices.dao.OrderStatusOptionDao;
import com.bp.alps.vehiclecommerceservices.model.OrderStatusOptionModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class OrderStatusOptionDaoImpl extends DefaultGenericDao<OrderStatusOptionModel> implements OrderStatusOptionDao
{
	public OrderStatusOptionDaoImpl(){
		super(OrderStatusOptionModel._TYPECODE);
	}

	public List<OrderStatus> getOrderStatusByType(String type){
		List<OrderStatusOptionModel> optionModels = find(Collections.singletonMap(OrderStatusOptionModel.ORDERTYPE, type));
		return optionModels.stream().map(optionModel -> optionModel.getStatus()).collect(Collectors.toList());
	}
}
