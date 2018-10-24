package com.bp.alps.storefront.controllers.cms;

import com.bp.alps.core.model.components.CustomerStreamFormComponentModel;
import com.bp.alps.facades.customersteam.CustomerStreamFacade;
import com.bp.alps.facades.data.SalesStaffListResponse;
import com.bp.alps.storefront.controllers.ControllerConstants;
import de.hybris.platform.cmsfacades.data.OptionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bp.alps.facades.employee.SalesStaffFacade;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller("CustomerStreamFormComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.CustomerStreamFormComponentController)
public class CustomerStreamFormComponentController extends AbstractAcceleratorCMSComponentController<CustomerStreamFormComponentModel>
{
	@Resource
	private SalesStaffFacade salesStaffFacade;

	@Resource
	private CustomerStreamFacade customerStreamFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final CustomerStreamFormComponentModel component)
	{
		List<OptionData> customerStreamStatuses = customerStreamFacade.getAllStatus();
		List<OptionData> customerStreamTypes = customerStreamFacade.getTypes();
		List<OptionData> customerStreamGenders = customerStreamFacade.getGenders();
		SalesStaffListResponse salesStaffListResponse = salesStaffFacade.getSalesConsultant(0,1000);
		model.addAttribute("types", customerStreamTypes);
		model.addAttribute("status_list", customerStreamStatuses);
		model.addAttribute("genders", customerStreamGenders);
		model.addAttribute("sales_consultant", salesStaffListResponse.getSalesList());

	}
}
