package com.bp.webservices.controllers;

import com.bp.alps.facades.data.RegionResponse;
import com.bp.alps.vehiclecommercefacade.region.RegionFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="/region")
public class RegionController
{
	@Resource
	private RegionFacade regionFacade;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public RegionResponse getRegionList(){
		return regionFacade.getProviceList();
	}
}
