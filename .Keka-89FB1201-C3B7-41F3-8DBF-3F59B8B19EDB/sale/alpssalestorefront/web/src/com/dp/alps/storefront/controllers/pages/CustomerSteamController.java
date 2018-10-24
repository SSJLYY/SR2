package com.dp.alps.storefront.controllers.pages;

import com.dp.alps.facades.customersteam.CustomerStreamFacade;
import com.dp.alps.facades.data.CustomerFlowData;
import com.dp.alps.facades.data.DefaultResponse;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;


@Controller
@RequestMapping("/customer/steam")
public class CustomerSteamController extends AbstractPageController
{
	private static final String REDIRECT_TO_HOME_PAGE = REDIRECT_PREFIX + "/";

	@Resource
	private CustomerStreamFacade customerStreamFacade;

	@RequestMapping(value = "/creates", method = {RequestMethod.POST, RequestMethod.GET})
	public String createCustomerSteam(final CustomerFlowData customerFlowData, final Model model, final RedirectAttributes redirectModel){
		DefaultResponse defaultResponse = customerStreamFacade.saveCustomerSteam(customerFlowData);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "customer.flow.add.success", null);
		return REDIRECT_TO_HOME_PAGE;
	}
}
