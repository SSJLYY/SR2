package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.OppoStatusDao;
import com.bp.alps.core.enums.FollowType;
import com.bp.alps.core.model.oppo.type.OppoStatusModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;


public class OppoStatusDaoImpl extends DefaultGenericDao<OppoStatusModel> implements OppoStatusDao
{
	public OppoStatusDaoImpl(String typeCode){
		super(typeCode);
	}

	@Override public OppoStatusModel getOppoStatusByCode(String code)
	{
		final List<OppoStatusModel> oppoStatusList = find(Collections.singletonMap(OppoStatusModel.CODE, code));
		return CollectionUtils.isNotEmpty(oppoStatusList) ? oppoStatusList.get(0) : null;
	}

	public OppoStatusModel getOppoStatusByFollowType(final FollowType followType){
		final List<OppoStatusModel> oppoStatusList = find(Collections.singletonMap(OppoStatusModel.FOLLOWTYPE, followType));
		return CollectionUtils.isNotEmpty(oppoStatusList) ? oppoStatusList.get(0) : null;
	}

	public OppoStatusModel getDefaultOppoStatus(){
		final List<OppoStatusModel> oppoStatusList = find(Collections.singletonMap(OppoStatusModel.DEFAULT, true));
		return CollectionUtils.isNotEmpty(oppoStatusList) ? oppoStatusList.get(0) : null;
	}
}
