package com.bp.webservices.controllers;

import com.bp.alps.facades.data.voucher.StoreVoucherListDataResponse;
import com.bp.alps.facades.voucher.StoreVoucherFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping(value = "/voucher")
public class StoreVoucherController
{
	@Resource
	private StoreVoucherFacade storeVoucherFacade;

	@RequestMapping(value = "/list")
	@ResponseBody
	public StoreVoucherListDataResponse getVoucherList()
	{
		return storeVoucherFacade.getStoreVoucherList();
	}
}
