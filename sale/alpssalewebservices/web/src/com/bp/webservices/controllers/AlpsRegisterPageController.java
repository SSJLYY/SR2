package com.bp.webservices.controllers;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.user.RegisterFormRequest;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/register")
public  class AlpsRegisterPageController
{
    private static final Logger LOGGER = Logger.getLogger(AlpsRegisterPageController.class);

    public static final String REDIRECT_PREFIX = "redirect:";
    public static final String FORWARD_PREFIX = "forward:";
    public static final String ROOT = "/";

    public static final String PAGE_ROOT = "pages/";
    public static final String CMS_PAGE_MODEL = "cmsPage";
    public static final String CMS_PAGE_TITLE = "pageTitle";
    public static final String REDIRECT_TO_LOGIN_FOR_CHECKOUT = REDIRECT_PREFIX + "/login/checkout";
    public static final String REDIRECT_TO_MULTISTEP_CHECKOUT = REDIRECT_PREFIX + "/checkout/multi";

    @Resource(name = "customerFacade")
    private CustomerFacade customerFacade;

    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    @Resource(name = "cmsPageService")
    private CMSPageService cmsPageService;

    public CMSPageService getCmsPageService() {
        return cmsPageService;
    }

    @Resource(name = "cmsSiteService")
    private CMSSiteService cmsSiteService;

    public CMSSiteService getCmsSiteService() {
        return cmsSiteService;
    }


    @RequestMapping(value = "/newcustomer", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public DefaultResponse doAlpsRegister(@RequestBody(required = false) RegisterFormRequest registerFormRequest)
            throws CMSItemNotFoundException
    {

        DefaultResponse res =  new DefaultResponse();
        final RegisterData data = new RegisterData();
        data.setFirstName(registerFormRequest.getFirstName());
        data.setLastName(registerFormRequest.getLastName());
        data.setLogin(registerFormRequest.getEmail());
        data.setPassword(registerFormRequest.getPwd());
        data.setTitleCode(registerFormRequest.getTitleCode());
        data.setPathId("buyergroup");

        try {
            getCustomerFacade().register(data);
            res.setSuccess(true);
        } catch (DuplicateUidException e) {
            res.setMessage(e.getMessage());
            res.setSuccess(false);
        }

        return res;

    }

}
