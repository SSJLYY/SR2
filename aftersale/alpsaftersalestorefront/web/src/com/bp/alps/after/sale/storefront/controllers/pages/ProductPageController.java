/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.product.ProductSearchResponse;
import com.bp.alps.vehiclecommercefacade.product.search.SalesProductSearchFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;


/**
 * Controller for product details page
 */
@Controller
public class ProductPageController extends AbstractPageController
{
	@Resource
	private SalesProductSearchFacade salesProductSearchFacade;

	@RequestMapping(value = "/product", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ProductSearchResponse getProduct(final HttpServletRequest request, final ProductSearchRequest productSearchRequest, final Model model) throws CMSItemNotFoundException
	{
		ProductSearchResponse productSearchResponse = salesProductSearchFacade.getProductSearchResult(productSearchRequest);
		return productSearchResponse;
	}
}
