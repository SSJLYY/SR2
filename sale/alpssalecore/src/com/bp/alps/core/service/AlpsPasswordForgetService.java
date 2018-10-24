package com.bp.alps.core.service;

import com.bp.alps.facades.data.user.PasswordForgetRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;

public interface AlpsPasswordForgetService {

    /**
     *  shaun:获取验证码
     * @param passwordForgetRequest
     * @return
     */
    String getVerificationCode(PasswordForgetRequest passwordForgetRequest);


    /**
     * shaun:校验验证码
     * @param verificationCode
     * @return
     */
    Boolean checkVerificationCode(String verificationCode);


    /**
     *shaun :校验用户是否存在
     * @param username
     * @return
     */
    Boolean checkUserName(String username);


    /**
     * shaun:获取token
     * @param username
     * @param client
     * @return
     */
    OAuth2AccessToken getAccesstoken(String username, ClientDetails client);


    /**
     * shaun:创建token
     * @param username
     * @param client
     * @return
     */
    OAuth2AccessToken createNewAccesstoken(String username, ClientDetails client);


}
