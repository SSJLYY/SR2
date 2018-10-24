package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.core.data.order.AlpsCommerceEntryResult;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.vehiclecommerceservices.order.*;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.impl.DefaultCommercePlaceOrderStrategy;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;


public class AlpsCommerceHandleOrderStrategyImpl extends DefaultCommercePlaceOrderStrategy implements
		AlpsCommerceHandleOrderStrategy
{
	@Resource
	private CommerceOrderCalculationStrategy alpsCommerceOrderCalculationStrategy;

	@Resource
	private AlpsOrderEntryManagementStrategy alpsOrderEntryManagementStrategy;

	@Resource
	private CommerceStockManagementStrategy alpsCommerceStockManagementStrategy;

	@Resource
	private OrderFactory alpsDefaultOrderFactory;

	@Resource
	private ModelService modelService;

	public <T extends AbstractOrderModel> T cloneWithoutEntry(final ComposedTypeModel _orderType, final AbstractOrderModel original, final Class abstractOrderClassResult){
		return alpsDefaultOrderFactory.cloneWithoutEntry(_orderType, original, abstractOrderClassResult);
	}

	public AbstractOrderModel initializeOrderByType(Class orderType){
		return alpsDefaultOrderFactory.initializeOrderByType(orderType);
	}

	public AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter parameter){
		final AbstractOrderModel order = parameter.getOrder();
		AlpsCommerceResult alpsCommerceResult = new AlpsCommerceResult();
		if(CollectionUtils.isNotEmpty(parameter.getAdditionalEntryies()))
		{
			if(parameter.getManagementStock())
			{
				alpsCommerceResult = validateEntries(parameter.getAdditionalEntryies());
				if (!alpsCommerceResult.getSuccess())
				{
					return alpsCommerceResult;
				}
			}

			alpsCommerceResult = tryToAddEntryies(parameter);
			if(!alpsCommerceResult.getSuccess()){
				return alpsCommerceResult;
			}
		}
		alpsCommerceOrderCalculationStrategy.calculateOrder(order);
		alpsCommerceResult.setSuccess(true);
		alpsCommerceResult.setOrder(order);
		modelService.save(order);

		if(parameter.getManagementStock())
		{
			if (!handleStock(order))
			{
				alpsCommerceResult.setSuccess(false);
				alpsCommerceResult.setErrorMessage("invalid reserve stock");
				return alpsCommerceResult;
			}
		}
		return alpsCommerceResult;
	}

	public AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter parameter){
		final AbstractOrderModel order = parameter.getOrder();
		AlpsCommerceResult alpsCommerceResult = new AlpsCommerceResult();
		if(parameter.getManagementStock())
		{
			List<AlpsCommerceOrderEntryParameter> incrementEntryParameters = alpsOrderEntryManagementStrategy.calculateIncrementEntry(parameter);
			alpsCommerceResult = validateEntries(incrementEntryParameters);
			if (!alpsCommerceResult.getSuccess())
			{
				return alpsCommerceResult;
			}
		}

		List<AlpsCommerceEntryResult> entryResults = alpsOrderEntryManagementStrategy.updateEntry(parameter);

		alpsCommerceOrderCalculationStrategy.calculateOrder(order);
		alpsCommerceResult.setSuccess(true);
		alpsCommerceResult.setOrder(order);
		modelService.save(order);

		if(parameter.getManagementStock())
		{
			alpsCommerceStockManagementStrategy.reserveIncrementStockByHandleResult(order, entryResults);
			if(order.getStatus().equals(OrderStatus.CHECKED_INVALID)){
				alpsCommerceResult.setSuccess(false);
				alpsCommerceResult.setErrorMessage("invalid reserve stock");
				return alpsCommerceResult;
			}

			alpsCommerceStockManagementStrategy.releaseExcessStockByHandleResult(order, entryResults);
			if(order.getStatus().equals(OrderStatus.CHECKED_INVALID)){
				alpsCommerceResult.setSuccess(false);
				alpsCommerceResult.setErrorMessage("invalid relase stock");
				return alpsCommerceResult;
			}
		}
		return alpsCommerceResult;
	}

	protected AlpsCommerceResult validateEntries(List<AlpsCommerceOrderEntryParameter> parameters){
		List<AlpsCommerceEntryResult> commerceCartModifications = alpsOrderEntryManagementStrategy.getUnavailableProduct(parameters);
		return getFinalResult(commerceCartModifications);
	}

	protected AlpsCommerceResult tryToAddEntryies(AlpsCommercePlaceOrderParameter parameter){
		List<AlpsCommerceEntryResult> commerceCartModifications = alpsOrderEntryManagementStrategy.addEntryToOrder(parameter);
		return getFinalResult(commerceCartModifications);
	}

	protected AlpsCommerceResult getFinalResult(List<AlpsCommerceEntryResult> commerceCartModifications){
		Optional<AlpsCommerceEntryResult> optional = commerceCartModifications.stream().filter(commerceCartModification -> !commerceCartModification.getStatusCode().equals(CommerceCartModificationStatus.SUCCESS)).findAny();
		AlpsCommerceResult commerceOrderResult = new AlpsCommerceResult();
		commerceOrderResult.setSuccess(true);
		if(optional.isPresent()){
			CommerceCartModification commerceCartModification = optional.get();
			commerceOrderResult.setSuccess(false);
			commerceOrderResult.setErrorMessage(commerceCartModification.getStatusCode());
			return commerceOrderResult;
		}
		return commerceOrderResult;
	}

	protected Boolean handleStock(AbstractOrderModel orderModel){
		alpsCommerceStockManagementStrategy.reserveStock(orderModel);
		if(orderModel.getStatus().equals(OrderStatus.CHECKED_INVALID)){
			return false;
		}
		return true;
	}
}
