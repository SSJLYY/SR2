package com.bp.alps.vehiclecommercefacade.abstractOrder;

import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.core.model.order.AbstractOrderModel;


public interface AbstractOrderEntryOutputConverter<T>
{
	void converter(AbstractOrderModel abstractOrderModel, T abstractOrderData);
}
