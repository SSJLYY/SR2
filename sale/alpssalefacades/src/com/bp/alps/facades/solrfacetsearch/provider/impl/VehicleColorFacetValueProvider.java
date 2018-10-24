package com.bp.alps.facades.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.variants.model.VehicleVariantProductModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.*;


public class VehicleColorFacetValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider
{
	@Resource(name="solrFieldNameProvider")
	private FieldNameProvider fieldNameProvider;

	@Resource
	private CommonI18NService commonI18NService;

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (!(model instanceof VehicleVariantProductModel))
		{
			return Collections.emptyList();
		}
		VehicleVariantProductModel vehicleVariantProductModel = (VehicleVariantProductModel)model;

		final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
		Collection<LanguageModel> languageModels = indexConfig.getLanguages();

		for(LanguageModel languageModel : languageModels){
			Locale locale =  commonI18NService.getLocaleForLanguage(languageModel);
			String colors = vehicleVariantProductModel.getColors(locale);
			fieldValues.addAll(createFieldValue(colors, indexedProperty, languageModel));
			if(locale.equals(Locale.CHINESE)){
				fieldValues.addAll(createFieldValue(colors, indexedProperty, null));
			}
		}

		if (CollectionUtils.isNotEmpty(fieldValues))
		{
			return fieldValues;
		}

		return Collections.emptyList();
	}

	protected List<FieldValue> createFieldValue(final String color, final IndexedProperty indexedProperty, final LanguageModel languageModel)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		final Object value = color;
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, languageModel!=null?languageModel.getIsocode():null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
		return fieldValues;
	}

	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

}
