package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.CustomerStreamDao;
import com.bp.alps.core.model.type.CustomerStreamModel;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import org.springframework.beans.factory.annotation.Required;

import java.util.*;


public class CustomerStreamDaoImpl extends DefaultGenericDao<CustomerStreamModel> implements CustomerStreamDao
{
	private PagedFlexibleSearchService pagedFlexibleSearchService;

	protected static final String CUSTOMER = "customer";
	protected static final String TODAY = "today";
	protected static final String SORT_BY_CREATIONTIME_DESC = "byCreationTime";
	protected static final String SORT_CUSTOMERS_BY_NAME_DESC = " ORDER BY {"+CustomerStreamModel._TYPECODE+":" + CustomerStreamModel.CODE + "} DESC";

	protected static final String CUSTOMER_STREAM_BY_USER = "SELECT {"+CustomerStreamModel._TYPECODE+":" + CustomerStreamModel.PK + "}"
			+ " FROM {"+CustomerStreamModel._TYPECODE+" as "+CustomerStreamModel._TYPECODE+"}"
			+ " WHERE  {"+CustomerStreamModel._TYPECODE+":"+CustomerStreamModel.SALESCONSULTANT+"} = ?" + CUSTOMER;

	public CustomerStreamDaoImpl(final String typeCode){
		super(typeCode);
	}

	public SearchPageData<CustomerStreamModel> getCustomerStreamByUser(final CustomerModel customerModel, final PageableData pageableData){
		return getCustomerStreamByUserBeforDate(customerModel, null, pageableData);
	}

	public SearchPageData<CustomerStreamModel> getCustomerStreamByUserBeforDate(final CustomerModel customerModel, Date date, final PageableData pageableData){
		final StringBuilder queryString = new StringBuilder();
		queryString.append(CUSTOMER_STREAM_BY_USER);
		final Map<String,Object> parameter = new HashMap<String, Object>();
		parameter.put(CUSTOMER,customerModel);

		if(date != null){
			queryString.append(" AND {"+CustomerStreamModel._TYPECODE+":"+CustomerStreamModel.CREATIONTIME+"} >= ?"+ TODAY);
			parameter.put(TODAY,date);
		}

		final List<SortQueryData> sortQueries = Arrays.asList(
				createSortQueryData(SORT_BY_CREATIONTIME_DESC, createQuery(queryString.toString(), SORT_CUSTOMERS_BY_NAME_DESC)));
		return getPagedFlexibleSearchService().search(sortQueries, SORT_BY_CREATIONTIME_DESC, parameter, pageableData);
	}

	public List<CustomerStreamModel> getCustomerStreamByCode(String code){
		List<CustomerStreamModel> customerStreamModels = find(Collections.singletonMap(CustomerStreamModel.CODE, (String) code));
		return customerStreamModels;
	}

	protected String createQuery(final String... queryClauses)
	{
		final StringBuilder queryBuilder = new StringBuilder();

		for (final String queryClause : queryClauses)
		{
			queryBuilder.append(queryClause);
		}

		return queryBuilder.toString();
	}

	protected SortQueryData createSortQueryData(final String sortCode, final String query)
	{
		final SortQueryData result = new SortQueryData();
		result.setSortCode(sortCode);
		result.setQuery(query);
		return result;
	}

	protected PagedFlexibleSearchService getPagedFlexibleSearchService()
	{
		return pagedFlexibleSearchService;
	}

	@Required
	public void setPagedFlexibleSearchService(final PagedFlexibleSearchService pagedFlexibleSearchService)
	{
		this.pagedFlexibleSearchService = pagedFlexibleSearchService;
	}
}
