package com.bp.alps.vehiclecommerceservices.order;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;

import java.util.List;


public interface AlpsCommerceHandleOrderStrategy
{
	AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter parameter);

	<T extends AbstractOrderModel> T cloneWithoutEntry(final ComposedTypeModel _orderType, final AbstractOrderModel original, final Class abstractOrderClassResult);

	AbstractOrderModel initializeOrderByType(Class orderType);

	AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter parameter);
}
