package com.bp.after.sale.facades.incrementOrder.impl;

import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.after.sale.facades.incrementOrder.IncrementOrder2UserFacade;
import com.bp.alps.after.sale.core.enums.Service2RoleType;
import com.bp.alps.after.sale.core.increment.IncrementOrderService;
import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class IncrementOrder2UserFacadeImpl implements IncrementOrder2UserFacade
{
	@Resource
	private AlpsCustomerService alpsCustomerService;

	@Resource
	private IncrementOrderService incrementOrderService;

	@Resource
	private Converter<RelatedRoleData, OrderRelatedRolesModel> orderRelatedRoleConverter;

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

		OrderRelatedRolesModel serviceOrderRelatedRolesModel = incrementOrderService.createServiceOrder2User();
		orderRelatedRoleConverter.convert(relatedRoleData, serviceOrderRelatedRolesModel);
		incrementOrderService.saveOrder2User(serviceOrderRelatedRolesModel);
		return defaultResponse;
	}

	public DefaultResponse delete(List<String> pks){
		DefaultResponse defaultResponse = new DefaultResponse();
		defaultResponse.setSuccess(false);
		if(incrementOrderService.deleteOrder2UserByPks(pks)){
			defaultResponse.setSuccess(true);
			return defaultResponse;
		}
		defaultResponse.setMessageCode("invalid.delete");
		return defaultResponse;
	}
}
