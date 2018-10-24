package com.bp.after.sale.facades.populators.increment.output;

import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;

import javax.annotation.Resource;


public class OrderRelatedRoleOutputPopulator implements Populator<OrderRelatedRolesModel, RelatedRoleData>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(OrderRelatedRolesModel source, RelatedRoleData target) throws ConversionException
	{
		target.setPk(source.getPk().toString());
		if(source.getUser()!=null)
		{
			target.setName(source.getUser().getName());
			target.setMobile(source.getUser().getMobileNumber());
			target.setUid(source.getUser().getUid());
		}
		if(source.getOrder()!=null) target.setServiceOrderCode(source.getOrder().getCode());
		target.setUserType(enumerationService.getEnumerationName(source.getType()));
	}
}
