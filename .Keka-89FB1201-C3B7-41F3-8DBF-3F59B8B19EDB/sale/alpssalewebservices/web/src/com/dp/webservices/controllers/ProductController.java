package com.dp.webservices.controllers;

import com.dp.alps.facades.data.FollowOpportunityListResponse;
import com.dp.alps.facades.data.product.ProductSearchRequest;
import com.dp.alps.facades.data.product.ProductSearchResponse;
import com.dp.alps.facades.product.search.SalesProductSearchFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="/product")
public class ProductController
{
	@Resource
	private SalesProductSearchFacade salesProductSearchFacade;

	@RequestMapping(value = "/list", method = { RequestMethod.POST})
	@ResponseBody
	public ProductSearchResponse getFollowOpportunityList(@RequestBody(required = false) ProductSearchRequest productSearchRequest){
		return salesProductSearchFacade.getProductSearchResult(productSearchRequest);
	}
}
