package com.bp.alps.vehiclecommerceservices.order;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;

import java.util.List;


public interface SaveEntryCallBackWithoutResult
{
	void beforSaveEntry(AbstractOrderEntryModel entryModel, AlpsCommerceOrderEntryParameter entryParameter);
}
