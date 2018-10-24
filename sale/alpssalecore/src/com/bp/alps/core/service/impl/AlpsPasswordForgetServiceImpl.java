package com.bp.alps.core.service.impl;

import com.bp.alps.core.service.AlpsPasswordForgetService;
import com.bp.alps.facades.data.user.PasswordForgetRequest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.user.daos.UserDao;
import de.hybris.platform.webservicescommons.model.OAuthAccessTokenModel;
import de.hybris.platform.webservicescommons.model.OAuthRefreshTokenModel;
import de.hybris.platform.webservicescommons.oauth2.token.OAuthTokenService;
import de.hybris.platform.webservicescommons.oauth2.token.provider.HybrisOAuthTokenServices;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * 修改/忘记密码
 */
public class AlpsPasswordForgetServiceImpl extends HybrisOAuthTokenServices implements AlpsPasswordForgetService {

    private static final Logger LOG = Logger.getLogger(AlpsPasswordForgetServiceImpl.class);
    private boolean supportRefreshToken = false;
    private int refreshTokenValiditySeconds = 2592000;
    private int accessTokenValiditySeconds = 43200;

    @Resource
    private UserService userService;

    @Resource
    private OAuthTokenService oauthTokenService;

    @Resource
    private UserDao userDao;

    /**
     * 忘记密码获取验证码:shaun
     * @param passwordForgetRequest
     * @return
     */
    @Override
    public String getVerificationCode(PasswordForgetRequest passwordForgetRequest) {
        String verificationCode = null;

            verificationCode = "1234";

        return verificationCode;
    }



    /**
     *校验验证码
     * @param verificationCode
     * @return
     */
    @Override
    public Boolean checkVerificationCode(String verificationCode) {
        if("1234".equals(verificationCode)){
            return true;
        }else{
            return false;
        }
    }



    /**
     *校验用户名
     * @param username
     * @return
     */
    @Override
    public Boolean checkUserName(String username) {
        UserModel user = this.userDao.findUserByUID(username);
        if (user != null) {
            return true;
        }else{
            return false;
        }

    }



    /**
     * 获取 token
     * @param username
     * @param client
     * @return
     */
    @Override
    public OAuth2AccessToken getAccesstoken(String username,ClientDetails client) {

        String clientId = client.getClientId();
        Set<String> scopes = client.getScope();
        OAuth2AccessToken oAuth2AccessToken = null;
        OAuthAccessTokenModel accessTokenModel = null;

            String authenticationId = this.extractKey(username,clientId,scopes);

            try {
                accessTokenModel = this.oauthTokenService.getAccessTokenForAuthentication(authenticationId);
                oAuth2AccessToken = this.deserializeAccessToken((byte[]) accessTokenModel.getToken());
            }catch (ClassCastException var6) {
                LOG.warn("Could not extract access token for authentication " + username+clientId);
                this.oauthTokenService.removeAccessTokenForAuthentication(authenticationId);
            } catch (UnknownIdentifierException var7) {
                if (LOG.isInfoEnabled()) {
                    LOG.debug("Failed to find access token for authentication " + username+clientId);
                }
            }

            try {
                if (oAuth2AccessToken != null && accessTokenModel != null && !StringUtils.equals(authenticationId, this.extractKey(username,clientId,scopes))) {
                    this.replaceToken(username,clientId,scopes, oAuth2AccessToken);
                }
            } catch (ClassCastException | IllegalArgumentException var5) {
                this.replaceToken(username,clientId,scopes, oAuth2AccessToken);
            }

        return oAuth2AccessToken;
    }



    /**
     * 创建token
     * @param username
     * @param client
     * @return
     */
    @Override
    public OAuth2AccessToken createNewAccesstoken(String username, ClientDetails client) {

        String clientId = client.getClientId();
        Set<String> scopes = client.getScope();
        OAuth2AccessToken oAuth2AccessToken = null;
        OAuth2RefreshToken refreshToken = null;
        refreshToken = this.createMyRefreshToken(client);
        oAuth2AccessToken = this.createMyAccessToken(scopes,client, refreshToken);
        this.storeAccessToken(oAuth2AccessToken, username,clientId,scopes);
        return oAuth2AccessToken;

    }




    /**
     * 创建需要的token
     * @param scopes
     * @param client
     * @param refreshToken
     * @return
     */
    private OAuth2AccessToken createMyAccessToken(Set<String> scopes,ClientDetails client, OAuth2RefreshToken refreshToken) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        int validitySeconds = this.getNewAccessTokenValiditySeconds(client);
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (long)validitySeconds * 1000L));
        }
        token.setRefreshToken(refreshToken);
        token.setScope(scopes);
        return (OAuth2AccessToken)(token);
    }



    /**
     *
     * @param client
     * @return
     */
    protected int getNewAccessTokenValiditySeconds(ClientDetails client) {
        if (client != null) {
            Integer validity = client.getAccessTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }

        return this.accessTokenValiditySeconds;
    }


    /**
     *
     * @param client
     * @return
     */
    private OAuth2RefreshToken createMyRefreshToken(ClientDetails client) {
        if (!this.isSupportMyRefreshToken(client)) {
            return null;
        } else {
            int validitySeconds = this.getMyRefreshTokenValiditySeconds(client);
            String value = UUID.randomUUID().toString();
            return (OAuth2RefreshToken)(validitySeconds > 0 ? new DefaultExpiringOAuth2RefreshToken(value, new Date(System.currentTimeMillis() + (long)validitySeconds * 1000L)) : new DefaultOAuth2RefreshToken(value));
        }
    }


    /**
     *
     * @param client
     * @return
     */
    protected boolean isSupportMyRefreshToken(ClientDetails client) {
        if (client!= null) {
            return client.getAuthorizedGrantTypes().contains("refresh_token");
        } else {
            return this.supportRefreshToken;
        }
    }


    /**
     *
     * @param client
     * @return
     */
    protected int getMyRefreshTokenValiditySeconds(ClientDetails client) {
        if (client != null) {
            Integer validity = client.getRefreshTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return this.refreshTokenValiditySeconds;
    }



    /**
     * 获取:authenticationId
     * @param value
     * @return
     */
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


    /**
     * 2
     * @param username
     * @param clientId
     * @return
     */
    protected String extractKey(String username,String clientId, Set<String> scopes){
        Map<String, String> values = new LinkedHashMap();
        values.put("username", username);
        values.put("client_id", clientId);
        values.put("scope",OAuth2Utils.formatParameterList(scopes));
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var7) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException var6) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }

    }


    /**
     * 3
     * @param token
     * @return
     */
    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return (OAuth2AccessToken)SerializationUtils.deserialize(token);
    }


    /**
     * 替换token
     * @param username
     * @param clientId
     * @param oAuth2AccessToken
     */
    protected void replaceToken(String username,String clientId,Set<String> scopes, OAuth2AccessToken oAuth2AccessToken) {
        this.removeAccessToken(oAuth2AccessToken.getValue());
        this.storeAccessToken(oAuth2AccessToken, username,clientId,scopes);
    }


    /**
     * 移除token
     * @param tokenValue
     */
    public void removeAccessToken(String tokenValue) {
        this.oauthTokenService.removeAccessToken(this.extractTokenKey(tokenValue));
    }

    /**
     * 保存token
     * @param oAuth2AccessToken
     * @param username
     * @param clientId
     */
    public void storeAccessToken(OAuth2AccessToken oAuth2AccessToken, String username,String clientId,Set<String> scopes) {
        OAuthRefreshTokenModel refreshTokenModel = null;
        if (oAuth2AccessToken.getRefreshToken() != null) {
            String refreshTokenKey = this.extractTokenKey(oAuth2AccessToken.getRefreshToken().getValue());

            try {
                refreshTokenModel = this.oauthTokenService.getRefreshToken(refreshTokenKey);
            } catch (UnknownIdentifierException var5) {
                refreshTokenModel = this.oauthTokenService.saveRefreshToken(refreshTokenKey, this.serializeRefreshToken(oAuth2AccessToken.getRefreshToken()), this.serializeAuthentication(username,clientId));
            }
        }
        this.oauthTokenService.saveAccessToken(this.extractTokenKey(oAuth2AccessToken.getValue()),this.serializeAccessToken(oAuth2AccessToken),this.extractKey(username,clientId,scopes), this.serializeAuthentication(username,clientId), false ? null : username, clientId, refreshTokenModel);
    }


    protected byte[] serializeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        return SerializationUtils.serialize(oAuth2AccessToken);
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken oAuth2AccessToken) {
        return SerializationUtils.serialize(oAuth2AccessToken);
    }

    protected byte[] serializeAuthentication(String username,String clientId) {
        return SerializationUtils.serialize(username+clientId);
    }
}
