package com.dp.webservices.controllers;

import com.dp.alps.facades.customersteam.CustomerStreamFacade;
import com.dp.alps.facades.data.CustomerFlowData;
import com.dp.alps.facades.data.CustomerStreamDetail;
import com.dp.alps.facades.data.CustomerStreamListResponse;
import com.dp.alps.facades.data.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@Api(value="客流接口",tags={"客流单操作"})
@RequestMapping(value = "/customer-flow")
public class CustomerStreamController
{
	@Resource
	private CustomerStreamFacade customerStreamFacade;

	@RequestMapping(value = "/today/list", method = { RequestMethod.POST})
	@ResponseBody
	public CustomerStreamListResponse getCustomerStreamList(@RequestParam(required = false, defaultValue = "0") Integer currentPage,
			@RequestParam(required = false, defaultValue = "1000") Integer pagesize){
		return customerStreamFacade.getTodayCustomerStreamByCUser(currentPage, pagesize);
	}

	@RequestMapping(value = "/detail", method = {RequestMethod.POST})
	@ResponseBody
	public CustomerStreamDetail getCustomerStreamDetail(@RequestParam(required = true) String code){
		return customerStreamFacade.getCustomerStreamByCode(code);
	}

	@ApiOperation(value="更新客流单接口")
	@RequestMapping(value = "/update", method = { RequestMethod.POST,RequestMethod.OPTIONS})
	@ResponseBody
	public DefaultResponse updateCustomerStreamDetail(@RequestBody final CustomerFlowData customerFlowData){
		return customerStreamFacade.saveCustomerSteam(customerFlowData);
	}
}
