package com.bp.alps.vehiclecommercefacade.populators.product;

import com.bp.alps.facades.data.category.CategoryListData;
import com.bp.alps.facades.data.product.ProductSearchResponse;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


public class ProductSearchResponsePopuplator implements Populator<SearchPageData, ProductSearchResponse>
{
	@Resource
	private Converter<FacetValueData, CategoryListData> categoryListDataConverter;

	@Resource
	private Converter<ProductData, com.bp.alps.facades.data.product.ProductData> productDataConverter;

	@Override
	public void populate(SearchPageData source, ProductSearchResponse target) throws ConversionException
	{
		if(source instanceof ProductCategorySearchPageData){
			ProductCategorySearchPageData productCategorySearchPageData = (ProductCategorySearchPageData)source;

			populateCategory(productCategorySearchPageData, target);

			populateProduct(productCategorySearchPageData, target);

			PaginationData paginationData = productCategorySearchPageData.getPagination();
			target.setTotalPage(paginationData.getNumberOfPages());
			target.setSuccess(true);
		}
	}

	protected void populateCategory(ProductCategorySearchPageData source, ProductSearchResponse productSearchResponse){
		List<FacetData> facets =  source.getFacets();
		CategoryListData categoryListData = new CategoryListData();
		if(CollectionUtils.isNotEmpty(facets))
		{
			facets.stream().forEach(facetData -> {
				if (CollectionUtils.isNotEmpty(facetData.getValues()))
				{
					facetData.getValues().stream()
							.forEach(value -> categoryListDataConverter.convert((FacetValueData) value, categoryListData));
				}
			});
		}
		productSearchResponse.setCategoryList(categoryListData.getSubList());
	}

	protected void populateProduct(ProductCategorySearchPageData source, ProductSearchResponse productSearchResponse){
		List<ProductData> productDatas = source.getResults();
		List<com.bp.alps.facades.data.product.ProductData> productDataList = productDataConverter.convertAll(productDatas);
		productSearchResponse.setProductList(productDataList);
	}
}
