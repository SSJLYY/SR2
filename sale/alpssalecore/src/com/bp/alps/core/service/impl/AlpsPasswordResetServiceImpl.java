package com.bp.alps.core.service.impl;

import com.bp.alps.core.service.AlpsPasswordResetService;
import com.bp.alps.facades.data.user.PasswordResetRequest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.PasswordEncoderService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.webservicescommons.model.OAuthAccessTokenModel;
import de.hybris.platform.webservicescommons.oauth2.token.OAuthTokenService;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AlpsPasswordResetServiceImpl implements AlpsPasswordResetService
{

    @Resource
    private OAuthTokenService oauthTokenService;

    @Resource
    private UserService userService;

    @Resource
    private ModelService modelService;

    @Resource(name = "defaultPasswordEncoderService")
    private PasswordEncoderService passwordEncoderService;

    /**
     * 重设密码shaun
     * @param passwordResetRequest
     */
    @Override
    public Boolean resetPassword(PasswordResetRequest passwordResetRequest) {

        Boolean ignorePwd = passwordResetRequest.getIgnorePwd();
        String pwd = passwordResetRequest.getPwd();
        String oldPwd = passwordResetRequest.getOldPwd();
        OAuthAccessTokenModel accessTokenModel = null;


        //通过tokenID获取到oauthToken
        accessTokenModel = this.oauthTokenService.getAccessToken(this.extractTokenKey(passwordResetRequest.getToken()));
        //通过token获取userModel
        UserModel userModel =accessTokenModel.getUser();



        //获取token中user的ID:邮箱
        String id =  accessTokenModel.getUser().getUid();
        //获取请求中的登录用户名:邮箱
        String name = passwordResetRequest.getUsername();

        if(ignorePwd!=true){

            if(passwordResetRequest.getUsername().isEmpty() || accessTokenModel.getUser().getUid().isEmpty()||passwordResetRequest.getOldPwd().isEmpty()){

            }

            if(name.equals(id)){
                if(passwordEncoderService.isValid(userModel,oldPwd)){
                    //重设密码
                    userService.setPassword(accessTokenModel.getUser(),pwd,accessTokenModel.getUser().getPasswordEncoding());
                    //保存操作
                    modelService.save(accessTokenModel.getUser());
                    //刷新
                    modelService.refresh(accessTokenModel.getUser());
                    return true;
                }
            }

        }else {
            //重设密码
            userService.setPassword(accessTokenModel.getUser(),pwd,accessTokenModel.getUser().getPasswordEncoding());
            //保存操作
            modelService.save(accessTokenModel.getUser());
            //刷新
            modelService.refresh(accessTokenModel.getUser());
            return true;
        }


        return false;
    }


    /**
     * 生成tokenid
     * @param value
     * @return
     */
    public String extractTokenKey(String value) {
        if (value == null) {
            return null;
        } else {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException var5) {
                throw new IllegalStateException("SHA-256 algorithm not available.  Fatal (should be in the JDK).");
            }

            try {
                byte[] bytes = digest.digest(value.getBytes("UTF-8"));
                return String.format("%032x", new BigInteger(1, bytes));
            } catch (UnsupportedEncodingException var4) {
                throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
            }
        }
    }

}
