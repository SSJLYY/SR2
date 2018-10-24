package com.bp.alps.vehiclecommercefacade.abstractOrder;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.core.model.order.AbstractOrderModel;

import java.util.List;


public interface AbstractOrderEntryParameterConverter<T>
{
	List<AlpsCommerceOrderEntryParameter> converter(T abstractOrderData, AbstractOrderModel order);
}
