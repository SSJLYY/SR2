package com.bp.alps.facades.opportunity;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.OpportunityDetailResponse;
import com.bp.alps.facades.data.OpportunityListRequest;
import com.bp.alps.facades.data.OpportunityListResponse;
import com.bp.alps.facades.data.opp.OpportunityData;


public interface OpportunityFacade
{
	DefaultResponse saveOpportunity(OpportunityData opportunityData);

	OpportunityDetailResponse getOpportunityByKey(final String code, final String mobile);

	OpportunityListResponse getOpportunityList(final OpportunityListRequest opportunityListRequest);
}
