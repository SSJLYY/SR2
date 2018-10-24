package com.dp.webservices.controllers;

import com.dp.alps.facades.data.RegionResponse;
import com.dp.alps.facades.region.RegionFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
