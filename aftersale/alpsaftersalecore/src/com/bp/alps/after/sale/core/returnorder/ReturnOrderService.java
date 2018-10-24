package com.bp.alps.after.sale.core.returnorder;

import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.CommerceReturnRequestParameter;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.returns.model.ReturnRequestModel;


public interface ReturnOrderService
{
	ReturnRequestModel createReturnOrder(String orderCode);

	AlpsCommerceResult returnProcess(CommerceReturnRequestParameter alpsCommercePlaceOrderParameter);

	AlpsCommerceResult update(CommerceReturnRequestParameter alpsCommercePlaceOrderParameter);

	SearchPageData<ReturnRequestModel> getReturnOrderByCurrentConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData);

	ReturnRequestModel getReturnOrderByCode(String code);
}
