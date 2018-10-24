package com.bp.alps.core.quotation.impl;

import com.bp.alps.core.quotation.QuotationFactory;
import de.hybris.platform.commerceservices.order.impl.CommerceCartFactory;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import com.bp.alps.core.model.QuotationInfoModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Date;


public class QuotationFactoryImpl extends CommerceCartFactory implements QuotationFactory
{
	private KeyGenerator keyGenerator;
	private ModelService modelService;
	private UserService userService;
	private CommonI18NService commonI18NService;
	private String keyPrefix;

	@Override
	public QuotationInfoModel createQuotaion()
	{
		final UserModel user = userService.getCurrentUser();
		final CurrencyModel currency = commonI18NService.getCurrentCurrency();
		final QuotationInfoModel quotation = modelService.create(QuotationInfoModel.class);
		quotation.setCode(keyPrefix + String.valueOf(keyGenerator.generate()));
		quotation.setUser(user);
		quotation.setCurrency(currency);
		quotation.setDate(new Date());
		quotation.setNet(Boolean.valueOf(getNetGrossStrategy().isNet()));
		quotation.setSite(getBaseSiteService().getCurrentBaseSite());
		quotation.setStore(getBaseStoreService().getCurrentBaseStore());
		quotation.setGuid(getGuidKeyGenerator().generate().toString());
		quotation.setEntries(new ArrayList<>());
		return quotation;
	}

	@Required
	public void setKeyGenerator(final KeyGenerator keyGenerator)
	{
		this.keyGenerator = keyGenerator;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		super.setModelService(modelService);
		this.modelService = modelService;
	}

	public String getKeyPrefix()
	{
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix)
	{
		this.keyPrefix = keyPrefix;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}
}
