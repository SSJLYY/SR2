package com.bp.after.sale.facades.populators.serviceorder;

import com.bp.after.sale.facades.data.ServiceOrderEntryData;
import com.bp.alps.after.sale.core.enums.ServiceOrderEntryType;
import com.bp.alps.after.sale.core.model.ServiceOrderEntryModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.lang3.StringUtils;


public class ServiceOrderEntryAddinfoPopulator implements Populator<ServiceOrderEntryData, ServiceOrderEntryModel>
{
	@Override
	public void populate(ServiceOrderEntryData serviceOrderEntryData, ServiceOrderEntryModel serviceOrderEntryModel)
			throws ConversionException
	{
		if(serviceOrderEntryData.getRate()!=null) serviceOrderEntryModel.setDiscountRate(serviceOrderEntryData.getRate());
		if(StringUtils.isNoneBlank(serviceOrderEntryData.getServiceTypeCode())) serviceOrderEntryModel.setEntryType(ServiceOrderEntryType.valueOf(serviceOrderEntryData.getServiceTypeCode()));
		if(StringUtils.isNoneBlank(serviceOrderEntryData.getCategoryCode())) serviceOrderEntryModel.setProductType(serviceOrderEntryData.getCategoryCode());
	}
}
