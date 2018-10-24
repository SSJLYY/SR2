package com.bp.webservices.controllers;


import com.bp.alps.facades.customeraccount.AlpsCustomerAccountFacade;
import com.bp.alps.facades.data.PasswordForgetResponse;
import com.bp.alps.facades.data.VerificationCodeResponse;
import com.bp.alps.facades.data.user.PasswordForgetRequest;
import de.hybris.platform.servicelayer.i18n.I18NService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/forgetpwd")
public class AlpsPasswordForgetPageController
{

    @Resource
    private AlpsCustomerAccountFacade alpsCustomerAccountFacade;

    @Resource(name="alpsMessageSource")
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private I18NService i18nService;

    /**
     * 忘记密码请求:获取验证码
     * @param passwordForgetRequest
     * @return
     *
     */
    @RequestMapping(value = "/forget", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public VerificationCodeResponse forgetPassword(@RequestBody(required = false) PasswordForgetRequest passwordForgetRequest){
        VerificationCodeResponse verificationCodeResponse;
        verificationCodeResponse = alpsCustomerAccountFacade.getVerificationCode(passwordForgetRequest);

        if (StringUtils.isNotBlank(verificationCodeResponse.getMessageCode())){
            String message = messageSource.getMessage(verificationCodeResponse.getMessageCode(), null,i18nService.getCurrentLocale());
            verificationCodeResponse.setMessage(message);
        }

        return verificationCodeResponse;
    }






    /**
     * 忘记密码:检验验证码后重置token
     * @param passwordForgetRequest
     * @return
     */
    @RequestMapping(value = "/setPwd", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public PasswordForgetResponse setPassword(@RequestBody(required = false) PasswordForgetRequest passwordForgetRequest)
    {
        PasswordForgetResponse passwordForgetResponse;
        passwordForgetResponse = alpsCustomerAccountFacade.getToken(passwordForgetRequest);

        if (StringUtils.isNotBlank(passwordForgetResponse.getMessageCode())){
            String message =messageSource.getMessage(passwordForgetResponse.getMessageCode(), null,i18nService.getCurrentLocale());
            passwordForgetResponse.setMessage(message);
        }

        return passwordForgetResponse;
    }


    /**
     *清空properties缓存
     * @return
     */
    @RequestMapping(value = "/clearCache", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String  clearPropertiesCache(){
        try{

            messageSource.clearCache();
            return "Cache is cleared";
        }catch(Exception e){

            return e.getMessage();
        }

    }





}
