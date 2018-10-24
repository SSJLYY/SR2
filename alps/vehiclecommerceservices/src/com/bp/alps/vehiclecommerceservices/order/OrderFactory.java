package com.bp.alps.vehiclecommerceservices.order;

import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;


public interface OrderFactory
{
	<T extends AbstractOrderModel> T cloneWithoutEntry(final ComposedTypeModel _orderType, final AbstractOrderModel original, final Class abstractOrderClassResult);

	AbstractOrderModel initializeOrderByType(Class orderType);
}
