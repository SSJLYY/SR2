package com.bp.alps.vehiclecommercefacade.consultant.impl;

import com.bp.alps.facades.data.SalesStaffListResponse;
import com.bp.alps.vehiclecommercefacade.consultant.AlpsConsultantFacade;
import com.bp.alps.vehiclecommerceservices.enums.CustomerRole;
import com.bp.alps.vehiclecommerceservices.service.ConsultantService;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.store.services.BaseStoreService;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collections;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;


public class AlpsConsultantFacadeImpl extends DefaultCustomerFacade implements AlpsConsultantFacade
{
	private final static Logger LOG = Logger.getLogger( AlpsConsultantFacadeImpl.class.getName() );

	@Resource
	private ConsultantService consultantService;

	@Resource
	private BaseStoreService baseStoreService;

	@Override
	public void register(final RegisterData registerData) throws DuplicateUidException
	{
		validateParameterNotNullStandardMessage("registerData", registerData);
		Assert.hasText(registerData.getFirstName(), "The field [FirstName] cannot be empty");
		Assert.hasText(registerData.getLogin(), "The field [Login] cannot be empty");

		final CustomerModel newCustomer = getModelService().create(CustomerModel.class);
		newCustomer.setName(registerData.getFirstName());
		try
		{
			setUidForRegister(registerData, newCustomer);
			newCustomer.setStore(baseStoreService.getCurrentBaseStore());
			newCustomer.setSessionLanguage(getCommonI18NService().getCurrentLanguage());
			newCustomer.setSessionCurrency(getCommonI18NService().getCurrentCurrency());
			//customerRole
			if(registerData.getPathId()!=null&&"buyergroup".equals(registerData.getPathId())){
				newCustomer.setCustomerRole(CustomerRole.valueOf("customer"));
			}

			consultantService.initGroupForConsulatnt(newCustomer);
			getCustomerAccountService().register(newCustomer, registerData.getPassword());
		}catch (DuplicateUidException e){
			LOG.info("register error", e);
			throw new DuplicateUidException(e);
		}
	}

	public SearchPageData<CustomerModel> getUserByGroupUid(final Integer currentPage, Integer pagesize, String groupUid){
		PageableData pageableData = populatorPageable(currentPage,pagesize);
		SalesStaffListResponse salesStaffListResponse = new SalesStaffListResponse();
		SearchPageData<CustomerModel> searchPageData = consultantService.findAllCustomersByGroups(Collections.singletonList(groupUid),
				pageableData);
		return searchPageData;
	}

	public UserModel getCurrentUser(){
		return consultantService.getCurrentUser();
	}

	public PageableData populatorPageable(final Integer currentPage, Integer pagesize){
		final PageableData pageableData = new PageableData();
		pageableData.setPageSize(pagesize!=null?pagesize:20);
		pageableData.setCurrentPage(currentPage!=null?currentPage:0);
		return pageableData;
	}

	public void updateCustomer(final CustomerData customerData)
	{
		validateDataBeforeUpdate(customerData);
		final CustomerModel customer = getCurrentSessionCustomer();
		customer.setName(customerData.getName());
		customer.setMobileNumber(customerData.getMobileNumber());
		customer.setDescription(customerData.getDescription());
		consultantService.saveCustomer(customer);
	}

	protected void validateDataBeforeUpdate(final CustomerData customerData)
	{
		validateParameterNotNullStandardMessage("customerData", customerData);
		Assert.hasText(customerData.getUid(), "The field [Uid] cannot be empty");
	}
}
