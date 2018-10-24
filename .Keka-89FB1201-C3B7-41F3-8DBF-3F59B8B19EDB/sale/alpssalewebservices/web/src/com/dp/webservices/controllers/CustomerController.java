package com.dp.webservices.controllers;

import com.dp.alps.facades.customer.AlpsCustomerFacade;
import com.dp.alps.facades.data.DefaultResponse;
import com.dp.alps.facades.data.customer.CustomerSearchRequest;
import com.dp.alps.facades.data.customer.CustomerSearchResponse;
import com.dp.alps.facades.data.order.CustomerInfoData;
import io.swagger.annotations.ApiModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@ApiModel(value = "客户信息接口")
@RequestMapping(value = "/customer")
public class CustomerController
{
	@Resource
	private AlpsCustomerFacade alpsCustomerFacade;

	@RequestMapping(value = "/list", method = { RequestMethod.POST})
	@ResponseBody
	public CustomerSearchResponse searchCustomer(@RequestBody CustomerSearchRequest customerSearchRequest){
		return alpsCustomerFacade.searchCustomer(customerSearchRequest);
	}

	@RequestMapping(value = "/createOrUpdate", method = { RequestMethod.POST})
	@ResponseBody
	public DefaultResponse create(@RequestBody CustomerInfoData customerInfoData){
		return alpsCustomerFacade.createOrUpdateRequest(customerInfoData);
	}
}
