package com.bp.alps.after.sale.storefront.controllers.cms;

import com.bp.alps.after.sale.storefront.controllers.ControllerConstants;
import de.hybris.platform.acceleratorcms.constants.GeneratedAcceleratorCmsConstants;
import de.hybris.platform.acceleratorcms.model.components.BreadcrumbComponentModel;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.cms2.model.contents.components.CMSLinkComponentModel;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller("BreadcrumbComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms._Prefix+BreadcrumbComponentModel._TYPECODE+ControllerConstants.Actions.Cms._Suffix)
public class BreadcrumbComponentController extends AbstractAcceleratorCMSComponentController<BreadcrumbComponentModel>
{
	public static final String CMS_PAGE_MODEL = "page";

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final BreadcrumbComponentModel component)
	{
		final AbstractPageModel page = (AbstractPageModel) request.getAttribute(CMS_PAGE_MODEL);
		List<Breadcrumb> breadcrumbs = new ArrayList<>();
		if(page instanceof ContentPageModel)
		{
			ContentPageModel contentPage = (ContentPageModel)page;
			List<CMSNavigationNodeModel> navigationNodes = contentPage.getNavigationNodes();
			navigationNodes.stream().forEach(nodeModel -> {
				List<CMSLinkComponentModel> linkComponents = nodeModel.getLinks();
				if (CollectionUtils.isNotEmpty(linkComponents))
				{
					final Breadcrumb breadcrumbEntry = new Breadcrumb(linkComponents.get(0).getUrl(), linkComponents.get(0).getLinkName(), null);
					breadcrumbs.add(breadcrumbEntry);
				}
			});
		}
		final Breadcrumb loginBreadcrumbEntry = new Breadcrumb("#", page.getTitle(), null);
		breadcrumbs.add(loginBreadcrumbEntry);
		model.addAttribute("breadcrumbs", breadcrumbs);
	}
}
