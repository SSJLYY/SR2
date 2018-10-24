package com.bp.alps.core.service;

import com.bp.alps.facades.data.OAuthAccessTokenData;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface AlpsTokenEndpointService {

    OAuthAccessTokenData createMyAccessToken(OAuth2Authentication authentication);

}
