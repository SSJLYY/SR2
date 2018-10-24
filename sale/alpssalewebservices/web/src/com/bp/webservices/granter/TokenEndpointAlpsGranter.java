package com.bp.webservices.granter;

import com.bp.alps.facades.data.OAuthAccessTokenData;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;

public interface TokenEndpointAlpsGranter extends TokenGranter
{

    OAuthAccessTokenData getMyAccessToken(OAuth2Authentication authentication);



    /**
     * 等于"password"
     * @param grantType
     * @param tokenRequest
     * @return OAuthAccessTokenData
     */

    OAuthAccessTokenData grantForPassword(String grantType, TokenRequest tokenRequest);


}
