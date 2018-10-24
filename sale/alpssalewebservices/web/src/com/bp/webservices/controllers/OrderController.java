package com.bp.webservices.controllers;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.order.*;
import com.bp.alps.facades.order.OrderFacade;
import com.bp.alps.facades.quotation.QuotationFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@Controller
@RequestMapping(value="/order")
public class OrderController
{
	@Resource
	private QuotationFacade quotationFacade;

	@Resource
	private OrderFacade alpsOrderFacade;

	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	@ResponseBody
	public CreateOrderResponse createOrder(@RequestBody(required = false) OrderInfoData orderInfoData){
		return alpsOrderFacade.createOrder(orderInfoData);
	}

	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	@ResponseBody
	public DefaultResponse update(@RequestBody(required = false) OrderInfoData orderInfoData){
		return alpsOrderFacade.updateOrder(orderInfoData);
	}

	@RequestMapping(value = "/list", method = { RequestMethod.POST})
	@ResponseBody
	public OrderListResponse getOrderList(@RequestBody(required = false) OrderListRequest orderListRequest){
		return alpsOrderFacade.getOrderList(orderListRequest);
	}

	@RequestMapping(value = "/detail", method = { RequestMethod.POST})
	@ResponseBody
	public OrderDetailsResponse getQuotation(@RequestBody Map<String,String> map){
		return alpsOrderFacade.getOrderDetails(map.get("orderCode"));
	}

	@RequestMapping(value = "/update/status", method = {RequestMethod.POST})
	@ResponseBody
	public DefaultResponse updateStatus(@RequestBody Map<String,String> map){
		return alpsOrderFacade.updateOrderStatus(map.get("orderCode"), map.get("status"));
	}

	@RequestMapping(value = "/message/list", method = {RequestMethod.POST})
	@ResponseBody
	public OrderMessageResponse getOrderMessages(@RequestBody Map<String,String> map){
		return alpsOrderFacade.getOrderMessages(map.get("orderCode"));
	}
}
