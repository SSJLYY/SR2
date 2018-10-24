package com.bp.alps.facades.populators;

import com.bp.alps.core.model.StoreVoucherDescriptionModel;
import com.bp.alps.facades.data.voucher.StoreVoucherData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class StoreVoucherPoplator implements Populator<StoreVoucherDescriptionModel,StoreVoucherData>
{
	@Override
	public void populate(StoreVoucherDescriptionModel source, StoreVoucherData target)
			throws ConversionException
	{
		target.setCode(source.getCode());
		target.setName(source.getName());
		target.setPrice(source.getPrice());
		target.setActualPrice(source.getPrice());
	}
}
