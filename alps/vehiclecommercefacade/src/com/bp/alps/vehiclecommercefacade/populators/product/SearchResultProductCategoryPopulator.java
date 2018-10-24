package com.bp.alps.vehiclecommercefacade.populators.product;

import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class SearchResultProductCategoryPopulator implements Populator<SearchResultValueData, ProductData>
{
	protected static String separatorString = "/";

	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		final String finalCategory = findFinalCategory(source);
		if(finalCategory!=null){
			List<CategoryData> categoryDataList = new ArrayList<>();
			CategoryData categoryData = new CategoryData();
			categoryData.setCode(finalCategory);
			categoryDataList.add(categoryData);
			target.setCategories(categoryDataList);
		}
	}

	protected String findFinalCategory(final SearchResultValueData source){
		List<String> categoryes = this.<List> getValue(source, "category");
		if(CollectionUtils.isNotEmpty(categoryes))
		{
			String finalCategoryLink = "";
			for (String category : categoryes)
			{
				if (StringUtils.isBlank(finalCategoryLink) || category.startsWith(finalCategoryLink))
				{
					finalCategoryLink = category;
				}
			}

			final String[] finalCategoryes = finalCategoryLink.split(separatorString);
			return finalCategoryes[finalCategoryes.length - 1];
		}
		return null;
	}

	protected <T> T getValue(final SearchResultValueData source, final String propertyName)
	{
		if (source.getValues() == null)
		{
			return null;
		}

		// DO NOT REMOVE the cast (T) below, while it should be unnecessary it is required by the javac compiler
		return (T) source.getValues().get(propertyName);
	}
}
