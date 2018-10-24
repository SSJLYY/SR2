package com.bp.alps.core.dao;

import com.bp.alps.core.model.MarketActivityModel;

import java.util.List;


public interface MarketActivityDao
{
	MarketActivityModel getMarketActivityByCode(String code);

	List<MarketActivityModel> getAllMarketActivity();

	List<MarketActivityModel> searchMarketActivity(final String name);
}
