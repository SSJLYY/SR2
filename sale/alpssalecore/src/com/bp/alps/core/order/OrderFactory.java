package com.bp.alps.core.order;

import com.bp.alps.core.model.VehicleOrderModel;


public interface OrderFactory
{
	VehicleOrderModel createOrder();
}
