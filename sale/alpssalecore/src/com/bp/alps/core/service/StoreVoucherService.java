package com.bp.alps.core.service;

import com.bp.alps.core.model.StoreVoucherDescriptionModel;
import de.hybris.platform.core.model.order.CartModel;

import java.util.List;


public interface StoreVoucherService
{
	List<StoreVoucherDescriptionModel> getStoreVoucherList();

	Boolean redeemVoucherByModelList(List<StoreVoucherDescriptionModel> storeVoucherList, CartModel cartModel);
}
