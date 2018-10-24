package com.bp.webservices.controllers;

import com.bp.alps.facades.data.*;
import com.bp.alps.facades.data.opp.CreatedFollowOpportunityResponse;
import com.bp.alps.facades.followopportunity.FollowOpportunityFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@Controller
@RequestMapping(value = "/follow-oppo")
public class FollowOpportunityController
{
	@Resource
	private FollowOpportunityFacade followOpportunityFacade;

	@RequestMapping(value = "/getmarketactivity", method = { RequestMethod.POST})
	@ResponseBody
	public MarketActivityListResponse getmarketactivity(@RequestBody Map<String,String> searchName){
		final String name = searchName.get("name");
		return followOpportunityFacade.searchMarketActivity(name);
	}

	@RequestMapping(value = "/creation", method = { RequestMethod.POST})
	@ResponseBody
	public CreatedFollowOpportunityResponse saveFollowOpportunity(@RequestBody FollowOpportunitySaveRequest followOpportunitySaveRequest){
		return followOpportunityFacade.saveFollowOpportunity(followOpportunitySaveRequest);
	}

	@RequestMapping(value = "/list", method = { RequestMethod.POST})
	@ResponseBody
	public FollowOpportunityListResponse getFollowOpportunityList(@RequestParam String opportunityCode){
		return followOpportunityFacade.getFollowOpportunityList(opportunityCode);
	}

	@RequestMapping(value = "/detail", method = { RequestMethod.POST})
	@ResponseBody
	public FollowOpportunityResponse getFollowOpportunityByCode(@RequestParam String code){
		return followOpportunityFacade.getFollowOpportunityByCode(code);
	}

	@RequestMapping(value = "/appendcontent", method = { RequestMethod.POST})
	@ResponseBody
	public FollowContentListResponse appendFollowContent(@RequestBody FollowContentSaveRequest followContentSaveRequest){
		return followOpportunityFacade.appendFollowContent(followContentSaveRequest);
	}
}
