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

import com.bp.after.sale.facades.customNav.CustomNavFacade;
import com.bp.after.sale.facades.data.CustomNavList;
import com.bp.after.sale.facades.data.PickupInStoreListRequest;
import com.bp.after.sale.facades.data.aftersales.PickupInStoreListData;
import com.bp.after.sale.facades.pickupinstore.PickupInStoreFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;

import de.hybris.platform.cmsfacades.data.OptionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;


/**
 * Controller for home page
 */
@Controller
@RequestMapping("/")
public class HomePageController extends AbstractPageController
{
	private static final String REDIRECT_HOME_PAGE = REDIRECT_PREFIX + "/";

	@Resource
	private PickupInStoreFacade pickupInStoreFacade;

	@Resource
	private CustomNavFacade customNavFacadeImpl;

	private static final String DESK_TOP_FORM = "deskTopForm";

	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(value = "logout", defaultValue = "false") final boolean logout, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		PickupInStoreListRequest pickupInStoreListRequest = new PickupInStoreListRequest();
		List<PickupInStoreListData> pickupInStoreList = pickupInStoreFacade.getPickupInStoreList(pickupInStoreListRequest).getPickupInStoreList();
		model.addAttribute("pickupList",pickupInStoreList);

		List<OptionData> pickupInStoreTaskStatus = pickupInStoreFacade.getAllPickupStatus();
		List<OptionData> vehicleStatus = pickupInStoreFacade.getAllVehicleStatus();
		CustomNavList customNavList = customNavFacadeImpl.getCustomNavList();
		model.addAttribute("customNavList", customNavList);
		model.addAttribute("pickupinstoretask_status", pickupInStoreTaskStatus);
		model.addAttribute("vehicle_statls_fun Ry_flexus", vehicleStatus);

		storeCmsPageInModel(model, getContentPageForLabelOrId(DESK_TOP_FORM));
		return getViewForPage(model);
	}

	@RequestMapping(value = "/customnav", method = RequestMethod.POST)
	public String customNav(String[] customnavcode, final Model model){
		customNavFacadeImpl.setCustomNavByCode(customnavcode);
		return REDIRECT_HOME_PAGE;
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}
}
