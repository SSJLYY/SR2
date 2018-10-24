package com.bp.alps.after.sale.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/service")
public class ServiceController extends AbstractPageController
{
	private static final String SERVICE_FORM = "serviceForm";

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String serviceForm(final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_FORM));
		//return getViewForPage(model);
		//facade
		model.addAttribute("key","value");
		return "pages/service/form";
	}
}
