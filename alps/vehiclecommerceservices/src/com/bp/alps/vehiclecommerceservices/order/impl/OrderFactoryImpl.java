package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.vehiclecommerceservices.order.AlpsCloneAbstractOrderStrategy;
import com.bp.alps.vehiclecommerceservices.order.OrderFactory;
import de.hybris.platform.commerceservices.order.impl.CommerceCartFactory;
import de.hybris.platform.core.enums.DeliveryStatus;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.enums.PaymentStatus;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;


public class OrderFactoryImpl extends CommerceCartFactory implements OrderFactory
{
	private KeyGenerator keyGenerator;
	private ModelService modelService;
	private UserService userService;
	private CommonI18NService commonI18NService;

	@Resource
	private AlpsCloneAbstractOrderStrategy alpsCloneAbstractOrderStrategy;

	public <T extends AbstractOrderModel> T cloneWithoutEntry(final ComposedTypeModel _orderType, final AbstractOrderModel original, final Class abstractOrderClassResult){
		return alpsCloneAbstractOrderStrategy.cloneWithoutEntry(_orderType, original, String.valueOf(keyGenerator.generate()), abstractOrderClassResult);
	}

	public AbstractOrderModel initializeOrderByType(Class orderType)
	{
		final UserModel user = userService.getCurrentUser();
		final CurrencyModel currency = commonI18NService.getCurrentCurrency();
		final AbstractOrderModel order = modelService.create(orderType);
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
