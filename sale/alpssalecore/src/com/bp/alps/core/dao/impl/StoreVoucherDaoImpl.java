package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.StoreVoucherDao;
import com.bp.alps.core.model.StoreVoucherDescriptionModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;

import javax.annotation.Resource;
import java.util.*;


public class StoreVoucherDaoImpl extends DefaultGenericDao<StoreVoucherDescriptionModel> implements StoreVoucherDao
{
	@Resource(
			name = "userService"
	)
	private UserService userService;

	public StoreVoucherDaoImpl(String typeCode){
		super(typeCode);
	}

	@Override
	public List<StoreVoucherDescriptionModel> getVoucherList()
	{
		UserModel userModel = userService.getCurrentUser();
		BaseStoreModel baseStore;
		if(userModel instanceof CustomerModel){
			baseStore = ((CustomerModel) userModel).getStore();
			return find(Collections.singletonMap(StoreVoucherDescriptionModel.STORE,baseStore));
		}

		return new ArrayList<StoreVoucherDescriptionModel>();
	}

	public List<StoreVoucherDescriptionModel> getVoucherBycodes(List<String> code){
		Map<String,Object> mutiParameter = new HashMap<>();
		UserModel userModel = userService.getCurrentUser();
		BaseStoreModel baseStore;
		if(userModel instanceof CustomerModel)
		{
			baseStore = ((CustomerModel) userModel).getStore();
			mutiParameter.put(StoreVoucherDescriptionModel.STORE, baseStore);
			mutiParameter.put(StoreVoucherDescriptionModel.CODE, code);
			return find(mutiParameter);
		}

		return new ArrayList<StoreVoucherDescriptionModel>();
	}
}
