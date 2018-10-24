package com.bp.alps.vehiclecommerceservices.service.impl;

import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.vehiclecommerceservices.constants.VehiclecommerceservicesConstants;
import com.bp.alps.vehiclecommerceservices.dao.AlpsCustomerDao;
import com.bp.alps.vehiclecommerceservices.enums.CustomerStatus;
import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;


public class AlpsCustomerServiceImpl implements AlpsCustomerService
{
	@Resource
	private AlpsCustomerDao alpsCustomerDao;

	@Resource
	private ModelService modelService;

	@Resource
	private BaseStoreService baseStoreService;

	@Resource
	private UserService userService;

	@Override
	public CustomerModel getCustomerForMobileNumber(String mobileNumber)
	{
		return alpsCustomerDao.getCustomerForMobileNumber(mobileNumber);
	}

	public CustomerModel getCustomerForUid(String uid)
	{
		UserModel userModel = userService.getUserForUID(uid);
		if(userModel instanceof CustomerModel){
			return (CustomerModel)userModel;
		}
		return null;
	}

	@Override
	public CustomerModel getCurrentSalesConsultant()
	{
		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel){
			return (CustomerModel)userModel;
		}
		return null;
	}

	public SearchPageData<CustomerModel> searchCustomer(CustomerSearchRequest customerSearchRequest, PageableData pageableData){
		return alpsCustomerDao.searchCustomer(customerSearchRequest.getName(), customerSearchRequest.getPhone(), customerSearchRequest.getSearchText(), customerSearchRequest.getCustomerType(), customerSearchRequest.getAttribute(), pageableData);
	}

	public CustomerModel createCustomer(){
		CustomerModel customerModel =  modelService.create(CustomerModel.class);
		customerModel.setStore(baseStoreService.getCurrentBaseStore());
		UserGroupModel userGroupModel = userService.getUserGroupForUID(Config.getString(VehiclecommerceservicesConstants.CUSTOMER_GROUP_FOR_SALES,"buyergroup"));
		Set<PrincipalGroupModel> userGroups = new HashSet<>();
		userGroups.add(userGroupModel);
		customerModel.setGroups(userGroups);
		UserModel userModel = userService.getCurrentUser();
		if(userModel!=null)
		{
			customerModel.setOwner(userModel);
		}
		return customerModel;
	}

	public void saveCustomer(CustomerModel customerModel){
		if(customerModel.getPk()==null){
			customerModel.setUid(customerModel.getMobileNumber());
			customerModel.setStatus(CustomerStatus.VALID);
		}
		AddressModel addressModel = customerModel.getDefaultShipmentAddress();

		if(addressModel!=null){
			if(customerModel.getPk()==null){
				customerModel.setDefaultShipmentAddress(null);
				modelService.save(customerModel);
			}
			addressModel.setOwner(customerModel);
			modelService.save(addressModel);
			customerModel.setDefaultShipmentAddress(addressModel);
		}

		modelService.save(customerModel);
	}
}
