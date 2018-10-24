package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.AlpsOrderDao;
import com.bp.alps.core.data.order.SearchOrderParameter;
import com.bp.alps.core.enums.OrderType;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.core.enums.OrderStatus;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;

import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class AlpsOrderDaoImpl extends DefaultPagedGenericDao<VehicleOrderModel> implements AlpsOrderDao
{
	public AlpsOrderDaoImpl(String typeCode){
		super(typeCode);
	}

	@Override
	public SearchPageData<VehicleOrderModel> getOrderListByCurrentSales(String orderType, CustomerModel customerModel, PageableData pageableData)
	{
		SortParameters sortParameters = new SortParameters();
		sortParameters.addSortParameter(VehicleOrderModel.CREATIONTIME, SortParameters.SortOrder.DESCENDING);
		if(orderType!=null){
			Map<String,Object> params = new HashMap<>();
			params.put(VehicleOrderModel.CONSULTANT, customerModel);
			if(StringUtils.isNotBlank(orderType) && OrderType.valueOf(orderType)!=null){
				params.put(VehicleOrderModel.ORDERTYPE, OrderType.valueOf(orderType));
			}
			return find(params, sortParameters, pageableData);
		}
		return find(Collections.singletonMap(VehicleOrderModel.CONSULTANT, customerModel), sortParameters, pageableData);
	}

	public SearchPageData<VehicleOrderModel> searchForCurrentSalesOrders(SearchOrderParameter searchOrderParameter, CustomerModel customerModel, PageableData pageableData)
	{
		StringBuilder sql = new StringBuilder();
		StringBuilder appendWhere = new StringBuilder();
		Map<String,Object> paramenter = new HashMap<>();
		sql.append("SELECT {"+VehicleOrderModel._TYPECODE+":pk} FROM ");
		sql.append("{"+VehicleOrderModel._TYPECODE+" ");

		if(StringUtils.isNotBlank(searchOrderParameter.getCustomerName()) || StringUtils.isNotBlank(searchOrderParameter.getCustomerMobileNumber()))
		{
			sql.append(" JOIN "+CustomerModel._TYPECODE+" ON {"+CustomerModel._TYPECODE+":pk}={"+VehicleOrderModel._TYPECODE+":user} ");
			if(StringUtils.isNotBlank(searchOrderParameter.getCustomerName()))
			{
				appendWhere.append(" AND {"+CustomerModel._TYPECODE+":name} like ?userName ");
				StringBuilder username = new StringBuilder("%");
				username.append(searchOrderParameter.getCustomerName()).append("%");
				paramenter.put("userName", username.toString());
			}

			if(StringUtils.isNotBlank(searchOrderParameter.getCustomerMobileNumber()))
			{
				appendWhere.append(" AND {"+CustomerModel._TYPECODE+":mobileNumber} like ?mobileNumber ");
				StringBuilder mobileNumber = new StringBuilder("%");
				mobileNumber.append(searchOrderParameter.getCustomerMobileNumber()).append("%");
				paramenter.put("mobileNumber", mobileNumber.toString());
			}
		}
		sql.append("} WHERE {"+VehicleOrderModel._TYPECODE+":consultant} =?salesConsultant");
		paramenter.put("salesConsultant", customerModel);

		if(StringUtils.isNotBlank(searchOrderParameter.getOrderStatus()))
		{
			sql.append(" AND {"+VehicleOrderModel._TYPECODE+":status} =?orderStatus");
			paramenter.put("orderStatus", OrderStatus.valueOf(searchOrderParameter.getOrderStatus()));
		}

		if(StringUtils.isNotBlank(searchOrderParameter.getOrderType()))
		{
			sql.append(" AND {"+VehicleOrderModel._TYPECODE+":orderType} =?orderType");
			paramenter.put("orderType", OrderType.valueOf(searchOrderParameter.getOrderType()));
		}
		sql.append(appendWhere);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.addQueryParameters(paramenter);
		return getPagedFlexibleSearchService().search(query, pageableData);
	}

	public VehicleOrderModel getOrderByCode(String orderCode){
		PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(0);
		pageableData.setPageSize(1);
		SearchPageData<VehicleOrderModel> searchPageData = find(Collections.singletonMap(VehicleOrderModel.CODE, orderCode), pageableData);
		return CollectionUtils.isNotEmpty(searchPageData.getResults()) ? searchPageData.getResults().get(0) : null;
	}
}
