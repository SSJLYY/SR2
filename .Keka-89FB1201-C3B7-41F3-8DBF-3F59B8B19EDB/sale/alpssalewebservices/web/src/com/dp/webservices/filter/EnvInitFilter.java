package com.dp.webservices.filter;

import com.dp.alps.facades.constants.AlpssalefacadesConstants;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.webservicescommons.oauth2.HybrisOauth2UserFilter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class EnvInitFilter extends HybrisOauth2UserFilter
{
	@Resource(
			name = "userService"
	)
	private UserService userService;

	@Resource
	private BaseSiteService baseSiteService;

	@Resource
	private CommonI18NService commonI18NService;

	private static String baseSiteCode = Config.getString(AlpssalefacadesConstants.Store.ALPS_BASESITE_CODE, "alps");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.setHeader("Access-Control-Allow-Origin","*");
		if (auth != null && auth instanceof OAuth2Authentication && !((OAuth2Authentication)auth).isClientOnly()) {
			UserModel userModel = this.userService.getUserForUID((String)auth.getPrincipal());
			this.userService.setCurrentUser(userModel);

			if(userModel instanceof CustomerModel){
				CustomerModel customerModel = (CustomerModel)userModel;
				BaseStoreModel storeModel = customerModel.getStore();
				if(storeModel!=null){
					if(CollectionUtils.isNotEmpty(storeModel.getCurrencies()))
					{
						final Iterator iterator = storeModel.getCurrencies().iterator();
						if (iterator.hasNext())
						{
							commonI18NService.setCurrentCurrency((CurrencyModel) iterator.next());
						}
					}
					if(CollectionUtils.isNotEmpty(storeModel.getLanguages())){
						final Iterator lanIterator = storeModel.getLanguages().iterator();

						if(lanIterator.hasNext())
						{
							LanguageModel languageModel = (LanguageModel)lanIterator.next();
							commonI18NService.setCurrentLanguage(languageModel);
						}
					}
					Collection<BaseSiteModel> baseSiteModelList = storeModel.getCmsSites();
					if(CollectionUtils.isNotEmpty(baseSiteModelList))
					{
						List<BaseSiteModel> baseSiteModels = new ArrayList<>(baseSiteModelList);
						baseSiteService.setCurrentBaseSite(baseSiteModels.get(0), true);
						chain.doFilter(request, response);
						return;
					}
				}

				baseSiteService.setCurrentBaseSite(baseSiteCode, true);
				chain.doFilter(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
		return;
	}
}
