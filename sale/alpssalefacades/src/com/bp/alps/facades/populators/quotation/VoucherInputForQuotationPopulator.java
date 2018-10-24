package com.bp.alps.facades.populators.quotation;

import com.bp.alps.core.model.StoreVoucherDescriptionModel;
import com.bp.alps.facades.data.voucher.StoreVoucherData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class VoucherInputForQuotationPopulator implements Populator<StoreVoucherData, StoreVoucherDescriptionModel>
{
	@Override
	public void populate(StoreVoucherData storeVoucherData, StoreVoucherDescriptionModel storeVoucherDescriptionModel)
			throws ConversionException
	{
		storeVoucherDescriptionModel.setCode(storeVoucherData.getCode());
		storeVoucherDescriptionModel.setPrice(storeVoucherData.getActualPrice());
		storeVoucherDescriptionModel.setName(storeVoucherData.getName());
	}
}
