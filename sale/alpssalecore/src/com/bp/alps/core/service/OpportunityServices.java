package com.bp.alps.core.service;

import com.bp.alps.core.model.oppo.level.OpportunityLevelModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;


public interface OpportunityServices extends AlpssalecoreService
{
	OpportunityModel getOpportunityByKey(final String code, final String mobile);

	void getSaleAccess(OpportunityModel opportunityModel);

	void setTestDriveConfrimStatus(OpportunityModel opportunityModel);

	OpportunityModel saveOpportunity(OpportunityModel opportunityModel);

	SearchPageData<OpportunityModel> getOpportunityBySales(CustomerModel customer, final String moblie, final String opportunityLevel, PageableData pageableData);

	OpportunityModel additionaFolowlines(OpportunityModel opportunityModel, FollowOpportunityModel followOpportunityModel);

	OpportunityLevelModel getOpportunityLevelByCode(String code);
}
