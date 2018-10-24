package com.bp.alps.core.service.impl;

import com.bp.alps.core.service.AlpsTokenEndpointService;
import com.bp.alps.facades.data.OAuthAccessTokenData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.ModelRemovalException;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.webservicescommons.model.OAuthAccessTokenModel;
import de.hybris.platform.webservicescommons.oauth2.token.OAuthTokenService;
import de.hybris.platform.webservicescommons.oauth2.token.provider.HybrisOAuthTokenServices;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AlpsTokenEndpointServiceImpl extends HybrisOAuthTokenServices implements AlpsTokenEndpointService
{
    private static final Logger LOG = Logger.getLogger(AlpsTokenEndpointServiceImpl.class);

    @Resource
    private OAuthTokenService oauthTokenService;

    OAuthAccessTokenData accessTokenData = new OAuthAccessTokenData();

    public OAuthAccessTokenData createMyAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        try {
            OAuthAccessTokenModel accessTokenModel = null;
            OAuth2AccessToken oAuth2AccessToken = super.createAccessToken(authentication);

            accessTokenModel = this.oauthTokenService.getAccessToken(this.extractTokenKey(oAuth2AccessToken.toString()));
            if (null !=accessTokenModel.getUser()){
                UserModel userModel = accessTokenModel.getUser();
                accessTokenData.setUserCode(userModel.getUid());
                accessTokenData.setUserName(userModel.getName());
                if(userModel instanceof CustomerModel)
                {
                    CustomerModel customerModel = (CustomerModel)userModel;
                    if (null != customerModel.getStore())
                    {
                        accessTokenData.setStoreUid(customerModel.getStore().getUid());
                        accessTokenData.setStoreName(customerModel.getStore().getName());
                    }
                }
            }
            accessTokenData.setToken(oAuth2AccessToken.getValue());
        } catch (ModelSavingException var3) {
            LOG.debug("HybrisOAuthTokenServices->createAccessToken : ModelSavingException : " + var3.getMessage());
        } catch (ModelRemovalException var4) {
            LOG.debug("HybrisOAuthTokenServices->createAccessToken : ModelRemovalException :" + var4.getMessage());
        }
        return accessTokenData;
    }




    protected String extractTokenKey(String value) {
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
