package com.dp.webservices.controllers;


import com.dp.alps.facades.data.DefaultResponse;
import com.dp.alps.facades.data.user.PasswordForgetRequest;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/forgetpwd")
public class AlpsPasswordForgetPageController {

    @RequestMapping(value = "/forget", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String forgetPassword(@RequestBody(required = false) PasswordForgetRequest passwordForgetRequest)throws CMSItemNotFoundException
    {
        String verificationCode = null;
        String username = passwordForgetRequest.getUsername();


        if(true){
           /* Integer hashcode;
            Random random=new Random();
            //为变量赋随机值1000-9999
            hashcode=random.nextInt(9999-1000+1)+1000;
            String verificationCode = hashcode.toString();*/

             verificationCode = "1234";

        }



        return verificationCode;
    }

    @RequestMapping(value = "/setPwd", method = { RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public DefaultResponse setPassword(@RequestBody(required = false) PasswordForgetRequest passwordForgetRequest)throws CMSItemNotFoundException
    {
        DefaultResponse defaultResponse = new DefaultResponse();







        return defaultResponse;
    }

}
