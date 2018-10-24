package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.dao.ServiceOrderDao;
import com.bp.alps.after.sale.core.enums.ServiceOrderType;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.facades.data.order.SearchServiceOrderRequest;
import com.bp.alps.vehiclecommerceservices.dao.AlpsCustomerDao;
import com.bp.alps.vehiclecommerceservices.dao.VehicleDao;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


public class ServiceOrderDaoImpl extends DefaultPagedGenericDao<ServiceOrderModel> implements ServiceOrderDao
{
	@Resource
	private AlpsCustomerDao alpsCustomerDao;

	@Resource
	private VehicleDao vehicleDao;

	@Resource
	private UserService userService;

	private FlexibleSearchService flexibleSearchService;

	protected static final String SERVICE_ORDER_SEARCH = "SELECT {" + ServiceOrderModel.PK + "} FROM {" + ServiceOrderModel._TYPECODE + "} WHERE {" + ServiceOrderModel.PARENTORDER + "} is null ";

	protected static final String SERVICE_SUB_ORDER_SEARCH = "SELECT {" + ServiceOrderModel.PK + "} FROM {" + ServiceOrderModel._TYPECODE + "} WHERE {" + ServiceOrderModel.PARENTORDER + "} = ?"+ServiceOrderModel.PARENTORDER+" order by {"+ServiceOrderModel.CODE+"} asc";

	protected static final String SERVICE_ORDER_CODE = "SELECT {" + ServiceOrderModel.PK + "} FROM {" + ServiceOrderModel._TYPECODE + "} WHERE {" + ServiceOrderModel.CODE + "} = ?code ";

	public ServiceOrderDaoImpl(String typeCode){
		super(typeCode);
	}

	public ServiceOrderModel getServiceOrderByCode(String code){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SERVICE_ORDER_CODE);
		query.addQueryParameter("code", code);
		SearchResult<ServiceOrderModel> searchResult = getFlexibleSearchService().search(query);
		return searchResult.getCount()>0?searchResult.getResult().get(0):null;
	}

	public SearchResult<ServiceOrderModel> getSubOrder(ServiceOrderModel serviceOrderModel){
		StringBuilder sql = new StringBuilder(SERVICE_SUB_ORDER_SEARCH);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.addQueryParameter(ServiceOrderModel.PARENTORDER, serviceOrderModel);
		return getFlexibleSearchService().search(query);
	}

	public SearchPageData<ServiceOrderModel> getServiceOrderByCurrentServiceConsultant(SearchServiceOrderRequest searchServiceOrderRequest, PageableData pageableData){
		Map<String,Object> sqlParameter = new HashMap<>();
		StringBuilder sql = new StringBuilder(SERVICE_ORDER_SEARCH);

		if(StringUtils.isNotBlank(searchServiceOrderRequest.getCode())){
			sql.append(" AND {" + ServiceOrderModel.CODE + "} = ?code");
			sqlParameter.put("code", searchServiceOrderRequest.getCode());
		}

		if(StringUtils.isNotBlank(searchServiceOrderRequest.getStartTime())){
			sql.append(" AND {" + ServiceOrderModel.CREATIONTIME + "} >= ?creationtimeStart");
			sqlParameter.put("creationtimeStart", searchServiceOrderRequest.getStartTime());
		}

		if(StringUtils.isNotBlank(searchServiceOrderRequest.getEndTime())){
			sql.append(" AND {" + ServiceOrderModel.CREATIONTIME + "} <= ?creationtimeEnd");
			sqlParameter.put("creationtimeEnd", searchServiceOrderRequest.getEndTime());
		}

		CustomerModel customerModel = null;
		if(StringUtils.isNotBlank(searchServiceOrderRequest.getCustomerPhoneNumber()))
		{
			customerModel = alpsCustomerDao.getCustomerForMobileNumber(searchServiceOrderRequest.getCustomerPhoneNumber());
		}
		if(customerModel == null && StringUtils.isNotBlank(searchServiceOrderRequest.getCustomer())){
			customerModel = alpsCustomerDao.getCustomerForName(searchServiceOrderRequest.getCustomer());
		}

		if(customerModel!=null)
		{
			sql.append(" AND {" + ServiceOrderModel.USER + "} = ?customer");
			sqlParameter.put("customer", customerModel);
		}

		if(StringUtils.isNotBlank(searchServiceOrderRequest.getLicensePlateNumber()))
		{
			VehicleInfoModel vehicleInfoModel = vehicleDao.getVehicleByLicensePlateNumber(searchServiceOrderRequest.getLicensePlateNumber());
			sql.append(" AND {" + ServiceOrderModel.VEHICLE + "} <= ?vehicle");
			sqlParameter.put("vehicle", vehicleInfoModel);
		}

		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel)
		{
			sql.append(" AND {" + ServiceOrderModel.CONSULTANT + "} = ?salesconsultant");
			sqlParameter.put("salesconsultant", userModel);
		}

		if(StringUtils.isNotBlank(searchServiceOrderRequest.getServiceType())){
			sql.append(" AND {" + ServiceOrderModel.SERVICETYPE + "} = ?serviceType");
			sqlParameter.put("serviceType", ServiceOrderType.valueOf(searchServiceOrderRequest.getServiceType()));
		}

		if(StringUtils.isNotBlank(searchServiceOrderRequest.getServiceType())){
			sql.append(" AND {" + ServiceOrderModel.STATUS + "} = ?status");
			sqlParameter.put("status", OrderStatus.valueOf(searchServiceOrderRequest.getStatus()));
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
