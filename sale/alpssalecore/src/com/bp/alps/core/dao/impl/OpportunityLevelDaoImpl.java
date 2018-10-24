package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.OpportunityLevelDao;
import com.bp.alps.core.model.oppo.level.OpportunityLevelModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;


public class OpportunityLevelDaoImpl extends DefaultGenericDao<OpportunityLevelModel> implements OpportunityLevelDao
{
	public OpportunityLevelDaoImpl(String typecode){
		super(typecode);
	}

	public OpportunityLevelModel getOpportunityLevelByCode(String code){
		List<OpportunityLevelModel> opportunityLevelList = find(Collections.singletonMap(OpportunityLevelModel.CODE, code));
		return CollectionUtils.isNotEmpty(opportunityLevelList) ? opportunityLevelList.get(0) : null;
	}

	@Override
	public List<OpportunityLevelModel> getOpportunityLevelList()
	{
		return find();
	}
}
