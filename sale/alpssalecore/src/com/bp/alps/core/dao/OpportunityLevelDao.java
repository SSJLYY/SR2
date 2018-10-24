package com.bp.alps.core.dao;

import com.bp.alps.core.model.oppo.level.OpportunityLevelModel;

import java.util.List;


public interface OpportunityLevelDao
{
	OpportunityLevelModel getOpportunityLevelByCode(String code);

	List<OpportunityLevelModel>  getOpportunityLevelList();
}
