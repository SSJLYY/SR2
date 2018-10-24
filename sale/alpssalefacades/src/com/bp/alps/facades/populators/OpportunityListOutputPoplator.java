package com.bp.alps.facades.populators;

import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.opp.OpportunityListData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


public class OpportunityListOutputPoplator implements Populator<OpportunityModel,OpportunityListData>
{


	@Override
	public void populate(OpportunityModel source, OpportunityListData target) throws ConversionException
	{
		target.setCode(source.getCode());
		target.setName(source.getName());
		target.setGender(source.getGender());
		target.setMobile(source.getMobile());
		target.setGender(source.getGender());
		target.setVehicleCategoryCode(source.getVehicleCategory()!=null?source.getVehicleCategory().getCode():"");
		target.setVehicleCategoryName(source.getVehicleCategory()!=null?source.getVehicleCategory().getName():"");
		target.setVehicleCode(source.getVehicle()!=null?source.getVehicle().getCode():null);
		target.setVehicleName(source.getVehicle()!=null?source.getVehicle().getName():"");
		target.setLevelCode(source.getLevel()!=null?source.getLevel().getCode():null);
		target.setLevelName(source.getLevel()!=null?source.getLevel().getName():null);
		target.setLastFollowTime(DateFormatUtils.getDateString("datetime",source.getLastFollowTime()));
		target.setCreationtime(DateFormatUtils.getDateString("datetime",source.getCreationtime()));
		if(source.getLastFollowTime()!=null && source.getLevel()!=null)
		{
			target.setLeftFollowTime(DateFormatUtils.getLeftTime(source.getLastFollowTime(), source.getLevel().getMaxFollowTime()));
		}else{
			target.setLeftFollowTime(0);
		}
		target.setStatusCode(source.getStatus()!=null?source.getStatus().getCode():null);
		target.setStatusName(source.getStatus()!=null?source.getStatus().getName():null);
		target.setPlatform(source.getPlatform()!=null?source.getPlatform().getName():null);
	}
}
