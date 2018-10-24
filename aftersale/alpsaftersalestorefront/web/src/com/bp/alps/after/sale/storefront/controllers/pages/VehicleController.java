package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.product.ProductSearchResponse;
import com.bp.alps.facades.data.vehicle.*;
import com.bp.alps.vehiclecommercefacade.product.search.SalesProductSearchFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bp.alps.vehiclecommercefacade.vehicle.VehicleFacade;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@Controller
@RequestMapping("/vehicle")
public class VehicleController extends AbstractPageController
{
	private static final String REDIRECT_VEHICLE_LIST_URL = REDIRECT_PREFIX + "/vehicle";
	private static final String REDIRECT_VEHICLE_Detail_URL = REDIRECT_PREFIX + "/vehicle/detail?code=";

	@Resource
	private VehicleFacade vehicleFacade;

	@Resource
	private SalesProductSearchFacade salesProductSearchFacade;

	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	private static final String VEHICLE_FORM = "vehicleForm";
	private static final String VEHICLE_LIST = "vehicleList";
	private static final String VEHICLE_DETAIL = "vehicleDetail";

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String serviceForm(final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(VEHICLE_FORM));
		populatorVehicleOption(model);
		populatorVehicleProductOption(model);
		return "pages/vehicle/form";
	}

	protected void populatorVehicleOption(final Model model){
		model.addAttribute(new VehicleInfoData());
		model.addAttribute(new CustomerSearchRequest());
		model.addAttribute("vehicleType",vehicleFacade.getVehicleType());
		model.addAttribute("vehicleStatus",vehicleFacade.getStatus());
	}

	protected void populatorVehicleProductOption(final Model model){
		ProductSearchRequest productSearchRequest = new ProductSearchRequest();
		ProductSearchResponse productSearchResponse = salesProductSearchFacade.getProductSearchResult(productSearchRequest);
		model.addAttribute("brandList",productSearchResponse.getCategoryList());
		try
		{
			model.addAttribute("categoryList", JSON_MAPPER.writeValueAsString(productSearchResponse.getCategoryList()));
			model.addAttribute("productList", JSON_MAPPER.writeValueAsString(productSearchResponse.getProductList()));
		}catch (Exception JsonProcessingException){
			model.addAttribute("categoryList", "");
			model.addAttribute("productList", "");
		}
	}

	@RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
	public String create(final HttpServletRequest request, final VehicleInfoData vehicleInfoData, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = vehicleFacade.createOrUpdateRequest(vehicleInfoData);
		if(vehicleInfoData.getCode()!=null)
		{
			if(defaultResponse.getSuccess()){
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "vehicle.update.success", null);
				return REDIRECT_VEHICLE_Detail_URL+vehicleInfoData.getCode();
			}
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "vehicle.update.success", null);
			return REDIRECT_VEHICLE_Detail_URL+vehicleInfoData.getCode();
		}
		if(defaultResponse.getSuccess()){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "vehicle.creation.success", null);
			return REDIRECT_VEHICLE_LIST_URL;
		}
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "vehicle.creation.success", null);
		return REDIRECT_VEHICLE_LIST_URL;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String getDetail(@RequestParam(required = true) final String code, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException{
		storeCmsPageInModel(model, getContentPageForLabelOrId(VEHICLE_DETAIL));
		populatorVehicleInfo(model, code);
		populatorVehicleOption(model);
		populatorVehicleProductOption(model);
		populatorVehicle2UserOption(model);
		populatorInsuranceOption(model);
		return "pages/vehicle/detail";
	}

	protected void populatorVehicleInfo(final Model model, final String vehiclecode){
		VehicleInfoData vehicleInfoData = vehicleFacade.getVehicleDetail(vehiclecode);
		model.addAttribute("vehicleInfo",vehicleInfoData);
	}

	protected void populatorVehicle2UserOption(final Model model){
		model.addAttribute(new Vehicle2User());
		model.addAttribute("vehicle2userType",vehicleFacade.getVehicle2userType());
	}

	protected void populatorInsuranceOption(final Model model){
		model.addAttribute(new InsuranceData());
	}

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String getList(final SearchVehicleRequest searchVehicleRequest, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(VEHICLE_LIST));
		SearchVehicleResponse searchVehicleResponse = vehicleFacade.searchVehicle(searchVehicleRequest);
		model.addAttribute("vehicleList", searchVehicleResponse.getVehicleList());
		return "pages/vehicle/list";
	}

	@RequestMapping(value = "/listData", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public SearchVehicleResponse getListReponse(final SearchVehicleRequest searchVehicleRequest, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(VEHICLE_LIST));
		SearchVehicleResponse searchVehicleResponse = vehicleFacade.searchVehicle(searchVehicleRequest);
		return searchVehicleResponse;
	}
}
