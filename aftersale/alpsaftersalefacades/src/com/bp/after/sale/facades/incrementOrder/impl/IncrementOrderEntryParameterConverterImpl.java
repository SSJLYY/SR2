package com.bp.after.sale.facades.incrementOrder.impl;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.alps.vehiclecommercefacade.abstractOrder.impl.AbstractOrderEntryParameterConverterImpl;
import de.hybris.platform.core.model.order.OrderEntryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IncrementOrderEntryParameterConverterImpl extends AbstractOrderEntryParameterConverterImpl<IncrementOrderData>
{
	private static final Logger LOG = LoggerFactory.getLogger(
			IncrementOrderEntryParameterConverterImpl.class);

	public IncrementOrderEntryParameterConverterImpl(){
		setEntryClass(OrderEntryModel.class);
		setEntryListKeyInData(new String[]{"entries"});
	}
}
