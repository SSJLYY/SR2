package com.bp.alps.vehiclecommerceservices.order;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.CommerceReturnRequestParameter;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.returns.model.ReturnRequestModel;


public interface AlpsCommerceHandleReturnStrategy
{
	AlpsCommerceResult returnProcess(CommerceReturnRequestParameter<AlpsCommerceOrderEntryParameter> parameter);

	ReturnRequestModel initializeReturnRequest(AbstractOrderModel orderModel);

	AlpsCommerceResult updateReturn(CommerceReturnRequestParameter<AlpsCommerceOrderEntryParameter> parameter);
}
