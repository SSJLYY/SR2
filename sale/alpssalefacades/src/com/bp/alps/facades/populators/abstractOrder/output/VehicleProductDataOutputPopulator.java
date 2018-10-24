package com.bp.alps.facades.populators.abstractOrder.output;

import com.bp.alps.core.enums.OrderType;
import com.bp.alps.core.model.VehicleOrderModel;
import com.bp.alps.facades.data.quotation.QuotationListData;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.product.PriceService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.variants.model.VariantProductModel;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


public class VehicleProductDataOutputPopulator implements Populator<AbstractOrderModel, QuotationListData>
{
	@Resource
	private ProductService productService;

	@Resource
	private PriceService priceService;

	@Override
	public void populate(AbstractOrderModel abstractOrderModel, final QuotationListData quotationData)
			throws ConversionException
	{
		if(abstractOrderModel instanceof VehicleOrderModel){
			VehicleOrderModel vehicleOrder = (VehicleOrderModel)abstractOrderModel;
			if(vehicleOrder.getOrderType().equals(OrderType.INCREMENT)) return;
		}
		if(CollectionUtils.isNotEmpty(abstractOrderModel.getEntries())){
			abstractOrderModel.getEntries().stream()
				.filter(entry -> entry.getProduct()!=null)
				.filter(entry -> entry.getProduct().getItemtype().equals("VehicleProduct") || entry.getProduct().getItemtype().equals("VehicleVariantProduct"))
				.map(entry -> {
					ProductModel productModel = entry.getProduct();
					quotationData.setCarSalesPrice(entry.getActualPrice().toString());
					if(productModel instanceof VariantProductModel){
						VariantProductModel variantProductModel = (VariantProductModel)productModel;
						ProductModel baseProduct = variantProductModel.getBaseProduct();
						quotationData.setCarModel(baseProduct.getCode());
						quotationData.setCarModelName(baseProduct.getName());
						if(CollectionUtils.isNotEmpty(baseProduct.getSupercategories())){
							Optional<CategoryModel> optionalSuperCategory = baseProduct.getSupercategories().stream().findFirst();
							CategoryModel superCategory = optionalSuperCategory.get();
							quotationData.setVehicle(superCategory.getCode());
							quotationData.setVehicleName(superCategory.getName());
							if(CollectionUtils.isNotEmpty(superCategory.getSupercategories())){
								Optional<CategoryModel> optionalRootCategory = superCategory.getSupercategories().stream().findFirst();
								CategoryModel rootCategory = optionalRootCategory.get();
								quotationData.setVehicleBrand(rootCategory.getCode());
								quotationData.setVehicleBrandName(rootCategory.getName());
							}
						}
					}
					quotationData.setCarColor(productModel.getCode());
					quotationData.setCarColorName(productModel.getName());
					List<PriceInformation> priceInformations = priceService.getPriceInformationsForProduct(productModel);
					if(CollectionUtils.isNotEmpty(priceInformations)){
						Optional<PriceInformation> optional = priceInformations.stream().findFirst();
						PriceInformation priceInformation = optional.get();
						Double priceValue = priceInformation.getPriceValue().getValue();
						quotationData.setCarPrice(priceValue!=null?priceValue.toString():"0");
					}
					return quotationData;
				}).findAny();
		}
	}
}
