package com.bp.alps.core.dao.impl;

import com.bp.alps.core.constants.AlpssalecoreConstants;
import com.bp.alps.core.dao.PlatFormDao;
import com.bp.alps.core.model.type.PlatformModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.util.Config;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;


public class PlatFormDaoImpl extends DefaultGenericDao<PlatformModel> implements PlatFormDao
{
	public PlatFormDaoImpl(String typecode){
		super(typecode);
	}

	public PlatformModel getInStoreModel(){
		final String code = Config.getString(AlpssalecoreConstants.PlatForm.INSTORE_CODE_KEY,"instore");
		List<PlatformModel> platformModelList = find(Collections.singletonMap(PlatformModel.CODE, code));
		return CollectionUtils.isNotEmpty(platformModelList) ? platformModelList.get(0) : null;
	}
}
