package com.bp.alps.facades.populators;

import com.bp.alps.core.model.type.FollowContentModel;
import com.bp.alps.facades.data.opp.FollowContentData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class FollowContentInputPoplator implements Populator<FollowContentData,FollowContentModel>
{
	@Override
	public void populate(final FollowContentData source, FollowContentModel target) throws ConversionException
	{
		target.setContent(source.getContent());
	}
}
