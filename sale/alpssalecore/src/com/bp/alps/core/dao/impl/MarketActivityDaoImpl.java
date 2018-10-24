package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.MarketActivityDao;
import com.bp.alps.core.model.MarketActivityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;


public class MarketActivityDaoImpl extends DefaultGenericDao<MarketActivityModel> implements MarketActivityDao
{
	protected static final String SEARCH_MARKET_ACTIVITY = "SELECT {" + MarketActivityModel.PK + "}"
			+ " FROM {" + MarketActivityModel._TYPECODE + " as " + MarketActivityModel._TYPECODE + "}"
			+ " WHERE {" + MarketActivityModel._TYPECODE + ":" + MarketActivityModel.NAME + "} like (?"+MarketActivityModel.NAME+") ";

	public MarketActivityDaoImpl(String typeCode){
		super(typeCode);
	}

	public MarketActivityModel getMarketActivityByCode(String code){
		List<MarketActivityModel> marketActivityModel = find(Collections.singletonMap(MarketActivityModel.CODE,code));
		return marketActivityModel.size()>0?marketActivityModel.get(0):null;
	}

	public List<MarketActivityModel> getAllMarketActivity(){
		SortParameters sortParameters = new SortParameters();
		sortParameters.addSortParameter(MarketActivityModel.NAME, SortParameters.SortOrder.ASCENDING);
		return find(sortParameters);
	}

	public List<MarketActivityModel> searchMarketActivity(final String name){
		if(StringUtils.isNotBlank(name))
		{
			StringBuilder queryBuilder = new StringBuilder(SEARCH_MARKET_ACTIVITY);
			final FlexibleSearchQuery query = new FlexibleSearchQuery(queryBuilder);
			query.addQueryParameter(MarketActivityModel.NAME, "%"+name+"%");
			SearchResult<MarketActivityModel> modelSearchPageData = getFlexibleSearchService().search(query);
			return modelSearchPageData.getResult();
		}
		return getAllMarketActivity();
	}
}
