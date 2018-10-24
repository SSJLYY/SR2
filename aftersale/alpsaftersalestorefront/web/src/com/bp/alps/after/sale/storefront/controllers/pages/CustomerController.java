package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.alps.facades.data.DefaultPageableRequest;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.RegionResponse;
import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.facades.data.customer.CustomerSearchResponse;
import com.bp.alps.facades.data.vehicle.SearchVehicleResponse;
import com.bp.alps.vehiclecommercefacade.customer.AlpsCustomerFacade;
import com.bp.alps.vehiclecommercefacade.region.RegionFacade;
import com.bp.alps.vehiclecommercefacade.vehicle.VehicleFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bp.alps.facades.data.order.CustomerInfoData;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractPageController
{
	private static final String CUSTOMER_FORM = "customerForm";
	private static final String CUSTOMER_LIST = "customerList";
	private static final String CUSTOMER_DETAIL = "customerDetail";
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final String REDIRECT_CUSTOMER_LIST_URL = REDIRECT_PREFIX + "/customer";
	private static final String REDIRECT_CUSTOMER_Detail_URL = REDIRECT_PREFIX + "/customer/detail?code=";

	@Resource
	private AlpsCustomerFacade alpsCustomerFacade;

	@Resource
	private VehicleFacade vehicleFacade;

	@Resource
	private RegionFacade regionFacade;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public CustomerSearchResponse searchCustomer(@RequestBody CustomerSearchRequest customerSearchRequest, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		CustomerSearchResponse customerSearchResponse = alpsCustomerFacade.searchCustomer(customerSearchRequest);
		return customerSearchResponse;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(CustomerSearchRequest customerSearchRequest, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(CUSTOMER_LIST));
		CustomerSearchResponse customerSearchResponse = alpsCustomerFacade.searchCustomer(customerSearchRequest);
		populatorCustomerTypeOption(model);
		model.addAttribute("customerList", customerSearchResponse.getCustomerList());
		model.addAttribute(new CustomerSearchRequest());
		return "pages/customer/list";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String customerForm(final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(CUSTOMER_FORM));
		model.addAttribute(new CustomerInfoData());
		populatorCustomerTypeOption(model);
		populatorRegionInfoOption(model);
		return "pages/customer/form";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(@RequestParam(value = "code") final String code, final HttpServletRequest request, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(CUSTOMER_DETAIL));
		CustomerInfoData customerInfoData = alpsCustomerFacade.getCustomerByUid(code);
		if(customerInfoData==null){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "customer.not.found", null);
			return REDIRECT_CUSTOMER_LIST_URL;
		}
		model.addAttribute("customerInfoData", customerInfoData);
		model.addAttribute("customerCode", code);
		populatorCustomerTypeOption(model);
		populatorRegionInfoOption(model);
		return "pages/customer/detail";
	}

	@RequestMapping(value = "/related-vehicle/detail", method = RequestMethod.GET)
	public String relatedVehicleDetail(@RequestParam(value = "code") final String code, DefaultPageableRequest defaultPageableRequest, final HttpServletRequest request, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(CUSTOMER_DETAIL));
		SearchVehicleResponse searchVehicleResponse = vehicleFacade.getRelatedVehiclesByCustomerUid(code, defaultPageableRequest);
		model.addAttribute("vehicleListResponse", searchVehicleResponse);
		model.addAttribute("customerCode", code);
		return "pages/customer/relatedVehicle";
	}

	protected void populatorCustomerTypeOption(final Model model){
		model.addAttribute("customerRole", alpsCustomerFacade.getRole());
		model.addAttribute("buyerType", alpsCustomerFacade.getBuyerType());
		model.addAttribute("buyerCategory", alpsCustomerFacade.getBuyerCategory());
	}

	private void populatorRegionInfoOption(final Model model){
		RegionResponse regionResponse = regionFacade.getProviceList();
		try
		{
			model.addAttribute("regionList", JSON_MAPPER.writeValueAsString(regionResponse.getProvinceDataList()));
		}catch (Exception JsonProcessingException){
			model.addAttribute("regionList", "");
		}
	}

	@RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
	public String create(final HttpServletRequest request, final CustomerInfoData customerInfoData, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = alpsCustomerFacade.createOrUpdateRequest(customerInfoData);
		if(customerInfoData.getUid()!=null)
		{
			if(defaultResponse.getSuccess()){
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "customer.update.success", null);
				return REDIRECT_CUSTOMER_Detail_URL+customerInfoData.getUid();
			}
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "customer.update.fail", null);
			return REDIRECT_CUSTOMER_Detail_URL+customerInfoData.getUid();
		}
		if(defaultResponse.getSuccess()){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "customer.creation.success", null);
			return REDIRECT_CUSTOMER_LIST_URL;
		}
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "customer.creation.fail", null);
		return REDIRECT_CUSTOMER_LIST_URL;
	}
}
