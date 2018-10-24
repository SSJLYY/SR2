package com.bp.after.sale.facades.incrementOrder;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.SearchIncrementOrderRequest;
import com.bp.alps.facades.data.order.CreateIncrementOrderResponse;
import com.bp.alps.facades.data.order.IncrementOrderListResponse;
import de.hybris.platform.cmsfacades.data.OptionData;

import java.util.List;


public interface IncrementOrderFacade
{
	CreateIncrementOrderResponse updateIncrementOrder(IncrementOrderData incrementOrderData);

	CreateIncrementOrderResponse createIncrementOrder(IncrementOrderData incrementOrderData);

	IncrementOrderListResponse getIncrementOrderByCurrentConsultant(SearchIncrementOrderRequest searchIncrementOrderRequest);

	IncrementOrderData getIncrementOrderByCode(String code);

	List<OptionData> getServiceOrderStatus();
}
