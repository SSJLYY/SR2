package com.bp.alps.core.service.impl;

import com.bp.alps.core.dao.PlatFormDao;
import com.bp.alps.core.model.type.PlatformModel;
import com.bp.alps.core.service.PlatFormService;

import javax.annotation.Resource;


public class PlatFormServiceImpl implements PlatFormService
{
	@Resource
	public PlatFormDao platFormDao;

	public PlatformModel getInStoreModel(){
		return platFormDao.getInStoreModel();
	}
}
