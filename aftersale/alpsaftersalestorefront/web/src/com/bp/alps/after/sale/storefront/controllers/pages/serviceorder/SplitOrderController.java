package com.bp.alps.after.sale.storefront.controllers.pages.serviceorder;

import com.bp.after.sale.facades.data.ServiceOrderData;
import com.bp.after.sale.facades.data.SplitServiceOrderRequest;
import com.bp.after.sale.facades.serviceorder.ServiceOrder2UserFacade;
import com.bp.after.sale.facades.serviceorder.ServiceOrderFacade;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.facades.order.SubServiceOrderData;
import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.vehiclecommercefacade.product.search.SalesProductSearchFacade;
import com.bp.alps.vehiclecommercefacade.region.RegionFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cmsfacades.data.OptionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/service-order/splitorder")
public class SplitOrderController extends AbstractPageController
{
	private static final String REDIRECT_SPLIT_ORDER_DETAIL_URL = REDIRECT_PREFIX + "/service-order/splitorder/detail?code=";
	private static final String SERVICE_ORDER_DETAIL = "serviceOrderDetail";

	@Resource
	private ServiceOrderFacade serviceOrderFacade;

	@Resource
	private ServiceOrder2UserFacade serviceOrder2UserFacade;

	@Resource
	private SalesProductSearchFacade salesProductSearchFacade;

	@Resource
	private RegionFacade regionFacade;

	@RequestMapping(value = "/order", method = {RequestMethod.GET, RequestMethod.POST})
	public String splitOrder(final String code, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_ORDER_DETAIL));
		fillOrderInfo(model, code, true);
		List<OptionData> serviceSubType = serviceOrderFacade.getServiceSubType();
		model.addAttribute("serviceSubType", serviceSubType);
		model.addAttribute(new CustomerSearchRequest());
		return "pages/serviceorder/splitOrder";
	}

	@RequestMapping(value = "/process", method = {RequestMethod.GET, RequestMethod.POST})
	public String splitOrderProcess(final String code, final SplitServiceOrderRequest splitServiceOrderRequest, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_ORDER_DETAIL));
		List<AlpsCommerceResult> alpsCommerceResultList = serviceOrderFacade.splitOrder(splitServiceOrderRequest, code);
		return REDIRECT_SPLIT_ORDER_DETAIL_URL+code;
	}

	@RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public String splitOrderProcess(final String code, final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_ORDER_DETAIL));
		fillOrderInfo(model, code, true);
		List<OptionData> serviceSubType = serviceOrderFacade.getServiceSubType();
		model.addAttribute("serviceSubType", serviceSubType);
		model.addAttribute(new CustomerSearchRequest());
		return "pages/serviceorder/splitOrderDetail";
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
