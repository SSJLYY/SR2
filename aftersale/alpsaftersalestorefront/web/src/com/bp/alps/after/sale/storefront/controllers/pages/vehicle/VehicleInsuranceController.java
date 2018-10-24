package com.bp.alps.after.sale.storefront.controllers.pages.vehicle;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.vehicle.InsuranceData;
import com.bp.alps.facades.data.vehicle.Vehicle2User;
import com.bp.alps.vehiclecommercefacade.vehicle.Vehicle2UserFacade;
import com.bp.alps.vehiclecommercefacade.vehicle.VehicleInsuranceFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@Controller
@RequestMapping("/vehicle/insurance")
public class VehicleInsuranceController extends AbstractPageController
{
	@Resource
	private VehicleInsuranceFacade vehicleInsuranceFacade;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResponse create(final HttpServletRequest request, @RequestBody(required = false) final InsuranceData insuranceData, final Model model, final RedirectAttributes redirectModel) throws
			CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = vehicleInsuranceFacade.create(insuranceData);
		return defaultResponse;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public DefaultResponse delete(@RequestParam("pk[]") final ArrayList<String> pks,final HttpServletRequest request, final Model model) throws
			CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = vehicleInsuranceFacade.delete(pks);
		return defaultResponse;
	}
}
