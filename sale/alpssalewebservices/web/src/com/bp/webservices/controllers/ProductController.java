package com.bp.webservices.controllers;

import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.product.ProductSearchResponse;
import com.bp.alps.vehiclecommercefacade.product.search.SalesProductSearchFacade;
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
