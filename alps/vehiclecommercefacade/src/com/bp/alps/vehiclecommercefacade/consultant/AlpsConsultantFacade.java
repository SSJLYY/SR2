package com.bp.alps.vehiclecommercefacade.consultant;

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;


public interface AlpsConsultantFacade extends CustomerFacade
{
	SearchPageData<CustomerModel> getUserByGroupUid(final Integer currentPage, Integer pagesize, String groupUid);

	void updateCustomer(final CustomerData customerData);
}
