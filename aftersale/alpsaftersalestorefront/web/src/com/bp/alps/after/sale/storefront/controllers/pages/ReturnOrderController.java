package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.SearchIncrementOrderRequest;
import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.after.sale.facades.incrementOrder.IncrementOrder2UserFacade;
import com.bp.after.sale.facades.incrementOrder.IncrementOrderFacade;
import com.bp.after.sale.facades.returnorder.ReturnOrderFacade;
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
@RequestMapping("/return-order")
public class ReturnOrderController extends AbstractPageController
{
	private static final String RETURN_REQUEST_Detail = "returnOrderDetail";
	private static final String RETURN_REQUEST_LIST = "returnOrderList";
	private static final String RETURN_REQUEST_PRINT = "returnOrderPrint";
	private static final String REDIRECT_RETURN_REQUEST_DETAIL_URL = REDIRECT_PREFIX + "/return-order/detail?code=";
	private static final String REDIRECT_RETURN_REQUEST_LIST_URL = REDIRECT_PREFIX + "/return-order/list";
	private static final String REDIRECT_PRINT_URL = "pages/returnorder/print";

	@Resource
	private ReturnOrderFacade returnOrderFacade;

	@Resource
	private IncrementOrder2UserFacade incrementOrder2UserFacade;

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(final String code, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(RETURN_REQUEST_Detail));
		IncrementOrderData incrementOrderData = returnOrderFacade.getReturnOrderByCode(code);
		List<OptionData> orderStatus = returnOrderFacade.getOrderStatus();
		model.addAttribute(incrementOrderData);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute(new CustomerSearchRequest());
		model.addAttribute(new ProductSearchRequest());
		model.addAttribute(new SearchVehicleRequest());
		return "pages/returnorder/detail";
	}

	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(final HttpServletRequest request, final SearchIncrementOrderRequest searchIncrementOrderRequest, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(RETURN_REQUEST_LIST));
		IncrementOrderListResponse incrementOrderListResponse = returnOrderFacade.getReturnOrderByCurrentConsultant(searchIncrementOrderRequest);
		List<OptionData> orderStatus = returnOrderFacade.getOrderStatus();
		model.addAttribute(searchIncrementOrderRequest);
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("listResponse",incrementOrderListResponse);
		return "pages/returnorder/list";
	}

	@RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
	public String create(final HttpServletRequest request, final IncrementOrderData incrementOrderData, final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		CreateIncrementOrderResponse createIncrementOrderResponse;
		if(incrementOrderData.getReturnOrderCode()!=null){
			createIncrementOrderResponse = returnOrderFacade.updateOrder(incrementOrderData);
		}else{
			createIncrementOrderResponse = returnOrderFacade.createReturnOrder(incrementOrderData);
		}
		if(createIncrementOrderResponse.getCode()!=null)
		{
			if(createIncrementOrderResponse.getSuccess()){
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "return.request.update.success", null);
				return REDIRECT_RETURN_REQUEST_DETAIL_URL+createIncrementOrderResponse.getCode();
			}
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "return.request.update.fail", null);
			return REDIRECT_RETURN_REQUEST_DETAIL_URL+createIncrementOrderResponse.getCode();
		}
		if(createIncrementOrderResponse.getSuccess()){
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "return.request.creation.success", null);
			return REDIRECT_RETURN_REQUEST_DETAIL_URL+createIncrementOrderResponse.getCode();
		}
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "return.request.creation.fail", null);
		if(StringUtils.isNotBlank(createIncrementOrderResponse.getCode())){
			return REDIRECT_RETURN_REQUEST_DETAIL_URL+createIncrementOrderResponse.getCode();
		}
		return REDIRECT_RETURN_REQUEST_LIST_URL;
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String create(final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(RETURN_REQUEST_PRINT));
		return REDIRECT_PRINT_URL;
	}
}
