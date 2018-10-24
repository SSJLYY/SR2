package com.bp.alps.vehiclecommercefacade.populators.abstractOrder;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.facades.data.abstractOrder.EntryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class EntryDataPopulator implements Populator<EntryData, AlpsCommerceOrderEntryParameter>
{
	@Resource
	private ProductService productService;

	@Override
	public void populate(EntryData entryData, AlpsCommerceOrderEntryParameter commerceQuotaionEntryParameter)
			throws ConversionException
	{
		ProductModel product = productService.getProductForCode(entryData.getCode());
		if(product!=null) commerceQuotaionEntryParameter.setProduct(product);
		commerceQuotaionEntryParameter.setQuantity(entryData.getQuantity());
		commerceQuotaionEntryParameter.setActualPrice(entryData.getActualPrice());
		commerceQuotaionEntryParameter.setPrice(entryData.getPrice());
		if(product!=null) commerceQuotaionEntryParameter.setProductCode(product.getCode());
	}
}
