package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.SearchIncrementOrderRequest;
import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.after.sale.facades.incrementOrder.IncrementOrder2UserFacade;
import com.bp.after.sale.facades.incrementOrder.IncrementOrderFacade;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.facades.data.order.CreateIncrementOrderResponse;
import com.bp.alps.facades.data.order.IncrementOrderListResponse;
import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.vehicle.SearchVehicleRequest;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cmsfacades.data.OptionData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/increment-order")
public class IncrementOrderController extends AbstractPageController
{
	private static final String INCREMENT_ORDER_FORM = "incrementOrderForm";
	private static final String INCREMENT_ORDER_Detail = "incrementOrderDetail";
	private static final String INCREMENT_ORDER_LIST = "incrementOrderList";
	private static final String REDIRECT_INCREMENT_ORDER_DETAIL_URL = REDIRECT_PREFIX + "/increment-order/detail?code=";
	private static final String REDIRECT_INCREMENT_ORDER_LIST_URL = REDIRECT_PREFIX + "/increment-order/list";

	@Resource
	private IncrementOrderFacade incrementOrderFacade;

	@Resource
	private IncrementOrder2UserFacade incrementOrder2UserFacade;

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String serviceForm(final String pickupInStoreCode, final HttpServletRequest request, final Model model) throws
			CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(INCREMENT_ORDER_FORM));
		model.addAttribute(new IncrementOrderData());
		model.addAttribute(new CustomerSearchRequest());
		model.addAttribute(new ProductSearchRequest());
		model.addAttribute(new SearchVehicleRequest());
		return "pages/incrementorder/form";
	}

	@RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
	public String create(final HttpServletRequest request, final IncrementOrderData incrementOrderData, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		CreateIncrementOrderResponse createIncrementOrderResponse;
		if(incrementOrderData.getCode()!=null){
			createIncrementOrderResponse = incrementOrderFacade.updateIncrementOrder(incrementOrderData);
		}else{
			createIncrementOrderResponse = incrementOrderFacade.createIncrementOrder(incrementOrderData);
		}
		if(createIncrementOrderResponse.getCode()!=null)
		{
			if(createIncrementOrderResponse.getSuccess()){
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "incrementorder.update.success", null);
				return REDIRECT_INCREMENT_ORDER_DETAIL_URL+createIncrementOrderResponse.getCode();
			}
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "incrementorder.update.fail", null);
			return REDIRECT_INCREMENT_ORDER_DETAIL_URL+createIncrementOrderResponse.getCode();
		}
		if(createIncrementOrderResponse.getSuccess()){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "incrementorder.creation.success", null);
			return REDIRECT_INCREMENT_ORDER_DETAIL_URL+createIncrementOrderResponse.getCode();
		}
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "incrementorder.creation.fail", null);
		if(StringUtils.isNotBlank(createIncrementOrderResponse.getCode())){
			return REDIRECT_INCREMENT_ORDER_DETAIL_URL+createIncrementOrderResponse.getCode();
		}
		return REDIRECT_INCREMENT_ORDER_LIST_URL;
	}

	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(final HttpServletRequest request, final SearchIncrementOrderRequest searchIncrementOrderRequest, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(INCREMENT_ORDER_LIST));
		IncrementOrderListResponse incrementOrderListResponse = incrementOrderFacade.getIncrementOrderByCurrentConsultant(searchIncrementOrderRequest);
		List<OptionData> orderStatus = incrementOrderFacade.getServiceOrderStatus();
		model.addAttribute(searchIncrementOrderRequest);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("listResponse",incrementOrderListResponse);
		return "pages/incrementorder/list";
	}

	@RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public String detail(final String code, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(INCREMENT_ORDER_Detail));
		IncrementOrderData incrementOrderData = incrementOrderFacade.getIncrementOrderByCode(code);
		List<OptionData> orderStatus = incrementOrderFacade.getServiceOrderStatus();
		model.addAttribute("service2RoleTypeList", incrementOrder2UserFacade.getService2RoleType());
		model.addAttribute(incrementOrderData);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute(new RelatedRoleData());
		model.addAttribute(new CustomerSearchRequest());
		model.addAttribute(new ProductSearchRequest());
		model.addAttribute(new SearchVehicleRequest());
		return "pages/incrementorder/detail";
	}

	@RequestMapping(value = "/user/create", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public DefaultResponse createorder2user(final HttpServletRequest request, final RelatedRoleData relatedRoleData, final Model model) throws CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = incrementOrder2UserFacade.create(relatedRoleData);
		return defaultResponse;
	}

	@RequestMapping(value = "/user/delete", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public DefaultResponse deleteorder2user(@RequestParam("pk[]") final ArrayList<String> pks, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		DefaultResponse defaultResponse = incrementOrder2UserFacade.delete(pks);
		return defaultResponse;
	}
}
