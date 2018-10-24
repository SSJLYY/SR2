package com.bp.alps.after.sale.core.service;

import com.bp.alps.after.sale.core.model.CustomNavModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;


public interface CustomNavService
{
	List<CustomNavModel> getCustomNav();

	Boolean setCustomNavByCode(String[] customNavCode);
}
