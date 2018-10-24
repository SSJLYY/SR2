package com.bp.alps.after.sale.core.dao;

import com.bp.alps.after.sale.core.model.WorkHoursConfigurationModel;

import java.util.List;


public interface WorkHoursConfigurationDao
{
	List<WorkHoursConfigurationModel> getWorkHoursModelByProductCode(String productCode);
}
