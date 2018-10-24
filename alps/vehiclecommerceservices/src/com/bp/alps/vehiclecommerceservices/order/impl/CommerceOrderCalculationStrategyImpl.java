package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.vehiclecommerceservices.order.CommerceOrderCalculationStrategy;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCartCalculationStrategy;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.CalculationService;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.promotions.jalo.PromotionsManager;
import org.springframework.beans.BeansException;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


public class CommerceOrderCalculationStrategyImpl extends DefaultCommerceCartCalculationStrategy implements
		CommerceOrderCalculationStrategy
{
	public static final String CALCULATESERVICESUFFIX = "CalculationService";

	public boolean calculateOrder(final AbstractOrderModel orderModel){

		validateParameterNotNull(orderModel, "order model cannot be null");

		final CalculationService calcService = getCalculationService(orderModel);
		boolean recalculated = false;
		if (calcService.requiresCalculation(orderModel))
		{
			try
			{
				calcService.calculate(orderModel);
				getPromotionsService().updatePromotions(getPromotionGroups(), orderModel, true, PromotionsManager.AutoApplyMode.APPLY_ALL,
						PromotionsManager.AutoApplyMode.APPLY_ALL, getTimeService().getCurrentTime());
			}
			catch (final CalculationException calculationException)
			{
				throw new IllegalStateException("order model " + orderModel.getCode() + " was not calculated due to: "
						+ calculationException.getMessage(), calculationException);
			}
			finally
			{
			}
			recalculated = true;
		}
		return recalculated;
	}

	protected CalculationService getCalculationService(final AbstractOrderModel orderModel)
	{
		final String typeCode = lowerFirstCase(orderModel.getItemtype());
		if(Registry.getApplicationContext().containsBean(typeCode+CALCULATESERVICESUFFIX))
		{
			try
			{
				CalculationService specialCalculationService = Registry.getApplicationContext().getBean(typeCode + CALCULATESERVICESUFFIX, CalculationService.class);
				return specialCalculationService;
			}catch (BeansException e){
				return getCalculationService();
			}
		}
		return getCalculationService();
	}

	public String lowerFirstCase(String str){
		char[] chars = str.toCharArray();
		chars[0] +=32;
		return String.valueOf(chars);
	}
}
