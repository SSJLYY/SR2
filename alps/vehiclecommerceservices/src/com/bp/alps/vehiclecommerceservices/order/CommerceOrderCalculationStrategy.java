package com.bp.alps.vehiclecommerceservices.order;

import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;


public interface CommerceOrderCalculationStrategy extends CommerceCartCalculationStrategy
{
	boolean calculateOrder(final AbstractOrderModel vehicleOrderModel);
}
