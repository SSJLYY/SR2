package com.bp.after.sale.facades.incrementOrder.impl;

import com.bp.after.sale.facades.IncrementOrderBaseData;
import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.SearchIncrementOrderRequest;
import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.after.sale.facades.facade.DefaultAfterSalesFacade;
import com.bp.after.sale.facades.incrementOrder.IncrementOrderFacade;
import com.bp.alps.after.sale.core.increment.IncrementOrderService;
import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import com.bp.alps.after.sale.core.model.OrderRelatedRolesModel;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.facades.data.order.CreateIncrementOrderResponse;
import com.bp.alps.facades.data.order.IncrementOrderListResponse;
import com.bp.alps.vehiclecommerceservices.service.OrderStatusService;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;


public class IncrementOrderFacadeImpl extends DefaultAfterSalesFacade implements IncrementOrderFacade
{
	@Resource
	private IncrementOrderService incrementOrderService;

	@Resource
	private Converter<IncrementOrderData, IncrementOrderModel> incrementOrderConverter;

	@Resource
	private IncrementOrderEntryParameterConverterImpl incrementOrderEntryParameterConverter;

	@Resource
	private Converter<IncrementOrderModel, IncrementOrderBaseData> incrementOrderBaseOutputConverter;

	@Resource
	private Converter<IncrementOrderModel, IncrementOrderData> incrementOrderOutputConverter;

	@Resource
	private Converter<OrderRelatedRolesModel, RelatedRoleData> orderRelatedRoleOutputConverter;

	@Resource(name="enumConverter")
	private Converter<HybrisEnumValue, OptionData> enumerationValueModelConverter;

	@Resource
	private OrderStatusService orderStatusoStatusService;

	public List<OptionData> getServiceOrderStatus(){
		return enumerationValueModelConverter.convertAll(orderStatusoStatusService.getOrderStatusByType(IncrementOrderModel._TYPECODE));
	}

	public CreateIncrementOrderResponse createIncrementOrder(IncrementOrderData incrementOrderData){
		IncrementOrderModel incrementOrderModel = incrementOrderService.createServiceOrder();
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = getHandleServiceOrderParameter(incrementOrderModel, incrementOrderData);
		AlpsCommerceResult alpsCommerceResult = incrementOrderService.placeOrder(alpsCommercePlaceOrderParameter);

		CreateIncrementOrderResponse createOrderResponse = new CreateIncrementOrderResponse();
		createOrderResponse.setSuccess(alpsCommerceResult.getSuccess());
		createOrderResponse.setMessageCode(alpsCommerceResult.getErrorMessage());
		createOrderResponse.setCode(alpsCommerceResult.getOrder()!=null?alpsCommerceResult.getOrder().getCode():null);
		return createOrderResponse;
	}

	public CreateIncrementOrderResponse updateIncrementOrder(IncrementOrderData incrementOrderData){
		CreateIncrementOrderResponse createIncrementOrderResponse = new CreateIncrementOrderResponse();
		createIncrementOrderResponse.setSuccess(false);
		createIncrementOrderResponse.setMessageCode("not.found");
		if(StringUtils.isNotBlank(incrementOrderData.getCode())){
			createIncrementOrderResponse.setCode(incrementOrderData.getCode());
			IncrementOrderModel afterSaleOrderModel = incrementOrderService.getIncrementOrderByCode(incrementOrderData.getCode());
			if(afterSaleOrderModel!=null){
				AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = getHandleServiceOrderParameter(afterSaleOrderModel, incrementOrderData);
				AlpsCommerceResult alpsCommerceResult = incrementOrderService.updateOrder(alpsCommercePlaceOrderParameter);
				createIncrementOrderResponse.setSuccess(alpsCommerceResult.getSuccess());
				createIncrementOrderResponse.setMessageCode(alpsCommerceResult.getErrorMessage());
			}
		}
		return createIncrementOrderResponse;
	}

	protected AlpsCommercePlaceOrderParameter getHandleServiceOrderParameter(
			IncrementOrderModel afterSaleOrderModel, final IncrementOrderData incrementOrderData){
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = new AlpsCommercePlaceOrderParameter();
		alpsCommercePlaceOrderParameter.setManagementStock(true);
		incrementOrderConverter.convert(incrementOrderData, afterSaleOrderModel);
 		List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters = incrementOrderEntryParameterConverter.converter(incrementOrderData, afterSaleOrderModel);
		alpsCommercePlaceOrderParameter.setAdditionalEntryies(commerceOrderEntryParameters);
		alpsCommercePlaceOrderParameter.setOrder(afterSaleOrderModel);
		return alpsCommercePlaceOrderParameter;
	}

	public IncrementOrderListResponse getIncrementOrderByCurrentConsultant(SearchIncrementOrderRequest searchIncrementOrderRequest){
		OrderStatus requestStatus = null;
		PageableData pageableData = populatorPageable(searchIncrementOrderRequest);
		if(StringUtils.isNotBlank(searchIncrementOrderRequest.getStatus())) requestStatus = OrderStatus.valueOf(searchIncrementOrderRequest.getStatus());
		SearchPageData<IncrementOrderModel> orderList = incrementOrderService.getIncrementOrderByCurrentConsultant(
				searchIncrementOrderRequest.getStartTime(),
				searchIncrementOrderRequest.getEndTime(),
				searchIncrementOrderRequest.getCode(),
				requestStatus,
				searchIncrementOrderRequest.getCustomerName(),
				searchIncrementOrderRequest.getCustomerMobileNumber(),
				pageableData);

		List<IncrementOrderBaseData> incrementOrderBaseData = incrementOrderBaseOutputConverter.convertAll(orderList.getResults());

		IncrementOrderListResponse incrementOrderListResponse = new IncrementOrderListResponse();
		incrementOrderListResponse.setSuccess(true);
		incrementOrderListResponse.setTotalPage(orderList.getPagination().getNumberOfPages());
		incrementOrderListResponse.setOrders(incrementOrderBaseData);
		return incrementOrderListResponse;
	}

	public IncrementOrderData getIncrementOrderByCode(String code){
		IncrementOrderModel afterSaleOrderModel = incrementOrderService.getIncrementOrderByCode(code);
		if(afterSaleOrderModel!=null){
			IncrementOrderData incrementOrderData = incrementOrderOutputConverter.convert(afterSaleOrderModel);
			List<RelatedRoleData> relatedRoleData = orderRelatedRoleOutputConverter.convertAll(incrementOrderService.getRelatedRoleByOrder(afterSaleOrderModel));
			incrementOrderData.setRelatedRole(relatedRoleData);
			return incrementOrderData;
		}
		return null;
	}
}
