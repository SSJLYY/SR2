package com.bp.alps.vehiclecommerceservices.dao.impl;

import com.bp.alps.vehiclecommerceservices.dao.ConsultantDao;
import de.hybris.platform.commerceservices.search.flexiblesearch.data.SortQueryData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ConsultantDaoImpl extends DefaultPagedGenericDao<CustomerModel> implements ConsultantDao
{
	public static final String SORT_BY_NAME_ASC = "byNameAsc";
	public static final String SORT_BY_NAME_DESC = "byNameDesc";
	private static final String SORT_CUSTOMERS_BY_NAME_ASC = " ORDER BY {m." + CustomerModel.NAME + "} ASC";
	private static final String SORT_CUSTOMERS_BY_NAME_DESC = " ORDER BY {m." + CustomerModel.NAME + "} DESC";

	public ConsultantDaoImpl(final String typeCode)
	{
		super(typeCode);
	}

	protected static final String GROUPS_UID = "groupsUid";

	protected static final String CUSTOMERS_PER_STORE = "SELECT {m." + CustomerModel.PK + "}, {m." + CustomerModel.NAME + "}"
			+ " FROM {PrincipalGroupRelation as pg JOIN PrincipalGroup as p ON {pg.target} = {p.pk} JOIN " + CustomerModel._TYPECODE
			+ " as m  ON {pg.source} = {m." + CustomerModel.PK + "}}" + " WHERE  {p.uid} in ( ?" + GROUPS_UID + " )";

	public SearchPageData<CustomerModel> findAllCustomersByGroups(final List<String> groupKeys,
			final PageableData pageableData){
		final List<SortQueryData> sortQueries = Arrays.asList(
				createSortQueryData(SORT_BY_NAME_ASC, createQuery(CUSTOMERS_PER_STORE, SORT_CUSTOMERS_BY_NAME_ASC)),
				createSortQueryData(SORT_BY_NAME_DESC, createQuery(CUSTOMERS_PER_STORE, SORT_CUSTOMERS_BY_NAME_DESC)));

		return getPagedFlexibleSearchService().search(sortQueries, SORT_BY_NAME_ASC, Collections.singletonMap(GROUPS_UID, groupKeys), pageableData);
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
}
