package com.bp.after.sale.facades.customNav;

import com.bp.after.sale.facades.data.CustomNavList;


public interface CustomNavFacade
{
	CustomNavList getCustomNavList();

	Boolean setCustomNavByCode(String[] customNavCode);
}
