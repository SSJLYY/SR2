package com.bp.after.sale.facades.populators.increment;

import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.alps.after.sale.core.enums.Service2RoleType;
import com.bp.alps.after.sale.core.increment.IncrementOrderService;
import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;


public class OrderRelatedRolePopulator implements Populator<RelatedRoleData, OrderRelatedRolesModel>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private UserService userService;

	@Resource
	private IncrementOrderService incrementOrderService;

	public void populate(RelatedRoleData source, OrderRelatedRolesModel target) throws ConversionException
	{
		if(source.getUid()!=null)
		{
			UserModel userModel = userService.getUserForUID(source.getUid());
			if(userModel instanceof CustomerModel){
				target.setUser((CustomerModel)userModel);
			}
		}
		if(source.getServiceOrderCode()!=null) target.setOrder(incrementOrderService.getIncrementOrderByCode(source.getServiceOrderCode()));
		if(source.getUserType()!=null) target.setType(Service2RoleType.valueOf(source.getUserType()));
	}
}
