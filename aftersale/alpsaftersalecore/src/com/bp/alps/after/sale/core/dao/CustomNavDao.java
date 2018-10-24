package com.bp.alps.after.sale.core.dao;

import com.bp.alps.after.sale.core.model.CustomNavModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.SearchResult;


public interface CustomNavDao
{
	SearchResult<CustomNavModel> getCustomNav(CustomerModel customerModel);
}
