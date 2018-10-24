package com.bp.after.sale.facades.serviceorder.impl;

import com.bp.after.sale.facades.data.ServiceOrderBaseData;
import com.bp.after.sale.facades.data.ServiceOrderData;
import com.bp.after.sale.facades.data.ServiceOrderEntryData;
import com.bp.after.sale.facades.data.SplitServiceOrderRequest;
import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.after.sale.facades.serviceorder.ServiceOrderFacade;
import com.bp.alps.after.sale.core.enums.ServiceOrderEntryType;
import com.bp.alps.after.sale.core.enums.ServiceSubType;
import com.bp.alps.after.sale.core.enums.ServiceOrderType;
import com.bp.alps.after.sale.core.model.ServiceOrderEntryModel;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import com.bp.alps.after.sale.core.serviceorder.ServiceOrderService;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.facades.order.SubServiceOrderData;
import com.bp.alps.facades.data.order.CreateServiceOrderResponse;
import com.bp.alps.facades.data.order.SearchServiceOrderRequest;
import com.bp.alps.facades.data.order.ServiceOrderListResponse;
import com.bp.alps.vehiclecommercefacade.facade.DefaultAlpsCommerceFacade;
import com.bp.alps.vehiclecommerceservices.order.SaveEntryCallBackWithoutResult;
import com.bp.alps.vehiclecommerceservices.service.OrderStatusService;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


public class ServiceOrderFacadeImpl extends DefaultAlpsCommerceFacade implements ServiceOrderFacade
{
	@Resource
	private ServiceOrderService serviceOrderService;

	@Resource
	private ServiceOrderEntryParameterConverterImpl serviceOrderEntryParameterConverter;

	@Resource
	private Converter<ServiceOrderData, ServiceOrderModel> serviceOrderBaseConverter;

	@Resource(name="enumConverter")
	private Converter<HybrisEnumValue, OptionData> enumerationValueModelConverter;

	@Resource
	private Converter<ServiceOrderModel, ServiceOrderBaseData> serviceOrderBaseOutputConverter;

	@Resource
	private Converter<ServiceOrderEntryData, ServiceOrderEntryModel> serviceOrderEntryAddinfoConverter;
	@Resource
	private Converter<ServiceOrderRelatedRolesModel, RelatedRoleData> serviceOrderRelatedRoleOutputConverter;

	@Resource
	private Converter<ServiceOrderModel, ServiceOrderData> serviceOrderOutputConverter;

	@Resource
	private Converter<ServiceOrderModel, SubServiceOrderData> subOrderConverter;

	@Resource
	private OrderStatusService orderStatusoStatusService;

	@Resource
	private EnumerationService enumerationService;

	public List<OptionData> getServiceOrderStatus(){
		return enumerationValueModelConverter.convertAll(orderStatusoStatusService.getOrderStatusByType(ServiceOrderModel._TYPECODE));
	}

	public List<OptionData> getServiceSubType(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(ServiceSubType.class));
	}

	public List<OptionData> getServiceOrderType(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(ServiceOrderType.class));
	}

	public List<OptionData> getServiceOrderEntryType(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(ServiceOrderEntryType.class));
	}

	public CreateServiceOrderResponse createServiceOrder(ServiceOrderData serviceOrderData){
		ServiceOrderModel serviceOrderModel = serviceOrderService.createServiceOrder();
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = getHandleServiceOrderParameter(serviceOrderModel, serviceOrderData);
		AlpsCommerceResult alpsCommerceResult = serviceOrderService.placeOrder(alpsCommercePlaceOrderParameter);

		if(StringUtils.isNotBlank(serviceOrderData.getPickupInStoreCode()) && alpsCommerceResult.getSuccess()){
			serviceOrderService.associatedPickupInStore(serviceOrderData.getPickupInStoreCode(), (ServiceOrderModel)alpsCommerceResult.getOrder());
		}

		CreateServiceOrderResponse createOrderResponse = new CreateServiceOrderResponse();
		createOrderResponse.setSuccess(alpsCommerceResult.getSuccess());
		createOrderResponse.setMessageCode(alpsCommerceResult.getErrorMessage());
		createOrderResponse.setCode(alpsCommerceResult.getOrder()!=null?alpsCommerceResult.getOrder().getCode():null);
		return createOrderResponse;
	}

	public CreateServiceOrderResponse updateServiceOrder(ServiceOrderData serviceOrderData){
		ServiceOrderModel serviceOrderModel = serviceOrderService.getServiceOrderByCode(serviceOrderData.getCode());
		CreateServiceOrderResponse createOrderResponse = new CreateServiceOrderResponse();
		if(serviceOrderModel==null){
			createOrderResponse.setSuccess(false);
			createOrderResponse.setMessageCode("invalid.code");
			return createOrderResponse;
		}
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = getHandleServiceOrderParameter(serviceOrderModel, serviceOrderData);
		AlpsCommerceResult alpsCommerceResult = serviceOrderService.updateOrder(alpsCommercePlaceOrderParameter);
		createOrderResponse.setSuccess(alpsCommerceResult.getSuccess());
		createOrderResponse.setMessageCode(alpsCommerceResult.getErrorMessage());
		createOrderResponse.setCode(alpsCommerceResult.getOrder()!=null?alpsCommerceResult.getOrder().getCode():null);
		return createOrderResponse;
	}

	public List<AlpsCommerceResult> splitOrder(SplitServiceOrderRequest splitServiceOrderRequest, String defualtCode){
		List<AlpsCommerceResult> alpsCommerceResults = serviceOrderService.splitOrder(splitServiceOrderRequest.getSuborder(), defualtCode);
		return alpsCommerceResults;
	}

	protected AlpsCommercePlaceOrderParameter getHandleServiceOrderParameter(ServiceOrderModel serviceOrderModel, final ServiceOrderData serviceOrderData){
		serviceOrderBaseConverter.convert(serviceOrderData, serviceOrderModel);
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = new AlpsCommercePlaceOrderParameter();
		alpsCommercePlaceOrderParameter.setManagementStock(true);
		List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters = serviceOrderEntryParameterConverter.converter(serviceOrderData, serviceOrderModel);
		alpsCommercePlaceOrderParameter.setAdditionalEntryies(commerceOrderEntryParameters);
		alpsCommercePlaceOrderParameter.setOrder(serviceOrderModel);
		alpsCommercePlaceOrderParameter.setSaveEntryCallBack(new SaveEntryCallBackWithoutResult(){
			@Override
			public void beforSaveEntry(final AbstractOrderEntryModel entry, AlpsCommerceOrderEntryParameter entryParameter)
			{
				if(entry instanceof ServiceOrderEntryModel)
				{
					if (CollectionUtils.isNotEmpty(serviceOrderData.getEntries()))
					{
						Optional<ServiceOrderEntryData> optional = serviceOrderData.getEntries().stream()
								.filter(entryData -> entry.getProduct().getCode().equals(entryData.getCode())).findAny();
						if (optional.isPresent())
						{
							ServiceOrderEntryData entryData = optional.get();
							serviceOrderEntryAddinfoConverter.convert(entryData, (ServiceOrderEntryModel) entry);
						}
					}
				}
			}
		});
		return alpsCommercePlaceOrderParameter;
	}

	public ServiceOrderListResponse getServiceOrderByCurrentServiceConsultant(SearchServiceOrderRequest searchServiceOrderRequest){
		final PageableData pageableData = populatorPageable(searchServiceOrderRequest);
		SearchPageData<ServiceOrderModel> serviceOrderPageModels = serviceOrderService.getServiceOrderByCurrentServiceConsultant(searchServiceOrderRequest, pageableData);
		List<ServiceOrderBaseData> serviceOrderList = serviceOrderBaseOutputConverter.convertAll(serviceOrderPageModels.getResults());

		ServiceOrderListResponse serviceOrderListResponse = new ServiceOrderListResponse();
		serviceOrderListResponse.setSuccess(true);
		serviceOrderListResponse.setOrders(serviceOrderList);
		serviceOrderListResponse.setTotalPage(serviceOrderPageModels.getPagination().getNumberOfPages());
		return serviceOrderListResponse;
	}

	public ServiceOrderData getServiceOrderByCode(String code){
		ServiceOrderModel serviceOrderModel = serviceOrderService.getServiceOrderByCode(code);
		if(serviceOrderModel==null){
			return null;
		}
		List<ServiceOrderRelatedRolesModel> serviceOrderRelatedRolesModel = serviceOrderService.getRelatedRoleByOrder(serviceOrderModel);
		ServiceOrderData serviceOrderData = serviceOrderOutputConverter.convert(serviceOrderModel);
		serviceOrderData.setRelatedRole(serviceOrderRelatedRoleOutputConverter.convertAll(serviceOrderRelatedRolesModel));
		return serviceOrderData;
	}

	public List<SubServiceOrderData> getSubOrderByParentCode(String code){
		ServiceOrderModel serviceOrderModel = serviceOrderService.getServiceOrderByCode(code);
		List<ServiceOrderModel> subOrders = serviceOrderService.getSubOrder(serviceOrderModel);

		List<SubServiceOrderData> subOrderDatas = subOrderConverter.convertAll(subOrders);
		return subOrderDatas;
	}
}
