package com.bp.alps.after.sale.core.service.impl;

import com.bp.alps.after.sale.core.dao.WorkHoursConfigurationDao;
import com.bp.alps.after.sale.core.model.WorkHoursConfigurationModel;
import com.bp.alps.after.sale.core.service.WorkHoursConfigurationService;

import javax.annotation.Resource;
import java.util.List;


public class WorkHoursConfigurationServiceImpl implements WorkHoursConfigurationService
{
	@Resource
	private WorkHoursConfigurationDao workHoursConfigurationDao;

	public List<WorkHoursConfigurationModel> getWorkHoursModelByProductCode(String productCode){
		return workHoursConfigurationDao.getWorkHoursModelByProductCode(productCode);
	}
}
