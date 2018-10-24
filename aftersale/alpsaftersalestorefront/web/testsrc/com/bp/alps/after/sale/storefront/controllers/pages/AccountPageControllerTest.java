/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.bp.alps.after.sale.storefront.controllers.pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.storefront.util.PageTitleResolver;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController.ShowMode;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateEmailForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdatePasswordForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateProfileForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.AddressValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.EmailValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.PasswordValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.ProfileValidator;
import de.hybris.platform.acceleratorstorefrontcommons.forms.verification.AddressVerificationResultHandler;
import de.hybris.platform.acceleratorstorefrontcommons.util.AddressDataUtil;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.pages.PageTemplateModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commercefacades.address.AddressVerificationFacade;
import de.hybris.platform.commercefacades.address.data.AddressVerificationResult;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.address.AddressVerificationDecision;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import com.bp.alps.after.sale.storefront.controllers.ControllerConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@UnitTest
public class AccountPageControllerTest
{
	private final Model page = Mockito.spy(new BindingAwareModelMap());

	private static final String VIEW_FOR_PAGE = "accountTest.jsp";
	private static final String VIEW_PREFIX = "pages/";
	private static final String FULL_VIEW_PATH = VIEW_PREFIX + VIEW_FOR_PAGE;
	private static final String TITLE_FOR_PAGE = "Account Test Title";

	public static final String CMS_PAGE_MODEL = "cmsPage";
	public static final String FIRST_NAME = "First";
	public static final String LAST_NAME = "Last";
	public static final String EMAIL = "hybris@hybris.com";
	public static final String TITLE_CODE = "Mr.";
	public static final String TEST_CODE = "12345";
	public static final String TEST_COUNTRY_CODE = "US";
	private static final String REDIRECT_TO_EDIT_ADDRESS_PAGE = "redirect:/my-account/edit-address/";
	private static final String REDIRECT_TO_UPDATE_PROFILE = "redirect:/my-account/update-profile";
	private static final String REDIRECT_TO_PAYMENT_INFO_PAGE = "redirect:/my-account/payment-details";
	private static final String REDIRECT_TO_PASSWORD_UPDATE_PAGE = "redirect:/my-account/update-password";
	private static final String REDIRECT_TO_ADDRESS_BOOK_PAGE = "redirect:/my-account/address-book";
	private static final String REDIRECT_TO_ORDER_HISTORY_PAGE = "redirect:/my-account/orders";

	private static final String UPDATE_EMAIL_CMS_PAGE = "update-email";
	private static final String UPDATE_PROFILE_CMS_PAGE = "update-profile";

	@InjectMocks
	private final AccountPageController accountController = Mockito.spy(new AccountPageController());

	@Mock
	private UserFacade userFacade;
	@Mock
	private OrderFacade orderFacade;
	@Mock
	private CheckoutFacade checkoutFacade;
	@Mock
	private CustomerFacade customerFacade;
	@Mock
	private AddressVerificationFacade addressVerificationFacade;
	@Mock
	private I18NFacade i18NFacade;
	@Mock
	private I18NService i18NService;
	@Mock
	private ContentPageModel contentPageModel;
	@Mock
	private AddressData addressData;
	@Mock
	private CountryData countryData;
	@Mock
	private RegionData regionData;
	@Mock
	private CustomerData customerData;
	@Mock
	private TitleData titleData;
	@Mock
	private PaginationData paginationData;
	@Mock
	private OrderHistoryData orderHistoryData;
	@Mock
	private OrderData orderData;
	@Mock
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	@Mock
	private Breadcrumb breadcrumb;
	@Mock
	private MessageSource messageSource;
	@Mock
	private CMSPageService cmsPageService;
	@Mock
	private PageTitleResolver pageTitleResolver;
	@Mock
	private PageTemplateModel pageTemplateModel;
	@Mock
	private AbstractPageModel abstractPageModel;
	@Mock
	private AddressForm addressForm;
	@Mock
	private UpdateEmailForm emailForm;
	@Mock
	private UpdateProfileForm profileForm;
	@Mock
	private UpdatePasswordForm passwordForm;
	@Mock
	private BindingResult bindingResult;
	@Mock
	private RedirectAttributes redirectModel;
	@Mock
	private AddressVerificationResultHandler addressVerificationResultHandler;
	@Mock
	private PasswordValidator passwordValidator; //NOPMD
	@Mock
	private EmailValidator emailValidator; //NOPMD
	@Mock
	private AddressValidator addressValidator; //NOPMD
	@Mock
	private ProfileValidator profileValidator; //NOPMD
	@Mock
	private SiteConfigService siteConfigService; //NOPMD

	private List breadcrumbsList;

	private SearchPageData<OrderHistoryData> searchList;

	@InjectMocks
	private final AddressDataUtil addressConverter = Mockito.spy(new AddressDataUtil());

	@Before
	public void prepare() throws CMSItemNotFoundException
	{
		MockitoAnnotations.initMocks(this);
		final Locale locale = new Locale("en");
		final List breadcrumbsList = new ArrayList();
		breadcrumbsList.add(breadcrumb);


		BDDMockito.given(accountBreadcrumbBuilder.getBreadcrumbs(Mockito.anyString())).willReturn(breadcrumbsList);
		BDDMockito.given(cmsPageService.getPageForLabelOrId(Mockito.anyString())).willReturn(contentPageModel);
		BDDMockito.given(pageTitleResolver.resolveContentPageTitle(Mockito.anyString())).willReturn(TITLE_FOR_PAGE);
		BDDMockito.given(Boolean.valueOf(page.containsAttribute(CMS_PAGE_MODEL))).willReturn(Boolean.TRUE);
		BDDMockito.given(page.asMap().get(CMS_PAGE_MODEL)).willReturn(abstractPageModel);
		BDDMockito.given(abstractPageModel.getMasterTemplate()).willReturn(pageTemplateModel);
		BDDMockito.given(cmsPageService.getFrontendTemplateName(pageTemplateModel)).willReturn(VIEW_FOR_PAGE);

		BDDMockito.given(checkoutFacade.getDeliveryCountries()).willReturn(Collections.singletonList(countryData));
		BDDMockito.given(userFacade.getTitles()).willReturn(Collections.singletonList(titleData));

		BDDMockito.given(customerData.getFirstName()).willReturn(FIRST_NAME);
		BDDMockito.given(customerData.getLastName()).willReturn(LAST_NAME);
		BDDMockito.given(customerData.getTitleCode()).willReturn(TITLE_CODE);
		BDDMockito.given(customerData.getUid()).willReturn(FIRST_NAME);
		BDDMockito.given(customerFacade.getCurrentCustomer()).willReturn(customerData);
		BDDMockito.given(i18NService.getCurrentLocale()).willReturn(locale);
		BDDMockito.given(i18NFacade.getRegionsForCountryIso(Mockito.anyString())).willReturn(Collections.singletonList(regionData));
		BDDMockito.given(messageSource.getMessage(Mockito.anyString(), Mockito.any(Object[].class), Mockito.eq(locale)))
				.willReturn("ANY STRING");
		BDDMockito.given(i18NFacade.getCountryForIsocode(Mockito.anyString())).willReturn(countryData);
	}
}
