package com.bp.alps.vehiclecommerceservices.order;

import com.bp.alps.vehiclecommerceservices.model.ReturnEntryRecordModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.returns.model.ReturnRequestModel;


public interface ReturnRequestFactory
{
	ReturnRequestModel initializeReturnRequest(AbstractOrderModel orderModel);

	ReturnEntryRecordModel initializeReturnEntry(AbstractOrderEntryModel entryModel);
}
