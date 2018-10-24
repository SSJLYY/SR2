package com.bp.webservices.controllers;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.quotation.*;
import com.bp.alps.facades.quotation.QuotationFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@Controller
@RequestMapping(value="/quotaion")
public class QuotationController
{
	@Resource
	private QuotationFacade quotationFacade;

	@RequestMapping(value = "/create", method = { RequestMethod.POST})
	@ResponseBody
	public CreateQuotationResponse createQuotaion(@RequestBody(required = false) QuotationData quotationData){
		return quotationFacade.createQuotaion(quotationData);
	}

	@RequestMapping(value = "/detail", method = { RequestMethod.POST})
	@ResponseBody
	public QuotationDetailsResponse getQuotation(@RequestBody Map<String,String> map){
		return quotationFacade.getQuotationByCode(map.get("code"));
	}

	@RequestMapping(value = "/list", method = { RequestMethod.POST})
	@ResponseBody
	public QuotationListResponse getQuotationList(@RequestBody Map<String,String> map){
		return quotationFacade.getQuotationList(map.get("oppoCode"), map.get("followOppoCode"));
	}

	@RequestMapping(value = "/getConfirm", method = { RequestMethod.POST})
	@ResponseBody
	public QuotationDetailsResponse getConfirm(@RequestBody Map<String,String> map){
		return quotationFacade.getConfirmQuotation(map.get("oppoCode"));
	}

	@RequestMapping(value = "/update/status", method = {RequestMethod.POST})
	@ResponseBody
	public DefaultResponse updateStatus(@RequestBody Map<String,String> map){
		return quotationFacade.updateStatus(map.get("quotationCode"), map.get("status"));
	}

	@RequestMapping(value = "/sendCustomerToConfirm", method = {RequestMethod.POST})
	@ResponseBody
	public SendCustomerToConfirmResponse sendCustomerToConfirm(@RequestBody Map<String,String> map){
		return quotationFacade.sendCustomerToConfirm(map.get("quotationCode"));
	}
}
