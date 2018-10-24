package com.bp.alps.core.dao;

import com.bp.alps.core.data.order.SearchOrderParameter;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;


public interface AlpsOrderDao
{
	SearchPageData<VehicleOrderModel> getOrderListByCurrentSales(String orderType, CustomerModel customerModel, PageableData pageableData);

	SearchPageData<VehicleOrderModel> searchForCurrentSalesOrders(SearchOrderParameter searchOrderParameter, CustomerModel customerModel, PageableData pageableData);

	VehicleOrderModel getOrderByCode(String orderCode);
}
