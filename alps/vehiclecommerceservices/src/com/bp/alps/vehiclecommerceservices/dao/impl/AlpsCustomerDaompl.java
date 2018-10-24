package com.bp.alps.vehiclecommerceservices.dao.impl;

import com.bp.alps.vehiclecommerceservices.constants.VehiclecommerceservicesConstants;
import com.bp.alps.vehiclecommerceservices.dao.AlpsCustomerDao;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


public class AlpsCustomerDaompl extends DefaultPagedGenericDao<CustomerModel> implements AlpsCustomerDao
{
	@Resource
	private BaseStoreService baseStoreService;

	@Resource
	private EnumerationService enumerationService;

	@Resource
	private FlexibleSearchService flexibleSearchService;

	protected static final String CUSTOMER_QUERY = "SELECT {" + CustomerModel.PK + "} FROM {" + CustomerModel._TYPECODE
			+ "} WHERE {" + CustomerModel.MOBILENUMBER + "} = ?mobileNumber";

	protected static final String CUSTOMER_NAME_QUERY = "SELECT {" + CustomerModel.PK + "} FROM {" + CustomerModel._TYPECODE
			+ "} WHERE {" + CustomerModel.NAME + "} like ?name";

	protected static final String CUSTOMER_UID_QUERY = "SELECT {" + CustomerModel.PK + "} FROM {" + CustomerModel._TYPECODE
			+ "} WHERE {" + CustomerModel.UID + "} = ?uid";

	protected static final String SEARCH_CUSTOMER_QUERY = "SELECT {" + CustomerModel.PK + "} FROM {" + CustomerModel._TYPECODE
			+ " JOIN PrincipalGroupRelation as pg ON {pg.source} = {" + CustomerModel._TYPECODE + "." + CustomerModel.PK + "} "
			+ " JOIN PrincipalGroup as pgroup ON {pg.target} = {pgroup.pk}} WHERE {pgroup.uid} = ?groupuid ";

	public AlpsCustomerDaompl(String typeCode){
		super(typeCode);
	}

	public CustomerModel getCustomerForMobileNumber(final String mobileNumber)
	{
		validateParameterNotNull(mobileNumber, "mobile number must not be null");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(CUSTOMER_QUERY);
		query.addQueryParameter("mobileNumber", mobileNumber);
		final SearchResult<CustomerModel> result = flexibleSearchService.search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}

	public CustomerModel getCustomerForName(final String name)
	{
		validateParameterNotNull(name, "name must not be null");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(CUSTOMER_NAME_QUERY);
		query.addQueryParameter("name", name);
		final SearchResult<CustomerModel> result = flexibleSearchService.search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}

	public CustomerModel getCustomerForUid(String uid)
	{
		validateParameterNotNull(uid, "uid must not be null");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(CUSTOMER_UID_QUERY);
		query.addQueryParameter("uid", uid);
		final SearchResult<CustomerModel> result = flexibleSearchService.search(query);
		return CollectionUtils.isNotEmpty(result.getResult()) ? result.getResult().get(0) : null;
	}

	public SearchPageData<CustomerModel> searchCustomer(final String name, final String phone, final String searchText, final String customerType, final String customerAttr, PageableData pageableData)
	{
		Map<String,Object> sqlParameter = new HashMap<>();
		StringBuilder stringBuilder = new StringBuilder(SEARCH_CUSTOMER_QUERY);
		if(StringUtils.isNotBlank(name)){
			stringBuilder.append(" AND {" + CustomerModel.NAME + "} like ?name");
			StringBuilder searchNameBuilder = new StringBuilder();
			searchNameBuilder.append("%").append(name).append("%");
			sqlParameter.put("name",searchNameBuilder.toString());
		}
		if(StringUtils.isNotBlank(phone)){
			stringBuilder.append(" AND {" + CustomerModel.MOBILENUMBER + "} like ?mobileNumber");
			StringBuilder searchPhoneBuilder = new StringBuilder();
			searchPhoneBuilder.append("%").append(phone).append("%");
			sqlParameter.put("phone",searchPhoneBuilder.toString());
		}
		if(StringUtils.isNotBlank(searchText)){
			stringBuilder.append(" AND ({" + CustomerModel.MOBILENUMBER + "} like ?mobileNumber");
			stringBuilder.append(" OR {" + CustomerModel.NAME + "} like ?name)");
			StringBuilder searchTextBuilder = new StringBuilder();
			searchTextBuilder.append("%").append(searchText).append("%");
			sqlParameter.put("mobileNumber",searchTextBuilder.toString());
			sqlParameter.put("name",searchTextBuilder.toString());
		}
		if(StringUtils.isNotBlank(customerType)){
			try
			{
				CustomerType customerTypeEnum = enumerationService.getEnumerationValue(CustomerType.class, customerType);
				if (customerTypeEnum != null)
				{
					stringBuilder.append(" AND {" + CustomerModel.TYPE + "} = ?customerType");
					sqlParameter.put("customerType", customerTypeEnum);
				}
			}catch (Exception e){
			}
		}
		if(StringUtils.isNotBlank(customerAttr)){
			stringBuilder.append(" AND {" + CustomerModel.ATTRIBUTE + "} = ?customerAttr");
			StringBuilder searchTextBuilder = new StringBuilder();
			sqlParameter.put("customerAttr",customerAttr);
		}
		stringBuilder.append(" Order by {" + CustomerModel.NAME + "} ASC");
		sqlParameter.put("groupuid", Config.getString(VehiclecommerceservicesConstants.CUSTOMER_GROUP_FOR_SALES,"buyergroup"));
		final FlexibleSearchQuery query = new FlexibleSearchQuery(stringBuilder.toString());
		query.addQueryParameters(sqlParameter);
		return getPagedFlexibleSearchService().search(query, pageableData);
	}
}
