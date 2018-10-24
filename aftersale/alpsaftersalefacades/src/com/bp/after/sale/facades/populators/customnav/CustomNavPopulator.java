package com.bp.after.sale.facades.populators.customnav;

import com.bp.after.sale.facades.data.CustomNavData;
import com.bp.alps.after.sale.core.model.CustomNavModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class CustomNavPopulator implements Populator<CustomNavModel, CustomNavData>
{
	@Override
	public void populate(CustomNavModel customNavModel, CustomNavData customNavData) throws ConversionException
	{
		customNavData.setDisplay(customNavModel.getDisplay());
		customNavData.setCode(customNavModel.getCode());
		if(customNavModel.getLink()!=null)
		{
			customNavData.setLinkName(customNavModel.getLink().getLinkName());
			customNavData.setLinkUrl(customNavModel.getLink().getUrl());
		}
		if(customNavModel.getImage()!=null)
		{
			customNavData.setImage(customNavModel.getImage().getURL());
		}
	}
}
