package com.bp.after.sale.facades.serviceorder.impl;

import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.after.sale.facades.serviceorder.ServiceOrder2UserFacade;
import com.bp.alps.after.sale.core.enums.Service2RoleType;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import com.bp.alps.after.sale.core.serviceorder.ServiceOrderService;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class ServiceOrder2UserFacadeImpl implements ServiceOrder2UserFacade
{
	@Resource
	private AlpsCustomerService alpsCustomerService;

	@Resource
	private ServiceOrderService serviceOrderService;

	@Resource
	private Converter<RelatedRoleData, ServiceOrderRelatedRolesModel> serviceOrderRelatedRoleConverter;

	@Resource
	private EnumerationService enumerationService;

	@Resource(name="enumConverter")
	private Converter<HybrisEnumValue, OptionData> enumerationValueModelConverter;

	public List<OptionData> getService2RoleType(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(Service2RoleType.class));
	}

	public DefaultResponse create(RelatedRoleData relatedRoleData){
		CustomerModel customerModel = alpsCustomerService.getCustomerForUid(relatedRoleData.getUid());
		DefaultResponse defaultResponse = new DefaultResponse();
		if(customerModel==null){
			defaultResponse.setSuccess(false);
			defaultResponse.setMessageCode("invalid.user");
			return defaultResponse;
		}

		ServiceOrderRelatedRolesModel serviceOrderRelatedRolesModel = serviceOrderService.createServiceOrder2User();
		serviceOrderRelatedRoleConverter.convert(relatedRoleData, serviceOrderRelatedRolesModel);
		serviceOrderService.saveOrder2User(serviceOrderRelatedRolesModel);
		return defaultResponse;
	}

	public DefaultResponse delete(List<String> pks){
		DefaultResponse defaultResponse = new DefaultResponse();
		defaultResponse.setSuccess(false);
		if(serviceOrderService.deleteOrder2UserByPks(pks)){
			defaultResponse.setSuccess(true);
			return defaultResponse;
		}
		defaultResponse.setMessageCode("invalid.delete");
		return defaultResponse;
	}
}
