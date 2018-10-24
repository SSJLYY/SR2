package com.bp.after.sale.facades.returnorder;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.SearchIncrementOrderRequest;
import com.bp.alps.facades.data.order.CreateIncrementOrderResponse;
import com.bp.alps.facades.data.order.IncrementOrderListResponse;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

import java.util.List;


public interface ReturnOrderFacade
{
	CreateIncrementOrderResponse createReturnOrder(IncrementOrderData incrementOrderData);

	CreateIncrementOrderResponse updateOrder(IncrementOrderData incrementOrderData);

	IncrementOrderListResponse getReturnOrderByCurrentConsultant(SearchIncrementOrderRequest searchIncrementOrderRequest);

	IncrementOrderData getReturnOrderByCode(String code);

	List<OptionData> getOrderStatus();
}
