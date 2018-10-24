package com.bp.alps.facades.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VariantTypeModel;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class ProductVariantItemTypeValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider
{
	@Resource(name="solrFieldNameProvider")
	private FieldNameProvider fieldNameProvider;

	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();

		if (model instanceof ProductModel)
		{
			String value;
			ProductModel productModel = (ProductModel)model;
			VariantTypeModel variantTypeModel = productModel.getVariantType();
			if(variantTypeModel!=null){
				value = variantTypeModel.getCode();
			}else{
				value = productModel.getItemtype();
			}

			final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
			//fieldValues.add(new FieldValue(indexedProperty.getName(), value));
			for (final String fieldName : fieldNames)
			{
				fieldValues.add(new FieldValue(fieldName, value));
			}
			return fieldValues;
		}
		return Collections.emptyList();
	}

	protected FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}
}
