package com.bp.alps.facades.order;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.order.*;


public interface OrderFacade
{
	CreateOrderResponse createOrder(final OrderInfoData orderInfoData);

	DefaultResponse updateOrder(final OrderInfoData orderInfoData);

	OrderListResponse getOrderList(OrderListRequest orderListRequest);

	OrderDetailsResponse getOrderDetails(String orderCode);

	DefaultResponse updateOrderStatus(String orderCode, String statusCode);

	OrderMessageResponse getOrderMessages(String orderCode);
}
