package com.bp.alps.facades.voucher.impl;

import com.bp.alps.core.model.StoreVoucherDescriptionModel;
import com.bp.alps.core.service.StoreVoucherService;
import com.bp.alps.facades.data.voucher.StoreVoucherData;
import com.bp.alps.facades.data.voucher.StoreVoucherListDataResponse;
import com.bp.alps.facades.voucher.StoreVoucherFacade;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class StoreVoucherFacadeImpl implements StoreVoucherFacade
{
	@Resource
	private StoreVoucherService storeVoucherService;

	@Resource
	private Converter<StoreVoucherDescriptionModel,StoreVoucherData> storeVoucherConverter;

	@Override
	public StoreVoucherListDataResponse getStoreVoucherList()
	{
		StoreVoucherListDataResponse storeVoucherResponse = new StoreVoucherListDataResponse();
		List<StoreVoucherDescriptionModel> storeVoucherModelList = storeVoucherService.getStoreVoucherList();
		storeVoucherResponse.setSuccess(true);
		storeVoucherResponse.setVoucherList(storeVoucherConverter.convertAll(storeVoucherModelList));
		return storeVoucherResponse;
	}
}
