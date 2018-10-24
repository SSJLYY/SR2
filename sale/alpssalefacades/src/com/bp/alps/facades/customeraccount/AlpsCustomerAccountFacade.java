package com.bp.alps.facades.customeraccount;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.PasswordForgetResponse;
import com.bp.alps.facades.data.VerificationCodeResponse;
import com.bp.alps.facades.data.user.PasswordForgetRequest;
import com.bp.alps.facades.data.user.PasswordResetRequest;

public interface AlpsCustomerAccountFacade
{
    /**
     * 修改密码shaun
     * @param passwordResetRequest
     * @return DefaultResponse
     */
    DefaultResponse getResetPasswordResult(PasswordResetRequest passwordResetRequest);



    /**
     * 忘记密码获取验证码:shaun
     * @param passwordForgetRequest
     * @return VerificationCodeResponse
     */
    VerificationCodeResponse getVerificationCode(PasswordForgetRequest passwordForgetRequest);


    /**
     * 忘记密码获取token:shaun
     * @param passwordForgetRequest
     * @return PasswordForgetResponse
     */
    PasswordForgetResponse getToken(PasswordForgetRequest passwordForgetRequest);



}
