package com.bp.alps.after.sale.core.serviceorder;

import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import de.hybris.platform.order.CalculationService;
import java.util.*;


public interface ServiceOrderCalculationService extends CalculationService
{
	void calculateReminPrice(ServiceOrderModel serviceOrder, List<ServiceOrderModel> subOrders);
}
