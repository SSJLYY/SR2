package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.dao.OrderRelatedRolesDao;
import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;


//DefaultGenericDao
public class OrderRelatedRolesDaoImpl extends DefaultGenericDao<OrderRelatedRolesModel> implements OrderRelatedRolesDao
{
	protected static final String RelatedRole_CODE = "SELECT {" + OrderRelatedRolesModel.PK + "} FROM {" + OrderRelatedRolesModel._TYPECODE + "} WHERE {" + OrderRelatedRolesModel.ORDER + "} = ?order ";

	public OrderRelatedRolesDaoImpl(String typecode){
		super(typecode);
	}

	public List<OrderRelatedRolesModel> getRelatedRoleByOrder(OrderModel orderModel){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(RelatedRole_CODE);
		query.addQueryParameter("order", orderModel);
		SearchResult<OrderRelatedRolesModel> searchResult = getFlexibleSearchService().search(query);
		return searchResult.getResult();
	}
}
