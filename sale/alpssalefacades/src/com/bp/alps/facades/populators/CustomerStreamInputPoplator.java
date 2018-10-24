package com.bp.alps.facades.populators;

import com.bp.alps.core.enums.CustomerStreamStatus;
import com.bp.alps.core.enums.CustomerStreamType;
import com.bp.alps.core.model.type.CustomerStreamModel;
import com.bp.alps.facades.data.CustomerFlowData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.Gender;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;


public class CustomerStreamInputPoplator implements Populator<CustomerFlowData,CustomerStreamModel>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private UserService userService;

	@Override
	public void populate(CustomerFlowData source, CustomerStreamModel target)
			throws ConversionException
	{
		target.setCode(source.getId());
		target.setName(source.getName());
		target.setGender(source.getGender()!=null?enumerationService.getEnumerationValue(Gender.class, source.getGender()):null);
		target.setMobile(source.getPhone());
		target.setInman(source.getInMan());
		target.setInwoman(source.getInWoman());
		target.setType(source.getTypeCode()!=null?enumerationService.getEnumerationValue(CustomerStreamType.class, source.getTypeCode()):null);
		target.setStatus(source.getStatusCode()!=null?enumerationService.getEnumerationValue(CustomerStreamStatus.class, source.getStatusCode()):null);
		if(source.getSalesConsultantId()!=null)
		{
			UserModel userModel = userService.getUserForUID(source.getSalesConsultantId());
			if (userModel instanceof CustomerModel)
			{
				target.setSalesconsultant((CustomerModel) userModel);
			}
		}
	}
}
