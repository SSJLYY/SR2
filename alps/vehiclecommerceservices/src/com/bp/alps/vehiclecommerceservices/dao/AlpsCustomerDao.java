package com.bp.alps.vehiclecommerceservices.dao;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;


public interface AlpsCustomerDao
{
	CustomerModel getCustomerForMobileNumber(final String mobileNumber);

	CustomerModel getCustomerForUid(String uid);

	CustomerModel getCustomerForName(final String name);

	SearchPageData<CustomerModel> searchCustomer(final String name, final String phone, final String searchText, final String customerType, final String customerAttr,
			final PageableData pageableData);
}
