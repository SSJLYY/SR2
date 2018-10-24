package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.FollowOpportunityDao;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;

import java.util.Collections;
import java.util.List;


public class FollowOpportunityDaoImpl extends DefaultGenericDao<FollowOpportunityModel> implements FollowOpportunityDao
{
	public FollowOpportunityDaoImpl(String typeCode){
		super(typeCode);
	}

	@Override
	public FollowOpportunityModel getFollowOpportunityByCode(String code)
	{
		List<FollowOpportunityModel> followOpportunityModels = find(Collections.singletonMap(FollowOpportunityModel.CODE,code));
		return followOpportunityModels.size()>0?followOpportunityModels.get(0):null;
	}

	public List<FollowOpportunityModel> getFollowOpportunityList(OpportunityModel opportunityModel){
		SortParameters sortParameters = new SortParameters();
		sortParameters.addSortParameter(FollowOpportunityModel.CREATIONTIME, SortParameters.SortOrder.DESCENDING);
		return find(Collections.singletonMap(FollowOpportunityModel.OPPORTUNITY, opportunityModel), sortParameters);
	}
}
