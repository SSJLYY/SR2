package com.bp.alps.core.service.impl;

import com.bp.alps.core.dao.StoreVoucherDao;
import com.bp.alps.core.model.StoreVoucherDescriptionModel;
import com.bp.alps.core.service.StoreVoucherService;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.order.Cart;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.voucher.jalo.StoreCoupon;
import de.hybris.platform.voucher.model.StoreCouponModel;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class StoreVoucherServiceImpl implements StoreVoucherService
{
	@Resource
	public StoreVoucherDao storeVoucherDao;

	@Resource
	private ModelService modelService;

	@Resource
	private CommonI18NService commonI18NService;

	@Override
	public List<StoreVoucherDescriptionModel> getStoreVoucherList()
	{
		return storeVoucherDao.getVoucherList();
	}

	public Boolean redeemVoucherByModelList(List<StoreVoucherDescriptionModel> storeVoucherList, CartModel cartModel){
		List<String> codes = storeVoucherList.stream().map(storeVoucher -> storeVoucher.getCode()).collect(Collectors.toList());
		List<StoreVoucherDescriptionModel> models = storeVoucherDao.getVoucherBycodes(codes);
		if(models.size() == storeVoucherList.size()){
			storeVoucherList.stream().forEach(storeVoucher->{
				StoreCouponModel storeCoupon = modelService.create(StoreCouponModel.class);
				storeCoupon.setVoucherCode(UUID.randomUUID().toString());
				storeCoupon.setCode(UUID.randomUUID().toString());
				storeCoupon.setValue(storeVoucher.getPrice());
				storeCoupon.setDescription(storeVoucher.getName());
				CurrencyModel currencyModel = commonI18NService.getCurrentCurrency();
				storeCoupon.setCurrency(currencyModel);
				modelService.save(storeCoupon);
				StoreCoupon storeCouponJalo = modelService.getSource(storeCoupon);
				Cart cartJalo = modelService.getSource(cartModel);
				cartJalo.addToDiscounts(storeCouponJalo);
			});
			return true;
		}
		return false;
	}
}
