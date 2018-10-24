package com.bp.alps.facades.customeraccount.impl;

import com.bp.alps.core.service.AlpsPasswordForgetService;
import com.bp.alps.core.service.AlpsPasswordResetService;
import com.bp.alps.facades.constants.AlpssalefacadesConstants;
import com.bp.alps.facades.customeraccount.AlpsCustomerAccountFacade;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.OAuthAccessTokenData;
import com.bp.alps.facades.data.PasswordForgetResponse;
import com.bp.alps.facades.data.VerificationCodeResponse;
import com.bp.alps.facades.data.user.PasswordForgetRequest;
import com.bp.alps.facades.data.user.PasswordResetRequest;
import de.hybris.platform.util.Config;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import javax.annotation.Resource;

/**
 * 修改/忘记密码
 */
public class AlpsCustomerAccountFacadeImpl implements AlpsCustomerAccountFacade
{

    private static final String TEXT_USER_ACCOUNT_NOTEXIST ="text.user.account.notexist";
    private static final String TEXT_USER_ACCOUNT_VERIFICATION_FAILED ="text.user.account.verification.failed";
    private static final String TEXT_USER_ACCOUNT_PASSWORD_RESET_FAILED ="text.user.account.password.reset.failed";


    @Resource
    private AlpsPasswordResetService alpsPasswordResetService;

    @Resource
    private AlpsPasswordForgetService alpsPasswordForgetService;

    @Resource
    private ClientDetailsService clientDetailsService;

    /**
     * 修改密码shaun
     * @param passwordResetRequest
     * @return DefaultResponse
     */
    public DefaultResponse getResetPasswordResult(PasswordResetRequest passwordResetRequest) {

            DefaultResponse defaultResponse = new DefaultResponse();

        try {
            Boolean isReset = alpsPasswordResetService.resetPassword(passwordResetRequest);
           if(isReset){
               defaultResponse.setSuccess(true);
               return defaultResponse;
           }
            defaultResponse.setMessageCode(TEXT_USER_ACCOUNT_PASSWORD_RESET_FAILED);
        } catch (Exception e) {
            defaultResponse.setMessage(e.getMessage());
        }
        defaultResponse.setSuccess(false);
        return defaultResponse;
    }


    /**
     * 忘记密码获取验证码:shaun
     * @param passwordForgetRequest
     * @return
     */
    @Override
    public VerificationCodeResponse getVerificationCode(PasswordForgetRequest passwordForgetRequest) {

        VerificationCodeResponse verificationCodeResponse = new VerificationCodeResponse();

        String username = passwordForgetRequest.getUsername();
        Boolean isUserExists = alpsPasswordForgetService.checkUserName(username);
        if (!isUserExists){
            verificationCodeResponse.setMessageCode(TEXT_USER_ACCOUNT_NOTEXIST);
        }else{
            try {
                String code = alpsPasswordForgetService.getVerificationCode(passwordForgetRequest);
                verificationCodeResponse.setVerificationCode(code);
            } catch (Exception e) {
                verificationCodeResponse.setMessage(e.getMessage());
            }
        }

        return verificationCodeResponse;
    }


    /**
     * 忘记密码获取token:shaun
     * @param passwordForgetRequest
     * @return
     */
    @Override
    public PasswordForgetResponse getToken(PasswordForgetRequest passwordForgetRequest) {

        PasswordForgetResponse passwordForgetResponse = new PasswordForgetResponse();

        String username = passwordForgetRequest.getUsername();
        String verificationCode = passwordForgetRequest.getVerificationCode();

        Boolean isCorrectCode = alpsPasswordForgetService.checkVerificationCode(verificationCode);
            if(!isCorrectCode){
                passwordForgetResponse.setMessageCode(TEXT_USER_ACCOUNT_VERIFICATION_FAILED);
                return passwordForgetResponse;
            }else {
                Boolean isUserExists = alpsPasswordForgetService.checkUserName(username);
                if (!isUserExists){
                    passwordForgetResponse.setMessageCode(TEXT_USER_ACCOUNT_NOTEXIST);
                    return passwordForgetResponse;
                }else{
                    //从配置文件中获取clientID
                    final String clientId = Config.getString(AlpssalefacadesConstants.ChangePwd.CLIENTID,"smartedit");

                    //通过clientId获取client
                    ClientDetails client = this.clientDetailsService.loadClientByClientId(clientId);

                    OAuth2AccessToken oAuth2AccessToken = null;
                    OAuthAccessTokenData accessTokenData = new OAuthAccessTokenData();

                    try{
                        //获取token
                        oAuth2AccessToken = alpsPasswordForgetService.getAccesstoken(username, client);
                    }catch (Exception e)
                    {
                        passwordForgetResponse.setMessage(e.getMessage());
                    }

                    if (oAuth2AccessToken==null){
                        try{
                            //创建token
                            oAuth2AccessToken = alpsPasswordForgetService.createNewAccesstoken(username, client);
                        }catch (Exception e)
                        {
                            passwordForgetResponse.setMessage(e.getMessage());
                        }

                    }

                    //model转化为data
                    accessTokenData.setToken(oAuth2AccessToken.getValue());
                    passwordForgetResponse.setAccessTokenData(accessTokenData);
                }
            }

        return passwordForgetResponse;
    }

}
