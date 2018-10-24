package com.bp.after.sale.facades.populators.serviceorder;

import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.alps.after.sale.core.enums.Service2RoleType;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import com.bp.alps.after.sale.core.serviceorder.ServiceOrderService;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;


public class ServiceOrderRelatedRolePopulator implements Populator<RelatedRoleData, ServiceOrderRelatedRolesModel>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private UserService userService;

	@Resource
	private ServiceOrderService serviceOrderService;

	public void populate(RelatedRoleData source, ServiceOrderRelatedRolesModel target) throws ConversionException
	{
		if(source.getUid()!=null)
		{
			UserModel userModel = userService.getUserForUID(source.getUid());
			if(userModel instanceof CustomerModel){
				target.setUser((CustomerModel)userModel);
			}
		}
		if(source.getServiceOrderCode()!=null) target.setServiceOrder(serviceOrderService.getServiceOrderByCode(source.getServiceOrderCode()));
		if(source.getUserType()!=null) target.setType(Service2RoleType.valueOf(source.getUserType()));
	}
}
