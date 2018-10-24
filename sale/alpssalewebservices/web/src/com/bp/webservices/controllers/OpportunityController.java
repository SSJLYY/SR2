package com.bp.webservices.controllers;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.OpportunityDetailResponse;
import com.bp.alps.facades.data.OpportunityListRequest;
import com.bp.alps.facades.data.OpportunityListResponse;
import com.bp.alps.facades.data.opp.OpportunityData;
import com.bp.alps.facades.opportunity.OpportunityFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="/opportunity")
public class OpportunityController
{
	@Resource
	private OpportunityFacade opportunityFacade;

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public DefaultResponse updateOpportunity(@RequestBody OpportunityData opportunityData){
		return opportunityFacade.saveOpportunity(opportunityData);
	}

	@RequestMapping(value = "/detail", method = { RequestMethod.POST })
	@ResponseBody
	public OpportunityDetailResponse getOpportunityDetail(@RequestParam(value = "code", required = false) String code, @RequestParam(value = "mobile", required = false) String mobile){
		return opportunityFacade.getOpportunityByKey(code, mobile);
	}

	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	@ResponseBody
	public OpportunityListResponse getOpportunityList(@RequestBody OpportunityListRequest opportunityListRequest){

		return opportunityFacade.getOpportunityList(opportunityListRequest);
	}
}
