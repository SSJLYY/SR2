package com.bp.after.sale.facades.populators.stocklevel;

import com.bp.after.sale.facades.data.StockLevelData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class StockLevelPopulator implements Populator<StockLevelModel, StockLevelData>
{
	@Override
	public void populate(StockLevelModel stockLevelModel, StockLevelData stockLevelData) throws ConversionException
	{
		stockLevelData.setProductCode(stockLevelModel.getProductCode());
		final Integer available =  stockLevelModel.getAvailable();
		final Integer reserved = stockLevelModel.getReserved();
		stockLevelData.setStockLevel(available - reserved);
		if(stockLevelModel.getWarehouse()!=null)
		{
			stockLevelData.setWarehouseName(stockLevelModel.getWarehouse().getName());
		}

	}
}
