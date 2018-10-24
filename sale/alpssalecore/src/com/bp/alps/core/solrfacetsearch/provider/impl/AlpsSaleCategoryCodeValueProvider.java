package com.bp.alps.core.solrfacetsearch.provider.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CategoryCodeValueProvider;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


public class AlpsSaleCategoryCodeValueProvider extends CategoryCodeValueProvider
{
	protected static String separatorString = "/";

	protected Object getPropertyValue(final Object model)
	{
		if(model instanceof CategoryModel){
			CategoryModel categoryModel = (CategoryModel)model;
			return getCategoryHierarchyCode(categoryModel, "");
		}else{
			return super.getPropertyValue(model);
		}
	}

	protected String getCategoryHierarchyCode(final CategoryModel model,final String oldName){
		String name = model.getCode();
		if(StringUtils.isNotBlank(oldName)){
			name = name + separatorString + oldName;
		}
		if(CollectionUtils.isNotEmpty(model.getSupercategories())){
			return getCategoryHierarchyCode(model.getSupercategories().get(0),name);
		}
		return name;
	}
}
