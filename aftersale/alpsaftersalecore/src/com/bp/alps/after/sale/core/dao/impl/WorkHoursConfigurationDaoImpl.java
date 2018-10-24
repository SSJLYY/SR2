package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.dao.WorkHoursConfigurationDao;
import com.bp.alps.after.sale.core.model.WorkHoursConfigurationModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;


public class WorkHoursConfigurationDaoImpl extends DefaultGenericDao<WorkHoursConfigurationModel> implements
		WorkHoursConfigurationDao
{
	protected static final String WORK_HOURS_SEARCH = "SELECT {" + WorkHoursConfigurationModel.PK + "} FROM {"
			+ WorkHoursConfigurationModel._TYPECODE + " "
			+ "join "+ ProductModel._TYPECODE + " on {"+WorkHoursConfigurationModel._TYPECODE+":"+WorkHoursConfigurationModel.PRODUCT+"} = {"+ ProductModel._TYPECODE + ":" + ProductModel.PK+"}"
			+" } WHERE {"+ProductModel._TYPECODE+":" + ProductModel.CODE + "} = ?productCode ";

	 public WorkHoursConfigurationDaoImpl(){
	 	super(WorkHoursConfigurationModel._TYPECODE);
	 }

	@Override
	public List<WorkHoursConfigurationModel> getWorkHoursModelByProductCode(String productCode)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(WORK_HOURS_SEARCH);
		query.addQueryParameter("productCode", productCode);
		SearchResult<WorkHoursConfigurationModel> searchResult = getFlexibleSearchService().search(query);
		return searchResult.getResult();
	}
}
