package com.bp.alps.core.order.impl;

import com.bp.alps.core.order.OrderFactory;
import de.hybris.platform.commerceservices.order.impl.CommerceCartFactory;
import de.hybris.platform.core.enums.DeliveryStatus;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.enums.PaymentStatus;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Date;


public class OrderFactoryImpl extends CommerceCartFactory implements OrderFactory
{
	private KeyGenerator keyGenerator;
	private ModelService modelService;
	private UserService userService;
	private CommonI18NService commonI18NService;

	public VehicleOrderModel createOrder()
	{
		final UserModel user = userService.getCurrentUser();
		final CurrencyModel currency = commonI18NService.getCurrentCurrency();
		final VehicleOrderModel order = modelService.create(VehicleOrderModel.class);
		order.setCode(String.valueOf(keyGenerator.generate()));
		order.setUser(user);
		order.setCurrency(currency);
		order.setDate(new Date());
		order.setNet(Boolean.valueOf(getNetGrossStrategy().isNet()));
		order.setSite(getBaseSiteService().getCurrentBaseSite());
		order.setStore(getBaseStoreService().getCurrentBaseStore());
		order.setGuid(getGuidKeyGenerator().generate().toString());
		order.setPaymentStatus(PaymentStatus.NOTPAID);
		order.setDeliveryStatus(DeliveryStatus.NOTSHIPPED);
		order.setStatus(OrderStatus.CREATED);
		order.setEntries(new ArrayList<>());
		return order;
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
