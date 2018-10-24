package com.bp.alps.vehiclecommerceservices.service;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;

import java.util.List;


public interface ConsultantService
{
	SearchPageData<CustomerModel> findAllCustomersByGroups(final List<String> groupKeys, final PageableData pageableData);

	UserModel getCurrentUser();

	UserGroupModel getGroupForUid(String uid);

	void saveCustomer(CustomerModel customerModel);

	void initGroupForConsulatnt(CustomerModel customerModel);
}
