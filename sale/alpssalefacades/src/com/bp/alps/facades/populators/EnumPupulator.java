package com.bp.alps.facades.populators;

import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.enumeration.EnumerationValueModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.type.TypeService;

import javax.annotation.Resource;


public class EnumPupulator implements Populator<HybrisEnumValue,OptionData>
{
	@Resource
	private TypeService typeService;

	@Override
	public void populate(final HybrisEnumValue source, OptionData target) throws ConversionException
	{
		EnumerationValueModel enumerationValue = typeService.getEnumerationValue(source);
		target.setId(enumerationValue.getCode());
		target.setLabel(enumerationValue!=null?enumerationValue.getName():source.getCode());
	}
}
