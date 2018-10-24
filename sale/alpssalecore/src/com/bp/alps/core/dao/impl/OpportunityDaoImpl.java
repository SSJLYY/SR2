package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.OpportunityDao;
import com.bp.alps.core.dao.OpportunityLevelDao;
import com.bp.alps.core.model.oppo.level.OpportunityLevelModel;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.util.Config;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.*;


public class OpportunityDaoImpl extends DefaultGenericDao<OpportunityModel> implements OpportunityDao
{
	@Resource
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	@Resource
	private OpportunityLevelDao opportunityLevelDao;

	protected static final String GET_OPPORTUNITY_BY_USER = "SELECT {" + OpportunityModel.PK + "}"
			+ " FROM {" + OpportunityModel._TYPECODE + " as " + OpportunityModel._TYPECODE + "}"
			+ " WHERE {" + OpportunityModel._TYPECODE + ":" + OpportunityModel.CREATEDPERSON + "} = ?"+OpportunityModel.CREATEDPERSON+" ";

	protected static final String GET_OPPORTUNITY_BY_USER_ADD_MUTIFIELD = "SELECT {" + OpportunityModel.PK + "},{" + OpportunityModel.CODE + "},{" + OpportunityModel.NAME + "},{" + OpportunityModel.GENDER + "},{" + OpportunityModel.LEVEL + "}"
			+ " FROM {" + OpportunityModel._TYPECODE + " as " + OpportunityModel._TYPECODE + "}"
			+ " WHERE {" + OpportunityModel._TYPECODE + ":" + OpportunityModel.CREATEDPERSON + "} = ?"+OpportunityModel.CREATEDPERSON+" ";

	protected static final String GET_OPPORTUNITY_BY_USER_ADD_LEVEL = "SELECT {" + OpportunityModel.PK + "}"
			+ " FROM {" + OpportunityModel._TYPECODE + " as "+OpportunityModel._TYPECODE+" left join "+ OpportunityLevelModel._TYPECODE+" "
			+ " on { " + OpportunityModel._TYPECODE + ":level } = { " + OpportunityLevelModel._TYPECODE + ":pk }}"
			+ " WHERE {" + OpportunityModel._TYPECODE + ":createdPerson} = ?"+OpportunityModel.CREATEDPERSON+" ";

	public OpportunityDaoImpl(String typecode){
		super(typecode);
	}

	@Override
	public OpportunityModel getOpportunityByKey(Map<String,Object> key)
	{
		List<OpportunityModel> opportunityModels = find(key);
		return opportunityModels.size()>0?opportunityModels.get(0):null;
	}

	public SearchPageData<OpportunityModel> getOpportunityBySales(CustomerModel customer, String opportunityLevel, String mobile, PageableData pageableData){
		StringBuilder queryBuilder = new StringBuilder();
		if(Config.getBoolean("alps.opportunity.load.muti.field.for.sales",false)){
			queryBuilder.append(GET_OPPORTUNITY_BY_USER);
		}else{
			queryBuilder.append(GET_OPPORTUNITY_BY_USER_ADD_MUTIFIELD);
		}
		Map<String, Object> queryParameterMap = new HashMap<>();
		queryParameterMap.put(OpportunityModel.CREATEDPERSON, customer);
		if(opportunityLevel != null){
			queryBuilder = new StringBuilder(GET_OPPORTUNITY_BY_USER_ADD_LEVEL);
			queryBuilder.append(" AND {"+OpportunityLevelModel._TYPECODE+":code} = ?"+OpportunityLevelModel.CODE);
			queryParameterMap.put(OpportunityLevelModel.CODE, opportunityLevel);
		}
		if(StringUtils.isNotBlank(mobile)){
			queryBuilder.append(" AND {" + OpportunityModel._TYPECODE + ":"+OpportunityModel.MOBILE+"} = ?"+OpportunityModel.MOBILE);
			queryParameterMap.put(OpportunityModel.MOBILE, mobile);
		}
		if(opportunityLevel == null && StringUtils.isBlank(mobile)){
			List<OpportunityLevelModel> opportunityLevelModelList = opportunityLevelDao.getOpportunityLevelList();
			queryBuilder.append(" AND ( 1=1 ");
			for(OpportunityLevelModel opportunityLevelModel : opportunityLevelModelList){
				final Integer maxFollowTime = opportunityLevelModel.getMaxFollowTime();
				String beforetime = DateFormatUtils.getDateString("datetime",DateFormatUtils.getBeforeTimee(maxFollowTime));
				queryBuilder.append(" OR ( {" + OpportunityModel._TYPECODE + ":"+OpportunityModel.LEVEL+"} = ?level"+opportunityLevelModel.getCode());
				queryParameterMap.put("level"+opportunityLevelModel.getCode(), opportunityLevelModel.getPk());
				if(maxFollowTime > 60 * 24)
				{
					String beforeOneDaytime = DateFormatUtils.getDateString("datetime",DateFormatUtils.getBeforeTimee(60 * 24));
					queryBuilder.append(" AND {" + OpportunityModel._TYPECODE + ":" + OpportunityModel.LASTFOLLOWTIME + "} <= ?"
							+ opportunityLevelModel.getCode() + "beforeOneDaytime");
					queryParameterMap.put(opportunityLevelModel.getCode() + "beforeOneDaytime", beforeOneDaytime);
				}
				queryBuilder.append(" AND {" + OpportunityModel._TYPECODE + ":"+OpportunityModel.LASTFOLLOWTIME+"} > ?"+opportunityLevelModel.getCode()+"beforetime )");
				queryParameterMap.put(opportunityLevelModel.getCode()+"beforetime", beforetime);

			}
			queryBuilder.append(" ) ");
		}
		queryBuilder.append(" ORDER BY {"+OpportunityModel.CODE+"} DESC");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryBuilder);
		query.addQueryParameters(queryParameterMap);
		query.setResultClassList(Collections.singletonList(OpportunityModel.class));
		/*final FlexibleSearchQuery querytwo = new FlexibleSearchQuery(queryBuilder);
		querytwo.addQueryParameters(queryParameterMap);
		List<Class> opportunityModels = new ArrayList<Class>();
		opportunityModels.add(OpportunityModel.class);
		opportunityModels.add(String.class);
		opportunityModels.add(String.class);
		opportunityModels.add(String.class);
		opportunityModels.add(String.class);
		querytwo.setResultClassList(opportunityModels);*/
		SearchPageData searchPageData = pagedFlexibleSearchService.search(query, pageableData);
		System.out.println(searchPageData.toString());
		return pagedFlexibleSearchService.search(query, pageableData);
	}
}
