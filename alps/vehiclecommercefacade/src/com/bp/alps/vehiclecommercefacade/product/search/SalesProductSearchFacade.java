package com.bp.alps.vehiclecommercefacade.product.search;

import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.product.ProductSearchResponse;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;

import java.util.List;


public interface SalesProductSearchFacade extends ProductSearchFacade
{
	ProductSearchResponse getProductSearchResult(ProductSearchRequest productSearchRequest);

	List<CategoryData> getSubCategoriesForCode(final String code);
}
