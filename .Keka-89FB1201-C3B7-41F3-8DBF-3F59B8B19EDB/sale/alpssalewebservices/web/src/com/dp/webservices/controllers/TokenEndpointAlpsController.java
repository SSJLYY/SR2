package com.dp.webservices.controllers;

import com.dp.alps.facades.data.OAuthAccessTokenData;
import com.dp.webservices.granter.TokenEndpointAlpsGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.endpoint.AbstractEndpoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

@Controller
public class TokenEndpointAlpsController extends AbstractEndpoint
{
	private TokenEndpointAlpsGranter tokenEndpointAlpsGranter;

	private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();

	private OAuthAccessTokenData mytoken =null;

	@Autowired
	public TokenEndpointAlpsController(TokenEndpointAlpsGranter tokenGranter ,ClientDetailsService clientDetailsService)
	{
		tokenEndpointAlpsGranter = tokenGranter;
		super.setTokenGranter(tokenGranter);
		super.setClientDetailsService(clientDetailsService);
	}

	@RequestMapping(
			value = {"/oauth/token"},
			method = {RequestMethod.POST}
	)
	public ResponseEntity<OAuthAccessTokenData> postAccessMyToken(Principal principal, @RequestParam Map<String, String> parameters) throws
			HttpRequestMethodNotSupportedException {
		if (!(principal instanceof Authentication)) {
			throw new InsufficientAuthenticationException("There is no client authentication. Try adding an appropriate authentication filter.");
		} else {
			String clientId = this.getClientId(principal);
			ClientDetails authenticatedClient = this.getClientDetailsService().loadClientByClientId(clientId);
			TokenRequest tokenRequest = this.getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);
			if (clientId != null && !clientId.equals("") && !clientId.equals(tokenRequest.getClientId())) {
				throw new InvalidClientException("Given client ID does not match authenticated client");
			} else {
				if (authenticatedClient != null) {
					this.oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);
				}

				if (!StringUtils.hasText(tokenRequest.getGrantType())) {
					throw new InvalidRequestException("Missing grant type");
				} else if (tokenRequest.getGrantType().equals("implicit")) {
					throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
				} else {
					OAuthAccessTokenData mytoken = new OAuthAccessTokenData();
					if ("password".equals(tokenRequest.getGrantType())){
						//="password"
						 mytoken = tokenEndpointAlpsGranter.grantForPassword(tokenRequest.getGrantType(), tokenRequest);
					}else{
						//!="password"
						OAuth2AccessToken oauth2AccessToken = this.getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
						if (oauth2AccessToken == null) {
							throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
						} else {
							OAuth2Authentication authentication = (OAuth2Authentication)oauth2AccessToken;
							mytoken.setToken(oauth2AccessToken.getValue());
						}

					}
					return this.getmyResponse(mytoken);
				}
			}
		}
	}

	protected String getClientId(Principal principal) {
		Authentication client = (Authentication)principal;
		if (!client.isAuthenticated()) {
			throw new InsufficientAuthenticationException("The client is not authenticated.");
		} else {
			String clientId = client.getName();
			if (client instanceof OAuth2Authentication) {
				clientId = ((OAuth2Authentication)client).getOAuth2Request().getClientId();
			}

			return clientId;
		}
	}

	private ResponseEntity<OAuthAccessTokenData> getmyResponse(OAuthAccessTokenData mytoken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cache-Control", "no-store");
		headers.set("Pragma", "no-cache");
		return new ResponseEntity(mytoken, headers, HttpStatus.OK);
	}

}

