package com.bp.alps.facades.populators;

import com.bp.alps.core.model.type.CustomerStreamModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.CustomerFlowData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class CustomerStreamOutputPoplator implements Populator<CustomerStreamModel,CustomerFlowData>
{
	@Resource
	private DateFormatUtils dateFormatUtils;

	@Override
	public void populate(CustomerStreamModel customerStreamModel, CustomerFlowData customerFlowData)
			throws ConversionException
	{
		customerFlowData.setName(customerStreamModel.getName());
		customerFlowData.setGender(customerStreamModel.getGender()!=null?customerStreamModel.getGender().name():"");
		customerFlowData.setPhone(customerStreamModel.getMobile());
		customerFlowData.setId(customerStreamModel.getCode());
		customerFlowData.setInMan(customerStreamModel.getInman());
		customerFlowData.setInWoman(customerStreamModel.getInwoman());
		customerFlowData.setTypeCode(customerStreamModel.getType()!=null?customerStreamModel.getType().getCode():"");
		customerFlowData.setStatusCode(customerStreamModel.getStatus()!=null?customerStreamModel.getStatus().getCode():"");
		customerFlowData.setSalesConsultantId(customerStreamModel.getSalesconsultant().getCustomerID());
		customerFlowData.setCreationTime(dateFormatUtils.getDateString("datetime", customerStreamModel.getCreationtime()));
	}
}
