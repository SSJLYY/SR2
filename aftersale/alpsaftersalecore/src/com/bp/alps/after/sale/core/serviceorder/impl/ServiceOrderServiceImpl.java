package com.bp.alps.after.sale.core.serviceorder.impl;

import com.bp.alps.after.sale.core.dao.ServiceOrderDao;
import com.bp.alps.after.sale.core.dao.ServiceOrderRelatedRolesDao;
import com.bp.alps.after.sale.core.enums.PickupInStoreErrandStatus;
import com.bp.alps.after.sale.core.enums.Service2RoleType;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import com.bp.alps.after.sale.core.service.PickupInStoreServices;
import com.bp.alps.after.sale.core.serviceorder.AlpsSplitOrderStrategy;
import com.bp.alps.after.sale.core.serviceorder.ServiceOrderService;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.SplitServiceOrderData;
import com.bp.alps.facades.data.order.SearchServiceOrderRequest;
import com.bp.alps.vehiclecommerceservices.order.AlpsCommerceHandleOrderStrategy;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.search.SearchResult;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ServiceOrderServiceImpl implements ServiceOrderService
{
	@Resource
	private AlpsCommerceHandleOrderStrategy alpsCommerceHandleOrderStrategy;

	@Resource
	private ServiceOrderDao serviceOrderDao;

	@Resource
	private ServiceOrderRelatedRolesDao serviceOrderRelatedRolesDao;

	@Resource
	private UserService userService;

	@Resource
	private ModelService modelService;

	@Resource
	private PickupInStoreServices pickupInStoreServices;

	@Resource
	private AlpsSplitOrderStrategy alpsSplitOrderStrategy;

	public void associatedPickupInStore(String code, ServiceOrderModel serviceOrderModel){
		PickupInStoreModel pickupInStore = pickupInStoreServices.getPickupInStoreByCode(code);
		if(pickupInStore!=null){
			pickupInStore.setWorkOrder(serviceOrderModel);
			pickupInStore.setStatus(PickupInStoreErrandStatus.BILLED);
			pickupInStoreServices.updatePickupInStore(pickupInStore);
		}
	}

	public ServiceOrderModel createServiceOrder(){
		ServiceOrderModel serviceOrderModel = (ServiceOrderModel)alpsCommerceHandleOrderStrategy.initializeOrderByType(ServiceOrderModel.class);
		serviceOrderModel.setStatus(OrderStatus.CREATED);
		if(userService.getCurrentUser() instanceof CustomerModel) serviceOrderModel.setConsultant((CustomerModel)userService.getCurrentUser());
		return serviceOrderModel;
	}

	public ServiceOrderModel cloneServiceOrderByCode(String code){
		ServiceOrderModel serviceOrderModel = getServiceOrderByCode(code);
		if(serviceOrderModel != null)
		{
			return alpsCommerceHandleOrderStrategy.cloneWithoutEntry(null, serviceOrderModel, ServiceOrderModel.class);
		}
		return null;
	}

	public ServiceOrderRelatedRolesModel createServiceOrder2User(){
		ServiceOrderRelatedRolesModel serviceOrderRelatedRoles = modelService.create(ServiceOrderRelatedRolesModel.class);
		return serviceOrderRelatedRoles;
	}

	public void saveOrder2User(ServiceOrderRelatedRolesModel serviceOrderRelatedRoles){
		modelService.save(serviceOrderRelatedRoles);
	}

	public AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter parameter){
		AlpsCommerceResult alpsCommerceResult = alpsCommerceHandleOrderStrategy.placeOrder(parameter);
		if(alpsCommerceResult.getSuccess() && parameter.getOrder().getUser() instanceof CustomerModel)
		{
			ServiceOrderRelatedRolesModel serviceOrderRelatedRoles = createServiceOrder2User();
			serviceOrderRelatedRoles.setUser((CustomerModel)parameter.getOrder().getUser());
			serviceOrderRelatedRoles.setType(Service2RoleType.PAYER);
			serviceOrderRelatedRoles.setServiceOrder((ServiceOrderModel)parameter.getOrder());
			saveOrder2User(serviceOrderRelatedRoles);
		}
		return alpsCommerceResult;
	}

	public AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter parameter){
		return alpsCommerceHandleOrderStrategy.updateOrder(parameter);
	}

	public List<ServiceOrderModel> getSubOrder(ServiceOrderModel serviceOrderModel){
		SearchResult<ServiceOrderModel> searchResult = serviceOrderDao.getSubOrder(serviceOrderModel);
		return searchResult.getResult();
	}

	public List<AlpsCommerceResult> splitOrder(List<SplitServiceOrderData> splitServiceOrderData, String defualtCode){
		ServiceOrderModel serviceOrderModel = getServiceOrderByCode(defualtCode);
		return alpsSplitOrderStrategy.splitOrder(splitServiceOrderData, serviceOrderModel);
	}

	public SearchPageData<ServiceOrderModel> getServiceOrderByCurrentServiceConsultant(SearchServiceOrderRequest searchServiceOrderRequest, PageableData pageableData){
		return serviceOrderDao.getServiceOrderByCurrentServiceConsultant(searchServiceOrderRequest, pageableData);
	}

	public ServiceOrderModel getServiceOrderByCode(String code){
		return serviceOrderDao.getServiceOrderByCode(code);
	}

	public List<ServiceOrderRelatedRolesModel> getRelatedRoleByOrder(ServiceOrderModel serviceOrderModel){
		return serviceOrderRelatedRolesDao.getRelatedRoleByOrder(serviceOrderModel);
	}

	public Boolean deleteOrder2UserByPks(List<String> pks){
		List<ServiceOrderRelatedRolesModel> serviceOrderRelatedRolesList = getModelByPks(pks , ServiceOrderRelatedRolesModel.class);
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
