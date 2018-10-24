package com.bp.alps.after.sale.core.dao;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.returns.model.ReturnRequestModel;


public interface ReturnOrderDao
{
	ReturnRequestModel getReturnOrderByCode(String code);

	SearchPageData<ReturnRequestModel> getReturnOrderByCurrentServiceConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData);
}
