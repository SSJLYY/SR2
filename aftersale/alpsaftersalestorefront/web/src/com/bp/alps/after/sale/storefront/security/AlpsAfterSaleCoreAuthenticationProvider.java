package com.bp.alps.after.sale.storefront.security;

import de.hybris.platform.core.Registry;
import de.hybris.platform.jalo.JaloConnection;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.user.LoginToken;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.spring.security.CoreAuthenticationProvider;
import de.hybris.platform.spring.security.CoreUserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

public class AlpsAfterSaleCoreAuthenticationProvider extends CoreAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware
{

    private static final Logger LOG = Logger.getLogger(AlpsAfterSaleCoreAuthenticationProvider.class.getName());
    private UserDetailsService userDetailsService;
    private UserDetailsChecker preAuthenticationChecks;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private final UserDetailsChecker postAuthenticationChecks = new AlpsAfterSaleCoreAuthenticationProvider.DefaultPostAuthenticationChecks((AlpsAfterSaleCoreAuthenticationProvider.DefaultPostAuthenticationChecks)null);



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (Registry.hasCurrentTenant() && JaloConnection.getInstance().isSystemInitialized()) {
            String username = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
            UserDetails userDetails = null;

            try {
                userDetails = this.retrieveUser(username);
            } catch (UsernameNotFoundException var6) {
                throw new BadCredentialsException(this.messages.getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"), var6);
            }

            this.getPreAuthenticationChecks().check(userDetails);
            User user = UserManager.getInstance().getUserByLogin(userDetails.getUsername());
            Object credential = authentication.getCredentials();
            if (credential instanceof String) {
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                String findValue = "ROLE_SALESCONSULTANT";

                if(!authorities.contains(findValue)){
                    throw new BadCredentialsException(this.messages.getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"));
                }
                if (!user.checkPassword((String)credential)) {
                    throw new BadCredentialsException(this.messages.getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"));
                }
            } else {
                if (!(credential instanceof LoginToken)) {
                    throw new BadCredentialsException(this.messages.getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"));
                }

                if (!user.checkPassword((LoginToken)credential)) {
                    throw new BadCredentialsException(this.messages.getMessage("CoreAuthenticationProvider.badCredentials", "Bad credentials"));
                }
            }

            this.additionalAuthenticationChecks(userDetails, (AbstractAuthenticationToken)authentication);
            this.postAuthenticationChecks.check(userDetails);
            JaloSession.getCurrentSession().setUser(user);
            return this.createSuccessAuthentication(authentication, userDetails);
        } else {
            return this.createSuccessAuthentication(authentication, new CoreUserDetails("systemNotInitialized", "systemNotInitialized", true, false, true, true, Collections.EMPTY_LIST, (String)null));
        }
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {

        private DefaultPostAuthenticationChecks(DefaultPostAuthenticationChecks defaultPostAuthenticationChecks) {

        }

        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException(AlpsAfterSaleCoreAuthenticationProvider.this.messages.getMessage("CoreAuthenticationProvider.credentialsExpired", "User credentials have expired"));
            }
        }
    }




}
