package com.bp.alps.core.dao;

import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;

import java.util.List;


public interface FollowOpportunityDao
{
	FollowOpportunityModel getFollowOpportunityByCode(String code);

	List<FollowOpportunityModel> getFollowOpportunityList(OpportunityModel opportunityModel);
}
