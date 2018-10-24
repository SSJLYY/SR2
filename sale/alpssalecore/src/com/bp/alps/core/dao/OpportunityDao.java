package com.bp.alps.core.dao;

import com.bp.alps.core.model.type.OpportunityModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Map;


public interface OpportunityDao
{
	OpportunityModel getOpportunityByKey(Map<String,Object> key);

	SearchPageData<OpportunityModel> getOpportunityBySales(CustomerModel customer, String opportunityLevel, String mobile, PageableData pageableData);
}
