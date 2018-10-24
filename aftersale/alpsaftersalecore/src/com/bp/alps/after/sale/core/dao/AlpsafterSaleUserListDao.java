package com.bp.alps.after.sale.core.dao;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface AlpsafterSaleUserListDao {

    SearchPageData<CustomerModel> findAllCustomersByGroups(final List<String> groupKeys,final PageableData pageableData);


    SearchPageData<CustomerModel> getAllCustomerId(PageableData pageableData);
}
