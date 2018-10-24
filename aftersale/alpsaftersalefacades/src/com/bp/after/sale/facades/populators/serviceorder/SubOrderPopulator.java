package com.bp.after.sale.facades.populators.serviceorder;

import com.bp.after.sale.facades.data.ServiceOrderEntryData;
import com.bp.alps.after.sale.core.model.ServiceOrderEntryModel;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.core.facades.order.SubServiceOrderData;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


public class SubOrderPopulator implements Populator<ServiceOrderModel, SubServiceOrderData>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private Converter<ServiceOrderEntryModel, ServiceOrderEntryData> serviceOrderEntryOutputConverter;

	public void populate(ServiceOrderModel source, SubServiceOrderData target) throws ConversionException
	{
		target.setCode(source.getCode());
		if(source.getUser()!=null)
		{
			target.setBuyer(source.getUser().getUid());
			target.setBuyerName(source.getUser().getName());
		}
		if(source.getServiceSubType()!=null)
		{
			target.setServiceSubType(enumerationService.getEnumerationName(source.getServiceSubType()));
			target.setServiceSubTypeCode(source.getServiceSubType().getCode());
		}
		if(CollectionUtils.isNotEmpty(source.getEntries())){
			List<ServiceOrderEntryModel> orderEntryModelList = source.getEntries().stream().filter(abstractOrderEntryModel -> abstractOrderEntryModel instanceof ServiceOrderEntryModel).map(abstractOrderEntryModel -> (ServiceOrderEntryModel)abstractOrderEntryModel).collect(Collectors.toList());
			target.setEntries(serviceOrderEntryOutputConverter.convertAll(orderEntryModelList));
		}
	}
}
