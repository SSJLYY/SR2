package com.bp.alps.vehiclecommerceservices.dao.impl;

import com.bp.alps.vehiclecommerceservices.model.Vehicle2UserModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.dao.VehicleDao;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.commerceservices.search.dao.impl.DefaultPagedGenericDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;


public class VehicleDaoImpl extends DefaultPagedGenericDao<VehicleInfoModel> implements VehicleDao
{
	protected static final String SEARCH_VEHICLE_QUERY = "SELECT {" + VehicleInfoModel.PK + "} FROM {" + VehicleInfoModel._TYPECODE
			+ " ";

	protected static final String SEARCH_RELATED_VEHICLE_QUERY = "SELECT {" + VehicleInfoModel.PK + "} FROM {"
			+ VehicleInfoModel._TYPECODE
			+ " join "+ Vehicle2UserModel._TYPECODE +" on {"+VehicleInfoModel._TYPECODE+":"+VehicleInfoModel.PK+"} = {"+Vehicle2UserModel._TYPECODE+":"+Vehicle2UserModel.VEHICLEINFO+"} "
			+ " join "+ CustomerModel._TYPECODE +" on {"+CustomerModel._TYPECODE+":"+CustomerModel.PK+"} = {"+Vehicle2UserModel._TYPECODE+":"+Vehicle2UserModel.USER+"} }"
			+ " WHERE {"+CustomerModel._TYPECODE+":"+CustomerModel.UID+"} = ?customerUid ";

	public VehicleDaoImpl(String typeCode){
		super(typeCode);
	}

	public SearchPageData<VehicleInfoModel> getRelatedVehiclesByCustomerUid(String code, PageableData pageableData){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(SEARCH_RELATED_VEHICLE_QUERY);
		query.addQueryParameter("customerUid",code);
		return getPagedFlexibleSearchService().search(query, pageableData);
	}

	@Override
	public SearchPageData<VehicleInfoModel> searchVehicle(String customerName, String mobileNumber, String vehicleCode, String vinCode, String licensePlateNumber, String beforetimeKey, PageableData pageableData)
	{
		Map<String,Object> sqlParameter = new HashMap<>();
		StringBuilder sql = new StringBuilder(SEARCH_VEHICLE_QUERY);
		StringBuilder where = new StringBuilder("WHERE 1=1");
		if(StringUtils.isNotBlank(customerName)){
			sql.append(" JOIN "+CustomerModel._TYPECODE+" ON {"+CustomerModel._TYPECODE+":"+CustomerModel.PK+"}={"+VehicleInfoModel._TYPECODE+":"+VehicleInfoModel.CUSTOMER+"}");
			where.append(" AND {" + CustomerModel._TYPECODE + ":" + CustomerModel.NAME + "} = ?customerName");
			sqlParameter.put("customerName", customerName);
		}
		if(StringUtils.isNotBlank(mobileNumber)){
			sql.append(" JOIN "+CustomerModel._TYPECODE+" ON {"+CustomerModel._TYPECODE+":"+CustomerModel.PK+"}={"+VehicleInfoModel._TYPECODE+":"+VehicleInfoModel.CUSTOMER+"}");
			where.append(" AND {" + CustomerModel._TYPECODE + ":" + CustomerModel.MOBILENUMBER + "} = ?mobileNumber");
			sqlParameter.put("mobileNumber", mobileNumber);
		}
		if(StringUtils.isNotBlank(vehicleCode)){
			where.append(" AND {" + VehicleInfoModel.CODE + "} = ?code");
			sqlParameter.put("code", vehicleCode);
		}
		if(StringUtils.isNotBlank(licensePlateNumber)){
			where.append(" AND {" + VehicleInfoModel.LICENSEPLATENUMBER + "} = ?licensePlateNumber");
			sqlParameter.put("licensePlateNumber", licensePlateNumber);
		}
		if(StringUtils.isNotBlank(vinCode)){
			where.append(" AND {" + VehicleInfoModel.VINNUMBER + "} = ?vinCode");
			sqlParameter.put("vinCode", vinCode);
		}
		if(StringUtils.isNotBlank(beforetimeKey)){
			Date filterDate = DateFormatUtils.getBeforetimeKeyDate(beforetimeKey);
			if(!filterDate.equals(new Date()))
			{
				where.append(" AND {" + VehicleInfoModel.CREATIONTIME + "} >= ?creationtime");
				sqlParameter.put("creationtime", DateFormatUtils.getBeforetimeKeyDate(beforetimeKey));
			}
		}
		sql.append(" } ").append(where);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql.toString());
		query.addQueryParameters(sqlParameter);
		return getPagedFlexibleSearchService().search(query, pageableData);
	}

	@Override
	public VehicleInfoModel getVehicleByCode(String code)
	{
		PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(0);
		pageableData.setPageSize(1);
		SearchPageData<VehicleInfoModel> searchPageData = find(Collections.singletonMap(VehicleInfoModel.CODE, code), pageableData);
		return CollectionUtils.isNotEmpty(searchPageData.getResults()) ? searchPageData.getResults().get(0) : null;
	}

	public VehicleInfoModel getVehicleByLicensePlateNumber(String number)
	{
		PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(0);
		pageableData.setPageSize(1);
		SearchPageData<VehicleInfoModel> searchPageData = find(Collections.singletonMap(VehicleInfoModel.LICENSEPLATENUMBER, number), pageableData);
		return CollectionUtils.isNotEmpty(searchPageData.getResults()) ? searchPageData.getResults().get(0) : null;
	}
}
