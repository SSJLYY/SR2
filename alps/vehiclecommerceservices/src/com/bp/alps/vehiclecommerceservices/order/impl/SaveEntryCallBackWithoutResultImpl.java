package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.vehiclecommerceservices.order.SaveEntryCallBackWithoutResult;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;

import java.util.List;

public abstract class SaveEntryCallBackWithoutResultImpl implements SaveEntryCallBackWithoutResult
{
	public abstract void beforSaveEntry(List<AbstractOrderEntryModel> AbstractOrderEntryModels);
}
