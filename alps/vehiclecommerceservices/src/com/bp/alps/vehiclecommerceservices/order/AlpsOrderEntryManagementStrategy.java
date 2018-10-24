package com.bp.alps.vehiclecommerceservices.order;

import com.bp.alps.core.data.order.AlpsCommerceEntryResult;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import de.hybris.platform.core.model.order.AbstractOrderModel;

import java.util.List;


public interface AlpsOrderEntryManagementStrategy
{
	List<AlpsCommerceEntryResult> updateEntry(AlpsCommercePlaceOrderParameter parameter);

	List<AlpsCommerceEntryResult> addEntryToOrder(AlpsCommercePlaceOrderParameter parameter);

	List<AlpsCommerceEntryResult> getUnavailableProduct(final List<AlpsCommerceOrderEntryParameter> parameterList);

	List<AlpsCommerceOrderEntryParameter> calculateIncrementEntry(AlpsCommercePlaceOrderParameter parameter);
}
