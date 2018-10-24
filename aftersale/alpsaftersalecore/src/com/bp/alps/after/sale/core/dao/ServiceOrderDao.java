package com.bp.alps.after.sale.core.dao;

import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.facades.data.order.SearchServiceOrderRequest;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.search.SearchResult;


public interface ServiceOrderDao
{
	SearchPageData<ServiceOrderModel> getServiceOrderByCurrentServiceConsultant(SearchServiceOrderRequest searchServiceOrderRequest, PageableData pageableData);

	ServiceOrderModel getServiceOrderByCode(String code);

	SearchResult<ServiceOrderModel> getSubOrder(ServiceOrderModel serviceOrderModel);
}
