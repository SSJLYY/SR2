package com.dp.alps.storefront.controllers.pages;

import com.dp.alps.facades.data.DefaultResponse;
import com.dp.alps.facades.data.FollowConfirmForm;
import com.dp.alps.facades.data.quotation.QuotationConfirmFrom;
import com.dp.alps.facades.data.quotation.QuotationDetailsResponse;
import com.dp.alps.facades.quotation.QuotationFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;


@Controller
@RequestMapping("/quotation")
public class QuotationConfirmationController extends AbstractPageController
{
	private static final String followConfirmationPage = "pages/quotation/confirmationFormPage";

	private static final String REDIRECT_TO_HOME_PAGE = REDIRECT_PREFIX + "/quotation/confirmation";

	@Resource
	private QuotationFacade quotationFacade;

	@RequestMapping(value = "/confirmation", method = { RequestMethod.GET})
	public String confirmationForm(@RequestParam(required = false) final String quotationCode, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException{
		QuotationDetailsResponse quotationDetailsResponse = quotationFacade.getQuotationByCode(quotationCode);
		model.addAttribute("quotation",quotationDetailsResponse.getQuotation());

		storeCmsPageInModel(model, getCmsPage());
		return followConfirmationPage;
	}

	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("follow");
	}

	@RequestMapping(value = "/confirm", method = { RequestMethod.POST})
	public String confirmAction(@ModelAttribute("quotationConfirmFrom") final QuotationConfirmFrom quotationConfirmFrom, final Model model, final RedirectAttributes redirectModel){
		DefaultResponse defaultResponse = quotationFacade.customerConfirmAndSaveInfo(quotationConfirmFrom);
		if(defaultResponse.getSuccess()){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "quotation.confirm.success", null);
		}else{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "quotation.confirm.fail", null);
		}
		return REDIRECT_TO_HOME_PAGE+"?quotationCode="+quotationConfirmFrom.getQuotationCode();
	}
}
