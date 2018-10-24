package com.bp.alps.after.sale.core.serviceorder;

import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.SplitServiceOrderData;

import java.util.List;


public interface AlpsSplitOrderStrategy
{
	List<AlpsCommerceResult> splitOrder(List<SplitServiceOrderData> splitServiceOrderData, ServiceOrderModel defaultModel);
}
