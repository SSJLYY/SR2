package com.bp.alps.facades.populators.abstractOrder;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class VehicleProductDataPopulator implements Populator<QuotationData, AlpsCommerceOrderEntryParameter>
{
	@Resource
	private ProductService productService;

	@Override
	public void populate(QuotationData quotationData, AlpsCommerceOrderEntryParameter commerceQuotaionEntryParameter)
			throws ConversionException
	{
		if(quotationData.getCarPrice()!=null) commerceQuotaionEntryParameter.setPrice(Double.valueOf(quotationData.getCarPrice()));
		if(quotationData.getCarSalesPrice()!=null) commerceQuotaionEntryParameter.setActualPrice(Double.valueOf(quotationData.getCarSalesPrice()));
		commerceQuotaionEntryParameter.setQuantity(1);
		if(quotationData.getCarColor()!=null)
		{
			ProductModel product = productService.getProductForCode(quotationData.getCarColor());
			if (product != null)
				commerceQuotaionEntryParameter.setProduct(product);
		}
	}
}
