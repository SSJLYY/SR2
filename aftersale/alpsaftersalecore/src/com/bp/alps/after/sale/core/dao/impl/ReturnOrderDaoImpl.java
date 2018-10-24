package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.dao.ReturnOrderDao;
import com.bp.alps.vehiclecommerceservices.dao.AlpsCustomerDao;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


public class ReturnOrderDaoImpl extends DefaultPagedGenericDao<ReturnRequestModel> implements ReturnOrderDao
{
	private FlexibleSearchService flexibleSearchService;

	@Resource
	private AlpsCustomerDao alpsCustomerDao;

	@Resource
	private UserService userService;

	protected static final String RETURN_ORDER_CODE = "SELECT {" + ReturnRequestModel.PK + "} FROM {" + ReturnRequestModel._TYPECODE + "} WHERE {" + ReturnRequestModel.CODE + "} = ?code ";

	protected static final String SERVICE_ORDER_SEARCH = "SELECT {" + ReturnRequestModel.PK + "} FROM {" + ReturnRequestModel._TYPECODE + "} WHERE 1=1 ";

	public ReturnOrderDaoImpl(){
		super(ReturnRequestModel._TYPECODE);
	}

	public ReturnRequestModel getReturnOrderByCode(String code){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(RETURN_ORDER_CODE);
		query.addQueryParameter("code", code);
		SearchResult<ReturnRequestModel> searchResult = getFlexibleSearchService().search(query);
		return searchResult.getCount()>0?searchResult.getResult().get(0):null;
	}

	public SearchPageData<ReturnRequestModel> getReturnOrderByCurrentServiceConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData){
		Map<String,Object> sqlParameter = new HashMap<>();
		StringBuilder sql = new StringBuilder(SERVICE_ORDER_SEARCH);

		if(StringUtils.isNotBlank(orderCode)){
			sql.append(" AND {" + ReturnRequestModel.CODE + "} = ?code");
			sqlParameter.put("code", orderCode);
		}

		if(StringUtils.isNotBlank(startTime)){
			sql.append(" AND {" + ReturnRequestModel.CREATIONTIME + "} >= ?creationtimeStart");
			sqlParameter.put("creationtimeStart", startTime);
		}

		if(StringUtils.isNotBlank(endTime)){
			sql.append(" AND {" + ReturnRequestModel.CREATIONTIME + "} <= ?creationtimeEnd");
			sqlParameter.put("creationtimeEnd", endTime);
		}

		CustomerModel customerModel = null;
		if(StringUtils.isNotBlank(mobile))
		{
			customerModel = alpsCustomerDao.getCustomerForMobileNumber(mobile);
		}
		if(customerModel == null && StringUtils.isNotBlank(username)){
			customerModel = alpsCustomerDao.getCustomerForName(username);
		}

		if(customerModel!=null)
		{
			sql.append(" AND {" + ReturnRequestModel.USER + "} = ?customer");
			sqlParameter.put("customer", customerModel);
		}

		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel)
		{
			sql.append(" AND {" + ReturnRequestModel.CONSULTANT + "} = ?salesconsultant");
			sqlParameter.put("salesconsultant", userModel);
		}

		if(status!=null){
			sql.append(" AND {" + ReturnRequestModel.STATUS + "} = ?status");
			sqlParameter.put("status", status);
		}

		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.addQueryParameters(sqlParameter);
		return getPagedFlexibleSearchService().search(query, pageableData);
	}

	public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	public FlexibleSearchService getFlexibleSearchService(){
		return this.flexibleSearchService;
	}
}
