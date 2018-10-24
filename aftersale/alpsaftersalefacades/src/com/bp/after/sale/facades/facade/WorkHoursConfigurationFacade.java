package com.bp.after.sale.facades.facade;

import com.bp.after.sale.facades.data.WorkhoursData;

import java.util.List;


public interface WorkHoursConfigurationFacade
{
	List<WorkhoursData> getWorkHoursDataByProductCode(String productCode);
}
