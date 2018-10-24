package com.bp.after.sale.facades.populators.serviceorder.output;

import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import com.bp.after.sale.facades.data.RelatedRoleData;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;

import javax.annotation.Resource;


public class ServiceOrderRelatedRoleOutputPopulator implements Populator<ServiceOrderRelatedRolesModel, RelatedRoleData>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(ServiceOrderRelatedRolesModel source, RelatedRoleData target) throws ConversionException
	{
		target.setPk(source.getPk().toString());
		if(source.getUser()!=null)
		{
			target.setName(source.getUser().getName());
			target.setMobile(source.getUser().getMobileNumber());
			target.setUid(source.getUser().getUid());
		}
		if(source.getServiceOrder()!=null) target.setServiceOrderCode(source.getServiceOrder().getCode());
		target.setUserType(enumerationService.getEnumerationName(source.getType()));
	}
}
