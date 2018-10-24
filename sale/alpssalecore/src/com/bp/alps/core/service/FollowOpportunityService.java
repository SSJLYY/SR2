package com.bp.alps.core.service;

import com.bp.alps.core.model.MarketActivityModel;
import com.bp.alps.core.model.type.FollowContentModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;

import java.util.List;


public interface FollowOpportunityService
{
	List<MarketActivityModel> getAllMarketActivity();

	List<MarketActivityModel> searchMarketActivity(final String name);

	FollowOpportunityModel saveFollowOpportunity(FollowOpportunityModel followOpportunityModel);

	FollowOpportunityModel saveFollowOpportunity(OpportunityModel opportunityModel, FollowOpportunityModel followOpportunityModel);

	FollowContentModel saveFollowContent(FollowContentModel followContentModel, FollowOpportunityModel followOpportunityModel);

	FollowOpportunityModel getFollowOpportunityByCode(String code);

	List<FollowOpportunityModel> getFollowOpportunityList(OpportunityModel opportunityModel);
}
