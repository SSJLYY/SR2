package com.bp.alps.facades.populators;

import com.bp.alps.core.model.type.FollowContentModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.opp.FollowContentData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class FollowContentOutputPoplator implements Populator<FollowContentModel,FollowContentData>
{
	@Override
	public void populate(final FollowContentModel source, FollowContentData target) throws ConversionException
	{
		target.setContent(source.getContent());
		target.setCreationtime(DateFormatUtils.getDateString("datetime", source.getCreationtime()));

	}
}
