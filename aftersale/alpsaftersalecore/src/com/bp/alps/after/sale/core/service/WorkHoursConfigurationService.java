package com.bp.alps.after.sale.core.service;

import com.bp.alps.after.sale.core.model.WorkHoursConfigurationModel;

import java.util.List;


public interface WorkHoursConfigurationService
{
	List<WorkHoursConfigurationModel> getWorkHoursModelByProductCode(String productCode);
}
