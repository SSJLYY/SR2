package com.bp.alps.core.order;

import com.bp.alps.core.data.order.*;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import com.bp.alps.core.model.VehicleOrderModel;
import com.bp.alps.core.model.QuotationInfoModel;

import java.util.List;


public interface OrderService
{
	VehicleOrderModel createOrder();

	void saveOrder(VehicleOrderModel order);

	VehicleOrderModel getOrderByCode(String orderCode);

	void updateOrderStatus(VehicleOrderModel order, OrderStatus orderStatus);

	OrderStatus getOrderStatus(String statusCode);

	AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter parameter);

	AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter parameter);

	SearchPageData<VehicleOrderModel> getOrderListByCurrentSales(String orderType, PageableData pageableData);

	SearchPageData<VehicleOrderModel> searchForCurrentSalesOrders(SearchOrderParameter searchOrderParameter, PageableData pageableData);
}
