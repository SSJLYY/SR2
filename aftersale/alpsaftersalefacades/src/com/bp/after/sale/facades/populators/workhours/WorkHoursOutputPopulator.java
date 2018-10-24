package com.bp.after.sale.facades.populators.workhours;

import com.bp.after.sale.facades.data.WorkhoursData;
import com.bp.alps.after.sale.core.model.WorkHoursConfigurationModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class WorkHoursOutputPopulator implements Populator<WorkHoursConfigurationModel, WorkhoursData>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(WorkHoursConfigurationModel source, WorkhoursData workhoursData)
			throws ConversionException
	{
		workhoursData.setHours(source.getHours());
		workhoursData.setProductCode(source.getProduct().getCode());
		workhoursData.setTypesOfWork(source.getTypesOfWork().getCode());
		workhoursData.setTypesOfWorkName(enumerationService.getEnumerationName(source.getTypesOfWork()));
	}
}
