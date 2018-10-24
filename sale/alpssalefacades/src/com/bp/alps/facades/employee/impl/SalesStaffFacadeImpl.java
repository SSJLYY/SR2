package com.bp.alps.facades.employee.impl;

import com.bp.alps.facades.constants.AlpssalefacadesConstants;
import com.bp.alps.facades.data.SalesStaffData;
import com.bp.alps.facades.data.SalesStaffListResponse;
import com.bp.alps.facades.employee.SalesStaffFacade;
import com.bp.alps.vehiclecommercefacade.consultant.impl.AlpsConsultantFacadeImpl;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;


public class SalesStaffFacadeImpl extends AlpsConsultantFacadeImpl implements SalesStaffFacade
{
	@Resource
	private Converter<CustomerModel,SalesStaffData> salesStaffConverter;

	public SalesStaffListResponse getInStoreReceptionist(final Integer currentPage, Integer pagesize){
		return getEmployeeByGroupUid(currentPage, pagesize, AlpssalefacadesConstants.Customer.inStoreReceptionistGroup);
	}

	public SalesStaffListResponse getSalesConsultant(final Integer currentPage, Integer pagesize){

		return getEmployeeByGroupUid(currentPage, pagesize, AlpssalefacadesConstants.Customer.salesConsultantGroup);
	}

	protected SalesStaffListResponse getEmployeeByGroupUid(final Integer currentPage, Integer pagesize, String groupUid){
		SalesStaffListResponse salesStaffListResponse = new SalesStaffListResponse();
		try
		{
			SearchPageData<CustomerModel> searchPageData = getUserByGroupUid(currentPage, pagesize, groupUid);
			List<SalesStaffData> salesStaffDatas = salesStaffConverter.convertAll(searchPageData.getResults());
			salesStaffListResponse.setSuccess(true);
			salesStaffListResponse.setSalesList(salesStaffDatas);
		}catch (Exception e){
			salesStaffListResponse.setSuccess(false);
			salesStaffListResponse.setMessage(e.getMessage());
		}
		return salesStaffListResponse;
	}
}
