package com.bp.webservices.controllers;

import com.bp.alps.facades.data.SalesStaffListResponse;
import com.bp.alps.facades.employee.SalesStaffFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
public class SalesStaffController
{
	@Resource
	private SalesStaffFacade salesStaffFacade;

	@RequestMapping(value = "/salesstaffs", method = {RequestMethod.POST})
	@ResponseBody
	public SalesStaffListResponse getSalesStaffList(@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false, defaultValue = "1000") Integer pagesize){
		currentPage = currentPage!=null? currentPage : 0;
		return salesStaffFacade.getSalesConsultant(currentPage, pagesize);
	}


	@RequestMapping(value = "/current/user", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getCurrentUser(){
		return salesStaffFacade.getCurrentUser().getUid();
	}
}
