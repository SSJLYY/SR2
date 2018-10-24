package com.bp.alps.after.sale.core.increment;

import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;

import java.util.List;


public interface IncrementOrderService
{
	IncrementOrderModel createServiceOrder();

	AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter);

	AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter);

	SearchPageData<IncrementOrderModel> getIncrementOrderByCurrentConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData);

	IncrementOrderModel getIncrementOrderByCode(String code);

	OrderRelatedRolesModel createServiceOrder2User();

	void saveOrder2User(OrderRelatedRolesModel serviceOrderRelatedRoles);

	Boolean deleteOrder2UserByPks(List<String> pks);

	List<OrderRelatedRolesModel> getRelatedRoleByOrder(IncrementOrderModel incrementOrderModel);
}
