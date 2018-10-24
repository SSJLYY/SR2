package com.bp.alps.core.service;

import com.bp.alps.core.model.type.CustomerStreamModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Date;
import java.util.List;


public interface CustomerSteamService
{
	CustomerStreamModel createCustomerSteam(CustomerStreamModel customerFlowData);

	CustomerStreamModel updateCustomerSteam(CustomerStreamModel customerStreamModel);

	SearchPageData<CustomerStreamModel> getCustomerStreamByUserBeforDate(final CustomerModel customerModel, Date date, final PageableData pageableData);

	List<CustomerStreamModel> getCustomerStreamByCode(String code);
}
