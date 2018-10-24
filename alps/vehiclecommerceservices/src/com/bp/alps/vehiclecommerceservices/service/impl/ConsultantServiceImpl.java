package com.bp.alps.vehiclecommerceservices.service.impl;

import com.bp.alps.vehiclecommerceservices.constants.VehiclecommerceservicesConstants;
import com.bp.alps.vehiclecommerceservices.dao.ConsultantDao;
import com.bp.alps.vehiclecommerceservices.service.ConsultantService;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConsultantServiceImpl extends DefaultCustomerAccountService implements ConsultantService, CustomerAccountService
{
	@Resource
	private ConsultantDao consultantDao;

	@Resource
	private ModelService modelService;

	public SearchPageData<CustomerModel> findAllCustomersByGroups(final List<String> groupKeys, final PageableData pageableData){
		return consultantDao.findAllCustomersByGroups(groupKeys,pageableData);
	}

	public UserModel getCurrentUser(){
		return getUserService().getCurrentUser();
	}

	public UserGroupModel getGroupForUid(String uid){
		return getUserService().getUserGroupForUID(uid);
	}

	public void saveCustomer(CustomerModel customerModel){
		modelService.save(customerModel);
	}

	public void initGroupForConsulatnt(CustomerModel customerModel){

		if (customerModel.getCustomerRole()!=null&&customerModel.getCustomerRole().getCode().equals("customer")){
			UserGroupModel userGroupModel = getGroupForUid(Config.getString(VehiclecommerceservicesConstants.CUSTOMER_GROUP_FOR_BUYER,"buyergroup"));
			Set<PrincipalGroupModel> userGroups = new HashSet<>(customerModel.getGroups());
			userGroups.add(userGroupModel);
			customerModel.setGroups(userGroups);
		}else{
			UserGroupModel userGroupModel = getGroupForUid(Config.getString(VehiclecommerceservicesConstants.CUSTOMER_GROUP_FOR_CONSULATNT,"salesconsultant"));
			Set<PrincipalGroupModel> userGroups = new HashSet<>(customerModel.getGroups());
			userGroups.add(userGroupModel);
			customerModel.setGroups(userGroups);
		}


	}
}
