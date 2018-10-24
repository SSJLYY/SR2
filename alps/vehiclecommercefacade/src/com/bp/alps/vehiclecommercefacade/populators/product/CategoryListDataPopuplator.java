package com.bp.alps.vehiclecommercefacade.populators.product;

import com.bp.alps.facades.data.category.CategoryListData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoryListDataPopuplator implements Populator<FacetValueData, CategoryListData>
{
	protected static String separatorString = "/";

	@Override
	public void populate(FacetValueData facetData, CategoryListData categoryListData) throws ConversionException
	{
		if(StringUtils.isNotBlank(facetData.getCode())&&StringUtils.isNotBlank(facetData.getName()))
		{
			String facetCode = facetData.getCode();
			String[] code = facetCode.split(separatorString);
			String name = facetData.getName();
			String[] nameCode = name.split(separatorString);
			if (code.length > 1)
			{
				List<CategoryListData> categoryListDataList = new ArrayList<>();
				facetData.setCode(facetCode.replace(code[0] + separatorString, ""));
				facetData.setName(facetCode.replace(nameCode[0] + separatorString, ""));

				if (CollectionUtils.isNotEmpty(categoryListData.getSubList()))
				{
					categoryListDataList = categoryListData.getSubList();
					//categoryListData1 -> {categoryListData1.getCode().equals(code[1]);}
					Optional<CategoryListData> optional = categoryListDataList.stream()
							.filter(categoryListData1 -> categoryListData1.getCode().equals(code[1])).findAny();
					if (optional.isPresent())
					{
						populate(facetData, optional.get());
						categoryListData.setSubList(categoryListDataList);
						return;
					}
				}

				CategoryListData categoryListData1 = new CategoryListData();
				populate(facetData, categoryListData1);
				categoryListDataList.add(categoryListData1);
				categoryListData.setSubList(categoryListDataList);
			}
			else
			{
				categoryListData.setCode(code[0]);
				categoryListData.setName(nameCode[0]);
			}
		}
	}
}
