package com.bp.alps.vehiclecommercefacade.populators.abstractOrder.output;

import com.bp.alps.facades.data.abstractOrder.EntryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.product.PriceService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


public class EntryDataOutputPopulator implements Populator<AbstractOrderEntryModel, EntryData>
{
	@Resource
	private ProductService productService;

	@Resource
	private PriceService priceService;

	@Override
	public void populate(AbstractOrderEntryModel entryModel, EntryData entryData)
			throws ConversionException
	{

		final ProductModel product = entryModel.getProduct();
		if(product!=null){
			entryData.setActualPrice(entryModel.getActualPrice());
			entryData.setQuantity(entryModel.getQuantity().intValue());
			List<PriceInformation> priceInformations = priceService.getPriceInformationsForProduct(product);
			if(CollectionUtils.isNotEmpty(priceInformations)){
				Optional<PriceInformation> optional = priceInformations.stream().findFirst();
				PriceInformation priceInformation = optional.get();
				Double priceValue = priceInformation.getPriceValue().getValue();
				entryData.setPrice(priceValue!=null ? priceValue : 0);
			}
			entryData.setCode(product.getCode());
			entryData.setName(product.getName());
		}
	}
}
