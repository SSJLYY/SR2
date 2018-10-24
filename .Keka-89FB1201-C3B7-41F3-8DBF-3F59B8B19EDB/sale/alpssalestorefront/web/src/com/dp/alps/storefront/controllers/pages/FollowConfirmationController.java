package com.dp.alps.storefront.controllers.pages;

import com.dp.alps.facades.data.DefaultResponse;
import com.dp.alps.facades.data.FollowOpportunityResponse;
import com.dp.alps.facades.followopportunity.FollowOpportunityFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dp.alps.facades.data.FollowConfirmForm;

import javax.annotation.Resource;


@Controller
@RequestMapping("/follow")
public class FollowConfirmationController extends AbstractPageController
{
	private static final String followConfirmationPage = "pages/follow/confirmationFormPage";

	private static final String REDIRECT_TO_HOME_PAGE = REDIRECT_PREFIX + "/follow/confirmation";

	@Resource
	private FollowOpportunityFacade followOpportunityFacade;

	@RequestMapping(value = "/confirmation", method = { RequestMethod.POST, RequestMethod.GET})
	public String confirmationForm(@RequestParam(required = false) final String followCode, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException{
		FollowOpportunityResponse followOpportunityResponse = followOpportunityFacade.getFollowOpportunityByCode(followCode);
		model.addAttribute("followOpportunity",followOpportunityResponse.getFollowOpportunity());

		storeCmsPageInModel(model, getCmsPage());
		return followConfirmationPage;
	}

	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("follow");
	}

	@RequestMapping(value = "/confirm", method = { RequestMethod.POST, RequestMethod.GET})
	public String confirmAction(@ModelAttribute("followConfirmFrom") final FollowConfirmForm followConfirmFrom, final Model model, final RedirectAttributes redirectModel){
		DefaultResponse defaultResponse = followOpportunityFacade.customerConfirmAndSaveInfo(followConfirmFrom);
		if(defaultResponse.getSuccess()){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "follow.confirm.success", null);
		}else{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "follow.confirm.fail", null);
		}
		return REDIRECT_TO_HOME_PAGE+"?followCode="+followConfirmFrom.getFollowCode();
	}
}
