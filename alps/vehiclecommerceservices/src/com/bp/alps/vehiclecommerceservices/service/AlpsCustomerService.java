package com.bp.alps.vehiclecommerceservices.service;

import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;


public interface AlpsCustomerService
{
	CustomerModel getCustomerForMobileNumber(final String mobileNumber);

	CustomerModel createCustomer();

	CustomerModel getCustomerForUid(String uid);

	void saveCustomer(CustomerModel customerModel);

	CustomerModel getCurrentSalesConsultant();

	SearchPageData<CustomerModel> searchCustomer(CustomerSearchRequest customerSearchRequest,
			PageableData pageableData);
}
