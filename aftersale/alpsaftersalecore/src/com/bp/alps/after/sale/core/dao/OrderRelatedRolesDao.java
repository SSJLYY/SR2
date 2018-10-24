package com.bp.alps.after.sale.core.dao;

import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;


public interface OrderRelatedRolesDao
{
	List<OrderRelatedRolesModel> getRelatedRoleByOrder(OrderModel orderModel);
}
