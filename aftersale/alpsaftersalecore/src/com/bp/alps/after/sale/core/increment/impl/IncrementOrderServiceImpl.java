package com.bp.alps.after.sale.core.increment.impl;

import com.bp.alps.after.sale.core.dao.IncrementOrderDao;
import com.bp.alps.after.sale.core.dao.OrderRelatedRolesDao;
import com.bp.alps.after.sale.core.enums.Service2RoleType;
import com.bp.alps.after.sale.core.increment.IncrementOrderService;
import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.vehiclecommerceservices.order.AlpsCommerceHandleOrderStrategy;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class IncrementOrderServiceImpl implements IncrementOrderService
{
	@Resource
	private UserService userService;

	@Resource
	private IncrementOrderDao incrementOrderDao;

	@Resource
	private OrderRelatedRolesDao orderRelatedRolesDao;

	@Resource
	private AlpsCommerceHandleOrderStrategy alpsCommerceHandleOrderStrategy;

	@Resource
	private ModelService modelService;

	public IncrementOrderModel createServiceOrder(){
		IncrementOrderModel incrementOrderModel = (IncrementOrderModel)alpsCommerceHandleOrderStrategy.initializeOrderByType(IncrementOrderModel.class);
		incrementOrderModel.setStatus(OrderStatus.CREATED);
		if(userService.getCurrentUser() instanceof CustomerModel) incrementOrderModel.setConsultant((CustomerModel)userService.getCurrentUser());
		return incrementOrderModel;
	}

	public AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter){
		AlpsCommerceResult alpsCommerceResult = alpsCommerceHandleOrderStrategy.placeOrder(alpsCommercePlaceOrderParameter);
		if(alpsCommerceResult.getSuccess() && alpsCommerceResult.getOrder()!=null){
			OrderRelatedRolesModel orderRelatedRolesModel = createServiceOrder2User();
			orderRelatedRolesModel.setOrder((IncrementOrderModel)alpsCommerceResult.getOrder());
			orderRelatedRolesModel.setUser((CustomerModel)alpsCommerceResult.getOrder().getUser());
			orderRelatedRolesModel.setType(Service2RoleType.CUSTOMER);
			saveOrder2User(orderRelatedRolesModel);
		}
		return alpsCommerceResult;
	}

	public AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter){
		AlpsCommerceResult alpsCommerceResult = alpsCommerceHandleOrderStrategy.updateOrder(alpsCommercePlaceOrderParameter);
		return alpsCommerceResult;
	}

	public SearchPageData<IncrementOrderModel> getIncrementOrderByCurrentConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData){
		return incrementOrderDao.getIncrementOrderByCurrentConsultant(startTime, endTime, orderCode, status, username, mobile ,pageableData);
	}

	public IncrementOrderModel getIncrementOrderByCode(String code){
		return incrementOrderDao.getIncrementOrderByCode(code);
	}

	public OrderRelatedRolesModel createServiceOrder2User(){
		OrderRelatedRolesModel serviceOrderRelatedRoles = modelService.create(OrderRelatedRolesModel.class);
		return serviceOrderRelatedRoles;
	}

	public List<OrderRelatedRolesModel> getRelatedRoleByOrder(IncrementOrderModel incrementOrderModel){
		return orderRelatedRolesDao.getRelatedRoleByOrder(incrementOrderModel);
	}

	public void saveOrder2User(OrderRelatedRolesModel serviceOrderRelatedRoles){
		modelService.save(serviceOrderRelatedRoles);
	}

	public Boolean deleteOrder2UserByPks(List<String> pks){
		List<OrderRelatedRolesModel> serviceOrderRelatedRolesList = getModelByPks(pks , OrderRelatedRolesModel.class);
		modelService.removeAll(serviceOrderRelatedRolesList);
		return true;
	}

	public <T> List<T> getModelByPks(List<String> pks, Class className){
		Collection<PK> pkList = PK.parse(pks);
		List<Object> objectList = pkList.stream().map(pk -> modelService.get(pk)).collect(Collectors.toList());
		List instanceList = objectList.stream().filter(object-> className.isInstance(object)).map(o -> className.cast(o)).collect(Collectors.toList());
		return instanceList;
	}
}
