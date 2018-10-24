package com.bp.after.sale.facades.stocklevel;

import com.bp.after.sale.facades.data.StockLevelData;

import java.util.Collection;


public interface StockLevelFacade
{
	Collection<StockLevelData> getAllStockLevelByProductCode(String productCode);
}
