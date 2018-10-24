package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.dao.IncrementOrderDao;
import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;


public class IncrementOrderDaoImpl extends DefaultPagedGenericDao<IncrementOrderModel> implements IncrementOrderDao
{
	private FlexibleSearchService flexibleSearchService;

	protected static final String increment_request = "SELECT {" + IncrementOrderModel.PK + "} FROM {" + IncrementOrderModel._TYPECODE + " ";

	public IncrementOrderDaoImpl(){
		super(IncrementOrderModel._TYPECODE);
	}

	public IncrementOrderModel getIncrementOrderByCode(String code){
		StringBuilder sql = new StringBuilder(increment_request);
		sql.append(" } WHERE  {"+IncrementOrderModel.CODE+"} =?code");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.addQueryParameter("code", code);
		SearchResult<IncrementOrderModel> searchResult = flexibleSearchService.search(query);
		return searchResult.getCount()>0 ? searchResult.getResult().get(0) : null;
	}

	public SearchPageData<IncrementOrderModel> getIncrementOrderByCurrentConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData){
		StringBuilder sql = new StringBuilder(increment_request);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		HashMap paramMap = new HashMap();

		if(status!=null){
			where.append(" AND {" + IncrementOrderModel.STATUS + "} =?status ");
			paramMap.put("status", status);
		}

		if(StringUtils.isNotBlank(orderCode)){
			where.append(" AND {" + IncrementOrderModel.CODE + "} =?code ");
			paramMap.put("code", orderCode);
		}

		if(StringUtils.isNotBlank(username) || StringUtils.isNotBlank(mobile)){
			sql.append(" JOIN " + CustomerModel._TYPECODE + " ON {"+CustomerModel._TYPECODE+":"+CustomerModel.PK+"} = {"+IncrementOrderModel._TYPECODE+":"+IncrementOrderModel.USER+"}" );
			if(StringUtils.isNotBlank(username))
			{
				where.append(" AND {" + CustomerModel._TYPECODE+ ":"+CustomerModel.NAME+"} =?username ");
				paramMap.put("username", username);
			}
			if(StringUtils.isNotBlank(mobile))
			{
				where.append(" AND {" + CustomerModel._TYPECODE+ ":"+CustomerModel.MOBILENUMBER+"} =?mobile ");
				paramMap.put("mobile", mobile);
			}
		}

		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			where.append(" AND {" + IncrementOrderModel.CREATIONTIME + "} >=?starttime ");
			where.append(" AND {" + IncrementOrderModel.CREATIONTIME + "} <=?endtime ");
			paramMap.put("starttime", startTime);
			paramMap.put("endtime", endTime);
		}

		sql.append("} ").append(where.toString()).append(" Order BY {" + IncrementOrderModel.CREATIONTIME + "} DESC");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.addQueryParameters(paramMap);
		return getPagedFlexibleSearchService().search(query, pageableData);
	}

	public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}
}
