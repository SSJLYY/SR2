package com.bp.after.sale.facades.facade.impl;

import com.bp.after.sale.facades.data.WorkhoursData;
import com.bp.after.sale.facades.facade.WorkHoursConfigurationFacade;
import com.bp.alps.after.sale.core.model.WorkHoursConfigurationModel;
import com.bp.alps.after.sale.core.service.WorkHoursConfigurationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class WorkHoursConfigurationFacadeImpl implements WorkHoursConfigurationFacade
{
	@Resource
	private WorkHoursConfigurationService workHoursConfigurationService;

	@Resource
	private Converter<WorkHoursConfigurationModel, WorkhoursData> workHoursOutputConverter;

	public List<WorkhoursData> getWorkHoursDataByProductCode(String productCode){

		List<WorkHoursConfigurationModel> workHoursConfigurationList = workHoursConfigurationService.getWorkHoursModelByProductCode(productCode);
		return workHoursOutputConverter.convertAll(workHoursConfigurationList);
	}
}
