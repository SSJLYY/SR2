package com.bp.webservices.controllers;

import com.bp.alps.facades.customeraccount.AlpsCustomerAccountFacade;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.user.PasswordResetRequest;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping(value = "/resetpwd")
public class AlpsPasswordResetPageController
{
    @Resource
    private AlpsCustomerAccountFacade alpsCustomerAccountFacade;

    @Resource
    private MessageSource messageSource;

    @Resource
    private I18NService i18nService;

    /**
     * 修改密码
     * @param passwordResetRequest
     * @return
     * @throws CMSItemNotFoundException
     */
    @RequestMapping(value = "/change", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public DefaultResponse changePassword(@RequestBody(required = false) PasswordResetRequest passwordResetRequest)
    {
        DefaultResponse defaultResponse;

        defaultResponse = alpsCustomerAccountFacade.getResetPasswordResult(passwordResetRequest);
        if(StringUtils.isNotBlank(defaultResponse.getMessageCode())){
            String message = messageSource.getMessage(defaultResponse.getMessageCode(), null,i18nService.getCurrentLocale());
            defaultResponse.setMessage(message);
        }

        return defaultResponse;
    }







}
