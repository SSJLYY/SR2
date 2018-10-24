package com.bp.alps.vehiclecommercefacade.populators.product;

import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionQualifierData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class ProductDataPopulator implements Populator<ProductData,com.bp.alps.facades.data.product.ProductData>
{
	private static final String colorsProperty = "colors";

	@Override public void populate(ProductData productData, com.bp.alps.facades.data.product.ProductData productData2)
			throws ConversionException
	{

		if(CollectionUtils.isNotEmpty(productData.getCategories())){
			productData.getCategories().stream().forEach(categoryData -> {
				productData2.setCategoryCode(categoryData.getCode());
				return;
			});
		}
		productData2.setCode(productData.getCode());
		productData2.setName(productData.getName());

		//shaun:添加显示字段
		productData2.setMaterialGroup(productData.getMaterialGroup());
		productData2.setMaterialType(productData.getMaterialType());
		productData2.setSpecificationModel(productData.getSpecificationModel());
		productData2.setManualDirectoryIdentifier(productData.getManualDirectoryIdentifier());
		productData2.setMaintenanceItemCode(productData.getMaintenanceItemCode());
		productData2.setPricingReferenceMaterial(productData.getPricingReferenceMaterial());
		productData2.setUnitHourSalesPrice(productData.getUnitHourSalesPrice());
		productData2.setRecentlyModifiedPerson(productData.getRecentlyModifiedPerson());
		productData2.setArtificialMainType(productData.getArtificialMainType());
		productData2.setServicePackStatus(productData.getServicePackStatus());
		productData2.setOnlineDate(productData.getOnlineDate());
		productData2.setOfflineDate(productData.getOfflineDate());


		PriceData priceData = productData.getPrice();
		if(priceData!=null){
			productData2.setPrice(priceData.getValue().doubleValue());
			productData2.setCurrency(priceData.getCurrencyIso());
			if(StringUtils.isNotBlank(priceData.getSuggestedRetailPrice())){
				productData2.setSuggestedRetailPrice(Double.parseDouble(priceData.getSuggestedRetailPrice()));
			}

		}

		if(CollectionUtils.isNotEmpty(productData.getVariantOptions())){
			List<com.bp.alps.facades.data.product.ProductData> variantList = populateVariant(productData.getVariantOptions());
			productData2.setSubProduct(variantList);
		}
	}

	protected List<com.bp.alps.facades.data.product.ProductData> populateVariant(final List<VariantOptionData> variantOptionDataList){
		List<com.bp.alps.facades.data.product.ProductData> saleProductDataList = new ArrayList<>();
		for (VariantOptionData variantOptionData : variantOptionDataList)
		{
			com.bp.alps.facades.data.product.ProductData salesProductData = new com.bp.alps.facades.data.product.ProductData();
			if (variantOptionData.getPriceData() != null && variantOptionData.getPriceData().getValue() != null)
			{
				salesProductData.setPrice(variantOptionData.getPriceData().getValue().doubleValue());
			}
			salesProductData.setCode(variantOptionData.getCode());
			populateVariantAdditionText(salesProductData, variantOptionData);
			saleProductDataList.add(salesProductData);
		}
		return saleProductDataList;
	}

	protected void populateVariantAdditionText(final com.bp.alps.facades.data.product.ProductData salesProductData, VariantOptionData variantOptionData){
		for (VariantOptionQualifierData variantData : variantOptionData.getVariantOptionQualifiers())
		{
			if(variantData.getQualifier().equals(colorsProperty)){
				salesProductData.setColor(variantData.getValue());
				break;
			}
		}
	}
}
