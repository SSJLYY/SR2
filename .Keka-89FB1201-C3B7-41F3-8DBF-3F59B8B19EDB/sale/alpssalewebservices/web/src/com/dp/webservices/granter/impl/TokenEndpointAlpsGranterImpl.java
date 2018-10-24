package com.dp.webservices.granter.impl;

import com.dp.alps.core.service.AlpsTokenEndpointService;
import com.dp.alps.facades.data.OAuthAccessTokenData;
import com.dp.webservices.granter.TokenEndpointAlpsGranter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public class TokenEndpointAlpsGranterImpl extends ResourceOwnerPasswordTokenGranter implements TokenEndpointAlpsGranter
{
    private AlpsTokenEndpointService alpsTokenEndpointService;

    private AuthenticationManager authenticationManager;
    private AuthorizationServerTokenServices tokenServices;
    private ClientDetailsService clientDetailsService;
    private OAuth2RequestFactory requestFactory;
    private String grantType;


    protected TokenEndpointAlpsGranterImpl(AuthenticationManager authenticationManager,AuthorizationServerTokenServices tokenServices,
                                           ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, AlpsTokenEndpointService alpsTokenEndpointService)
    {
        super(authenticationManager,tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager= authenticationManager;
        this.tokenServices = tokenServices;
        this.clientDetailsService = clientDetailsService;
        this.requestFactory = requestFactory;
        this.grantType = grantType;
        this.alpsTokenEndpointService = alpsTokenEndpointService;
    }



    /**
     * 等于password
     * @param grantType
     * @param tokenRequest
     * @return
     */
    public OAuthAccessTokenData grantForPassword(String grantType, TokenRequest tokenRequest) {

        if (!this.grantType.equals(grantType)) {
            return null;
        } else {
            String clientId = tokenRequest.getClientId();
            ClientDetails client = this.clientDetailsService.loadClientByClientId(clientId);
            this.validateGrantType(grantType, client);
            this.logger.debug("Getting access token for: " + clientId);
            return this.getMyAccessToken(this.getOAuth2Authentication(client, tokenRequest));
        }

    }

    @Override
    public OAuthAccessTokenData getMyAccessToken(OAuth2Authentication authentication)
    {
        return alpsTokenEndpointService.createMyAccessToken(authentication);
    }

}
