package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.after.sale.facades.data.ServiceOrderData;
import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.after.sale.facades.serviceorder.ServiceOrder2UserFacade;
import com.bp.after.sale.facades.serviceorder.ServiceOrderFacade;
import com.bp.alps.after.sale.core.constants.AlpsaftersalecoreConstants;
import com.bp.alps.core.facades.order.SubServiceOrderData;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.facades.data.order.CreateServiceOrderResponse;
import com.bp.alps.facades.data.order.SearchServiceOrderRequest;
import com.bp.alps.facades.data.order.ServiceOrderListResponse;
import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.vehicle.SearchVehicleRequest;
import com.bp.alps.vehiclecommercefacade.product.search.SalesProductSearchFacade;
import com.bp.alps.vehiclecommercefacade.region.RegionFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/service-order")
public class ServiceOrderController extends AbstractPageController
{
	private static final String REDIRECT_SERVICE_ORDER_DETAIL_URL = REDIRECT_PREFIX + "/service-order/detail?code=";
	private static final String REDIRECT_SERVICE_ORDER_LIST_URL = REDIRECT_PREFIX + "/service-order/list";
	private static final String SERVICE_ORDER_FORM = "serviceOrderForm";
	private static final String SERVICE_ORDER_LIST = "serviceOrderList";
	private static final String SERVICE_ORDER_DETAIL = "serviceOrderDetail";

	@Resource
	private ServiceOrderFacade serviceOrderFacade;

	@Resource
	private SalesProductSearchFacade salesProductSearchFacade;

	@Resource
	private ServiceOrder2UserFacade serviceOrder2UserFacade;

	@Resource
	private RegionFacade regionFacade;

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String serviceForm(final String pickupInStoreCode, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_ORDER_FORM));
		model.addAttribute("pickupInStoreCode", pickupInStoreCode);
		model.addAttribute(new ServiceOrderData());
		model.addAttribute(new CustomerSearchRequest());
		model.addAttribute(new ProductSearchRequest());
		model.addAttribute(new SearchVehicleRequest());

		model.addAttribute("serviceOrderTypeList", serviceOrderFacade.getServiceOrderType());
		fillEntryPopupInfo(model);

		return "pages/serviceorder/form";
	}

	@RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public String detail(final String code, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_ORDER_DETAIL));
		fillOrderInfo(model, code, true);
		model.addAttribute("serviceOrderStatusList", serviceOrderFacade.getServiceOrderStatus());
		model.addAttribute("service2RoleTypeList", serviceOrder2UserFacade.getService2RoleType());
		model.addAttribute(new RelatedRoleData());
		model.addAttribute(new CustomerSearchRequest());
		model.addAttribute(new SearchVehicleRequest());
		fillEntryPopupInfo(model);
		return "pages/serviceorder/detail";
	}

	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(final HttpServletRequest request, final SearchServiceOrderRequest searchServiceOrderRequest, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_ORDER_LIST));
		ServiceOrderListResponse serviceOrderListResponse = serviceOrderFacade.getServiceOrderByCurrentServiceConsultant(searchServiceOrderRequest);
		model.addAttribute(searchServiceOrderRequest);
		model.addAttribute("serviceOrderStatusList", serviceOrderFacade.getServiceOrderStatus());
		model.addAttribute("serviceOrderTypeList", serviceOrderFacade.getServiceOrderType());
		model.addAttribute("listResponse",serviceOrderListResponse);
		return "pages/serviceorder/list";
	}

	@RequestMapping(value = "/listData", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ServiceOrderListResponse listData(final HttpServletRequest request, final SearchServiceOrderRequest searchServiceOrderRequest, final Model model) throws CMSItemNotFoundException
	{
		ServiceOrderListResponse serviceOrderListResponse = serviceOrderFacade.getServiceOrderByCurrentServiceConsultant(searchServiceOrderRequest);
		return serviceOrderListResponse;
	}

	@RequestMapping(value = "/user/create", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public DefaultResponse createorder2user(final HttpServletRequest request, final RelatedRoleData relatedRoleData, final Model model) throws CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = serviceOrder2UserFacade.create(relatedRoleData);
		return defaultResponse;
	}

	@RequestMapping(value = "/user/delete", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public DefaultResponse deleteorder2user(@RequestParam("pk[]") final ArrayList<String> pks, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = serviceOrder2UserFacade.delete(pks);
		return defaultResponse;
	}

	@RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
	public String create(final HttpServletRequest request, final ServiceOrderData serviceOrderData, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		CreateServiceOrderResponse createServiceOrderResponse;
		if(serviceOrderData.getCode()!=null){
			createServiceOrderResponse = serviceOrderFacade.updateServiceOrder(serviceOrderData);
		}else{
			createServiceOrderResponse = serviceOrderFacade.createServiceOrder(serviceOrderData);
		}
		if(createServiceOrderResponse.getCode()!=null)
		{
			if(createServiceOrderResponse.getSuccess()){
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "serviceorder.update.success", null);
				return REDIRECT_SERVICE_ORDER_DETAIL_URL+createServiceOrderResponse.getCode();
			}
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "serviceorder.update.fail", null);
			return REDIRECT_SERVICE_ORDER_DETAIL_URL+createServiceOrderResponse.getCode();
		}
		if(createServiceOrderResponse.getSuccess()){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "serviceorder.creation.success", null);
			return REDIRECT_SERVICE_ORDER_DETAIL_URL+createServiceOrderResponse.getCode();
		}
		if(StringUtils.isNotBlank(createServiceOrderResponse.getCode())){
			return REDIRECT_SERVICE_ORDER_DETAIL_URL+createServiceOrderResponse.getCode();
		}
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "serviceorder.creation.fail", null);
		return REDIRECT_SERVICE_ORDER_LIST_URL;
	}

	protected void fillEntryPopupInfo(final Model model){
		model.addAttribute("serviceOrderEntryTypeList", serviceOrderFacade.getServiceOrderEntryType());
		model.addAttribute("serviceOrderEntryCategoryList", salesProductSearchFacade.getSubCategoriesForCode(Config.getString(
				AlpsaftersalecoreConstants.Category.ROOT_CATEGORY_CODE_FOR_AFTER_SALE, "aftersale")));
	}

	protected void fillOrderInfo(final Model model, final String code, Boolean includeSubOrder){
		ServiceOrderData serviceOrderData = serviceOrderFacade.getServiceOrderByCode(code);
		model.addAttribute("serviceOrderData", serviceOrderData);
		if(includeSubOrder)
		{
			List<SubServiceOrderData> subOrderDataList = serviceOrderFacade.getSubOrderByParentCode(code);
			model.addAttribute("subOrderDataList", subOrderDataList);
		}
	}
}
