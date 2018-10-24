package com.bp.alps.facades.populators;

import com.bp.alps.facades.data.SalesStaffData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class SalesStaffPopulator implements Populator<CustomerModel,SalesStaffData>
{
	@Override
	public void populate(final CustomerModel source, final SalesStaffData target) throws ConversionException
	{
		target.setCustomerId(source.getUid());
		target.setCustomerName(source.getName());
	}
}
