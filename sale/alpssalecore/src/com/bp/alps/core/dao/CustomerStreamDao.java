package com.bp.alps.core.dao;

import com.bp.alps.core.model.type.CustomerStreamModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.Date;
import java.util.List;


public interface CustomerStreamDao
{
	SearchPageData<CustomerStreamModel> getCustomerStreamByUser(final CustomerModel customerModel, final PageableData pageableData);

	SearchPageData<CustomerStreamModel> getCustomerStreamByUserBeforDate(final CustomerModel customerModel, Date date, final PageableData pageableData);

	List<CustomerStreamModel> getCustomerStreamByCode(String code);
}
