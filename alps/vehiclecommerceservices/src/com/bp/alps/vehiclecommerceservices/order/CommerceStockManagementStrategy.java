package com.bp.alps.vehiclecommerceservices.order;

import com.bp.alps.core.data.order.AlpsCommerceEntryResult;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsStockHandleParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;

import java.util.List;


public interface CommerceStockManagementStrategy
{
	void reserveStock(final AbstractOrderModel orderModel);

	void reserveStockByEntry(final AbstractOrderModel orderModel, List<AbstractOrderEntryModel> reserveItem);

	void reserveIncrementStockByHandleResult(final AbstractOrderModel orderModel, final List<AlpsCommerceEntryResult> orderEntryModels);

	void releaseExcessStockByHandleResult(final AbstractOrderModel orderModel, final List<AlpsCommerceEntryResult> orderEntryModels);

	void reserveStock(final AbstractOrderModel orderModel, final List<AlpsStockHandleParameter> alpsStockHandleParameters);

	void releaseStockByEntry(AbstractOrderModel order, List<AbstractOrderEntryModel> needRemoveEntry);
}
