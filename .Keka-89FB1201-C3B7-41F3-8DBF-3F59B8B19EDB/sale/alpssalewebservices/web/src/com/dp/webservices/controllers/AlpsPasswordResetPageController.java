package com.dp.webservices.controllers;

import com.dp.alps.facades.customeraccount.AlpsCustomerAccountFacade;
import com.dp.alps.facades.data.DefaultResponse;
import com.dp.alps.facades.data.user.PasswordResetRequest;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
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


    /**
     * 修改密码
     * @param passwordResetRequest
     * @return
     * @throws CMSItemNotFoundException
     */
    @RequestMapping(value = "/change", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public DefaultResponse changePassword(@RequestBody(required = false) PasswordResetRequest passwordResetRequest) throws CMSItemNotFoundException
    {
        return alpsCustomerAccountFacade.getResetPasswordResult(passwordResetRequest);
    }







}
