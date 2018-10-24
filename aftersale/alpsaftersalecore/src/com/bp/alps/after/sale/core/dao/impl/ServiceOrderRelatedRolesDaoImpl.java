package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.dao.ServiceOrderRelatedRolesDao;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;


//DefaultGenericDao
public class ServiceOrderRelatedRolesDaoImpl extends DefaultGenericDao<ServiceOrderRelatedRolesModel> implements ServiceOrderRelatedRolesDao
{
	protected static final String RelatedRole_CODE = "SELECT {" + ServiceOrderRelatedRolesModel.PK + "} FROM {" + ServiceOrderRelatedRolesModel._TYPECODE + "} WHERE {" + ServiceOrderRelatedRolesModel.SERVICEORDER + "} = ?serviceOrder ";

	public ServiceOrderRelatedRolesDaoImpl(String typecode){
		super(typecode);
	}

	public List<ServiceOrderRelatedRolesModel> getRelatedRoleByOrder(ServiceOrderModel serviceOrderModel){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(RelatedRole_CODE);
		query.addQueryParameter("serviceOrder", serviceOrderModel);
		SearchResult<ServiceOrderRelatedRolesModel> searchResult = getFlexibleSearchService().search(query);
		return searchResult.getResult();
	}
}
