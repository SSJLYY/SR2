package com.bp.alps.after.sale.core.dao;

import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;

import java.util.List;


public interface ServiceOrderRelatedRolesDao
{
	List<ServiceOrderRelatedRolesModel> getRelatedRoleByOrder(ServiceOrderModel serviceOrderModel);
}
