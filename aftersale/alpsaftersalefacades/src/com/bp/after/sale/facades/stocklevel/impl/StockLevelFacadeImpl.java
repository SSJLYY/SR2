package com.bp.after.sale.facades.stocklevel.impl;

import com.bp.after.sale.facades.data.StockLevelData;
import com.bp.after.sale.facades.stocklevel.StockLevelFacade;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.stock.StockService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class StockLevelFacadeImpl implements StockLevelFacade
{
	@Resource
	private StockService stockService;

	@Resource
	private ProductService productService;

	@Resource
	private Converter stockLevelConverter;

	public Collection<StockLevelData> getAllStockLevelByProductCode(String productCode){
		ProductModel productModel = productService.getProductForCode(productCode);
		if(productModel!=null)
		{
			Collection<StockLevelModel> stockLevels = stockService.getAllStockLevels(productModel);
			Collection<StockLevelData> stockLevelData = stockLevelConverter.convertAll(stockLevels);
			return stockLevelData;
		}
		return new ArrayList<>();
	}
}
