package com.bp.alps.facades.populators;

import com.bp.alps.core.model.MarketActivityModel;
import com.bp.alps.facades.data.MarketActivityData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class MarketActivityOutputPoplator implements Populator<MarketActivityModel,MarketActivityData>
{
	@Override
	public void populate(final MarketActivityModel source, MarketActivityData target) throws ConversionException
	{
		target.setName(source.getName());
		target.setCode(source.getCode());
	}
}
