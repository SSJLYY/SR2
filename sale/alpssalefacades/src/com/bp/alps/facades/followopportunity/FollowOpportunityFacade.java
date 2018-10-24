package com.bp.alps.facades.followopportunity;

import com.bp.alps.facades.data.*;
import com.bp.alps.facades.data.opp.CreatedFollowOpportunityResponse;


public interface FollowOpportunityFacade
{
	MarketActivityListResponse getAllMarketActivity();

	MarketActivityListResponse searchMarketActivity(final String name);

	CreatedFollowOpportunityResponse saveFollowOpportunity(FollowOpportunitySaveRequest followOpportunitySaveRequest);

	FollowOpportunityListResponse getFollowOpportunityList(final String opportunityCode);

	FollowOpportunityResponse getFollowOpportunityByCode(final String code);

	FollowContentListResponse appendFollowContent(FollowContentSaveRequest followContentSaveRequest);

	DefaultResponse customerConfirmAndSaveInfo(FollowConfirmForm followConfirmFrom);
}
