package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.after.sale.facades.data.PickupInStoreData;
import com.bp.after.sale.facades.data.PickupInStoreDetailResponse;
import com.bp.after.sale.facades.data.PickupInStoreListRequest;
import com.bp.after.sale.facades.data.ServiceConsultantListResponse;
import com.bp.after.sale.facades.data.aftersales.PickupInStoreListData;
import com.bp.after.sale.facades.pickupinstore.PickupInStoreFacade;
import com.bp.after.sale.facades.serviceorder.ServiceOrderFacade;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.servicelayer.i18n.I18NService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pickup")
public class PickupInShopController  extends AbstractPageController
{

    @Resource
    private PickupInStoreFacade pickupInStoreFacade;

    @Resource
    private ServiceOrderFacade serviceOrderFacade;

    @Resource
    private MessageSource messageSource;

    @Resource
    private I18NService i18nService;


    private static final String CREATE_FORM = "createForm";
    private static final String DETAIL_FORM = "detailForm";
    private static final String SHOW_FORM = "showForm";
    private static final String BOOK_TODAY_FORM = "bookTodayForm";


    /*
     *创建 get
     */
    @RequestMapping(value = "/recordCreate", method = RequestMethod.GET)
    public String createForm(final HttpServletRequest request, final Model model, final Integer currentPage, final Integer pagesize) throws CMSItemNotFoundException
    {
        ServiceConsultantListResponse serviceConsultantListResponse = pickupInStoreFacade
              .getServiceConsultant(currentPage,pagesize);
        model.addAttribute("service_consultant", serviceConsultantListResponse.getServiceList());

        List<OptionData> pickupInStorePurpose = pickupInStoreFacade.getAllPickupInStorePurpose();
        model.addAttribute("pickupinstore_purpose", pickupInStorePurpose);

        storeCmsPageInModel(model, getContentPageForLabelOrId(CREATE_FORM));
        return getViewForPage(model);
    }



    /*
     *创建 表单 post
     */
    @RequestMapping(value = "/recordCreated", method = RequestMethod.POST)
    public String createForm(final HttpServletRequest request, final Model model,PickupInStoreData pickupInStoreData) throws CMSItemNotFoundException
    {
        DefaultResponse response = pickupInStoreFacade.createPickupInStore(pickupInStoreData);
        storeCmsPageInModel(model, getContentPageForLabelOrId(CREATE_FORM));
        return "redirect:/pickup/list";
    }

    /*
     *详情
     */
    @RequestMapping(value = "/recordDetail", method = { RequestMethod.POST,RequestMethod.GET})
    public String detailForm(final HttpServletRequest request, final Model model,String code) throws CMSItemNotFoundException
    {
        List<PickupInStoreData> pickupInStoreList = pickupInStoreFacade.getPickupInStoreByCode(code).getPickupInStoreList();
        List<OptionData> pickupInStoreTaskStatus = pickupInStoreFacade.getAllPickupStatus();
        model.addAttribute("pickupinstoretask_status", pickupInStoreTaskStatus);
        model.addAttribute("recordList",pickupInStoreList);
        storeCmsPageInModel(model, getContentPageForLabelOrId(DETAIL_FORM));
        return getViewForPage(model);
    }



    /*
     *更新
     */
    @RequestMapping(value = "/recordUpdate", method = { RequestMethod.POST,RequestMethod.GET})
    public String updateForm(final HttpServletRequest request, final Model model,PickupInStoreData pickupInStoreData) throws CMSItemNotFoundException
    {
        pickupInStoreFacade.savePickupInStore(pickupInStoreData);
        return "redirect:/pickup/list";
    }


    /**
     * 今日预约看板 post
     * @param request
     * @param model
     * @return
     * @throws CMSItemNotFoundException
     */
    @RequestMapping(value = "/bookToday", method = { RequestMethod.POST})
    public String bookToday(final HttpServletRequest request, final Model model , PickupInStoreListRequest pickupInStoreListRequest) throws CMSItemNotFoundException
    {

        List<PickupInStoreListData> pickupInStoreList = pickupInStoreFacade.getPickupInStoreTodayList(pickupInStoreListRequest).getPickupInStoreList();

        model.addAttribute("pickupList",pickupInStoreList);
        model.addAttribute("now", DateFormatUtils.getDateString("date",new Date()));

        storeCmsPageInModel(model, getContentPageForLabelOrId(BOOK_TODAY_FORM));
        return getViewForPage(model);
    }


    /**
     * 接车列表
     * @param request
     * @param model
     * @return
     * @throws CMSItemNotFoundException
     */
    @RequestMapping(value = "/list", method = { RequestMethod.GET,RequestMethod.POST})
    public String showFormList(final HttpServletRequest request, final Model model , PickupInStoreListRequest pickupInStoreListRequest) throws CMSItemNotFoundException
    {
        List<PickupInStoreListData> pickupInStoreList = pickupInStoreFacade.getPickupInStoreList(pickupInStoreListRequest).getPickupInStoreList();
        model.addAttribute("pickupList",pickupInStoreList);

        List<OptionData> pickupInStoreTaskStatus = pickupInStoreFacade.getAllPickupStatus();
        List<OptionData> orderStatus = serviceOrderFacade.getServiceOrderStatus();
        model.addAttribute("pickupinstoretask_status", pickupInStoreTaskStatus);
        model.addAttribute("order_status", orderStatus);
        model.addAttribute("pickupInStoreListRequest",pickupInStoreListRequest);

        storeCmsPageInModel(model, getContentPageForLabelOrId(SHOW_FORM));
        return getViewForPage(model);
    }



    /**
     * 车牌号查询
     * @param vehicleNumber
     * @return
     */
    @RequestMapping(value = "/record", method = { RequestMethod.POST })
    @ResponseBody
    public PickupInStoreDetailResponse getPickupInStore(@RequestParam(value = "vehicleNumber", required = false) String vehicleNumber)
    {
        PickupInStoreDetailResponse pickupInStoreDetailResponse;

        pickupInStoreDetailResponse = pickupInStoreFacade.getPickupInStoreByVehicle(vehicleNumber);

        if(StringUtils.isNotBlank(pickupInStoreDetailResponse.getMessageCode())){
            String message = messageSource.getMessage(pickupInStoreDetailResponse.getMessageCode(), null,i18nService.getCurrentLocale());
            pickupInStoreDetailResponse.setMessage(message);
        }

        return pickupInStoreDetailResponse;
    }

}
