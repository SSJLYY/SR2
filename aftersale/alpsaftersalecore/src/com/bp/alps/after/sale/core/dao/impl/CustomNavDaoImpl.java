package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.dao.CustomNavDao;
import com.bp.alps.after.sale.core.model.CustomNavModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;


public class CustomNavDaoImpl extends DefaultGenericDao<CustomNavModel> implements CustomNavDao
{
	protected static final String CUSTOMNAVLIST = "SELECT {" + CustomNavModel.PK + "} FROM {" + CustomNavModel._TYPECODE + "} WHERE {" + CustomNavModel.USER + "} is null OR {" + CustomNavModel.USER + "}=?user order by {"+CustomNavModel.ORDER+"} asc";

	public CustomNavDaoImpl(){
		super(CustomNavModel._TYPECODE);
	}

	public SearchResult<CustomNavModel> getCustomNav(CustomerModel customerModel){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(CUSTOMNAVLIST);
		query.addQueryParameter("user", customerModel);
		SearchResult<CustomNavModel> searchResult = getFlexibleSearchService().search(query);
		return searchResult;
	}
}
