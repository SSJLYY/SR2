package com.bp.alps.after.sale.core.dao;

import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;

import java.util.List;


public interface IncrementOrderDao
{
	SearchPageData<IncrementOrderModel> getIncrementOrderByCurrentConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData);

	IncrementOrderModel getIncrementOrderByCode(String code);
}
