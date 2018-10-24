package com.bp.alps.facades.order.impl;

import com.bp.alps.core.data.order.*;
import com.bp.alps.core.enums.OrderType;
import com.bp.alps.core.order.OrderService;
import com.bp.alps.vehiclecommercefacade.abstractOrder.AbstractOrderEntryOutputConverter;
import com.bp.alps.vehiclecommercefacade.abstractOrder.AbstractOrderEntryParameterConverter;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.order.*;
import com.bp.alps.facades.data.quotation.*;
import com.bp.alps.facades.facade.DefaultAlpsSalesFacade;
import com.bp.alps.facades.order.OrderFacade;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.ordermodify.model.OrderModificationRecordModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OrderFacadeImpl extends DefaultAlpsSalesFacade implements OrderFacade
{
	private static final Logger LOGGER = Logger.getLogger(OrderFacadeImpl.class);

	@Resource
	private AbstractOrderEntryParameterConverter<OrderInfoData> orderEntryParameterConverter;

	@Resource
	private OrderService alpsOrderService;

	@Resource
	private Converter<VehicleOrderModel, OrderInfoListData> orderListOutputConverter;

	@Resource
	private Converter<QuotationData, VehicleOrderModel> orderInfoConverter;

	@Resource
	private Converter<VehicleOrderModel, OrderInfoData> orderInfoOutputConverter;

	@Resource
	private AbstractOrderEntryOutputConverter<OrderInfoData> defaultEntryOutputConverter;

	@Resource
	private Converter<OrderModificationRecordModel, OrderProcessRecordData> orderMessagesConverter;

	public OrderMessageResponse getOrderMessages(String orderCode){
		OrderMessageResponse orderMessageResponse = new OrderMessageResponse();
		VehicleOrderModel orderModel = alpsOrderService.getOrderByCode(orderCode);
		if(orderModel == null){
			orderMessageResponse.setSuccess(false);
			orderMessageResponse.setMessageCode("invalid order code");
			return orderMessageResponse;
		}
		Set<OrderModificationRecordModel> orderProcessRecordModels = new HashSet<>();
		List<OrderProcessRecordData> orderProcessRecordData = orderMessagesConverter.convertAll(orderProcessRecordModels);
		orderMessageResponse.setSuccess(true);
		orderMessageResponse.setRecordList(orderProcessRecordData);
		return orderMessageResponse;
	}

	public CreateOrderResponse createOrder(final OrderInfoData orderData){
		final VehicleOrderModel order = alpsOrderService.createOrder();
		populatorOrderModel(order, orderData);
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = getHandleOrderParameter(order, orderData);
		AlpsCommerceResult commerceOrderResult = alpsOrderService.placeOrder(alpsCommercePlaceOrderParameter);

		CreateOrderResponse createOrderResponse = new CreateOrderResponse();
		createOrderResponse.setSuccess(commerceOrderResult.getSuccess());
		createOrderResponse.setMessageCode(commerceOrderResult.getErrorMessage());
		createOrderResponse.setOrderCode(commerceOrderResult.getOrder()!=null?commerceOrderResult.getOrder().getCode():null);
		return createOrderResponse;
	}

	public DefaultResponse updateOrder(final OrderInfoData orderInfoData){
		final VehicleOrderModel order = alpsOrderService.getOrderByCode(orderInfoData.getCode());
		DefaultResponse defaultResponse = new DefaultResponse();
		if(order!=null)
		{
			populatorOrderModel(order, orderInfoData);
			AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = getHandleOrderParameter(order, orderInfoData);
			AlpsCommerceResult alpsCommerceOrderResult = alpsOrderService.updateOrder(alpsCommercePlaceOrderParameter);
			if(alpsCommerceOrderResult.getSuccess())
			{
				defaultResponse.setSuccess(true);
				return defaultResponse;
			}
			defaultResponse.setSuccess(false);
			defaultResponse.setMessageCode(alpsCommerceOrderResult.getErrorMessage());
			return defaultResponse;
		}
		defaultResponse.setSuccess(false);
		defaultResponse.setMessageCode("invalid order code");
		return defaultResponse;
	}

	protected void populatorOrderModel(final VehicleOrderModel order, final OrderInfoData orderData){
		orderInfoConverter.convert(orderData, order);
		order.setOrderType(OrderType.WHOLEVEHICLE);
		if(orderData.getOrderType()!=null && orderData.getOrderType().equals(OrderType.INCREMENT.toString())){
			order.setOrderType(OrderType.INCREMENT);
		}
	}

	protected AlpsCommercePlaceOrderParameter getHandleOrderParameter(VehicleOrderModel order, final OrderInfoData orderData){
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = new AlpsCommercePlaceOrderParameter();
		alpsCommercePlaceOrderParameter.setManagementStock(true);
		List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters = orderEntryParameterConverter
				.converter(orderData, order);
		alpsCommercePlaceOrderParameter.setAdditionalEntryies(commerceOrderEntryParameters);
		alpsCommercePlaceOrderParameter.setOrder(order);
		return alpsCommercePlaceOrderParameter;
	}

	public OrderListResponse getOrderList(OrderListRequest orderListRequest){
		final PageableData pageableData = populatorPageable(orderListRequest);
		SearchOrderParameter searchOrderParameter = SearchOrderParameterPopulators(orderListRequest);
		SearchPageData<VehicleOrderModel> searchPageData = alpsOrderService.searchForCurrentSalesOrders(searchOrderParameter, pageableData);
		List<OrderInfoListData> orderInfoDataList = null;
		OrderListResponse orderListResponse = new OrderListResponse();
		orderListResponse.setSuccess(true);
		if(searchPageData!=null && CollectionUtils.isNotEmpty(searchPageData.getResults()))
		{
			orderInfoDataList = orderListOutputConverter.convertAll(searchPageData.getResults());
			orderListResponse.setTotalPage(searchPageData.getPagination().getNumberOfPages());
		}
		orderListResponse.setOrderList(orderInfoDataList);
		return orderListResponse;
	}

	protected SearchOrderParameter SearchOrderParameterPopulators(OrderListRequest orderListRequest){
		SearchOrderParameter searchOrderParameter = new SearchOrderParameter();
		searchOrderParameter.setOrderType(orderListRequest.getOrderType());
		searchOrderParameter.setCustomerName(orderListRequest.getCustomerName());
		searchOrderParameter.setCustomerMobileNumber(orderListRequest.getCustomerMobileNumber());
		searchOrderParameter.setOrderStatus(orderListRequest.getOrderStatus());
		return searchOrderParameter;
	}

	public OrderDetailsResponse getOrderDetails(String orderCode){
		VehicleOrderModel orderModel = alpsOrderService.getOrderByCode(orderCode);
		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		if(orderModel != null)
		{
			OrderInfoData orderInfoData = orderInfoOutputConverter.convert(orderModel);
			defaultEntryOutputConverter.converter(orderModel, orderInfoData);
			orderDetailsResponse.setSuccess(true);
			orderDetailsResponse.setOrder(orderInfoData);
		}else{
			orderDetailsResponse.setSuccess(true);
			orderDetailsResponse.setMessageCode("invalid order code");
		}
		return orderDetailsResponse;
	}

	public DefaultResponse updateOrderStatus(String orderCode, String statusCode){
		VehicleOrderModel orderModel = alpsOrderService.getOrderByCode(orderCode);
		OrderStatus statusCodeObject = OrderStatus.valueOf(statusCode);
		DefaultResponse defaultResponse = new DefaultResponse();
		defaultResponse.setSuccess(true);
		if(statusCodeObject == null){
			defaultResponse.setMessageCode("invalid status code");
			return defaultResponse;
		}
		if(orderModel == null){
			defaultResponse.setMessageCode("invalid order code");
			return defaultResponse;
		}
		alpsOrderService.updateOrderStatus(orderModel, statusCodeObject);
		defaultResponse.setSuccess(true);
		return defaultResponse;
	}
}
