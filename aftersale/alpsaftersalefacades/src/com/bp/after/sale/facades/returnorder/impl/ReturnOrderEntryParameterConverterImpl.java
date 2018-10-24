package com.bp.after.sale.facades.returnorder.impl;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.data.IncrementOrderEntryData;
import com.bp.alps.after.sale.core.model.ReturnRecordOrderEntryModel;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.vehiclecommercefacade.abstractOrder.impl.AbstractOrderEntryParameterConverterImpl;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;


public class ReturnOrderEntryParameterConverterImpl extends AbstractOrderEntryParameterConverterImpl<IncrementOrderData>
{
	private static final Logger LOG = LoggerFactory.getLogger(
			ReturnOrderEntryParameterConverterImpl.class);

	public ReturnOrderEntryParameterConverterImpl(){
		setEntryClass(ReturnRecordOrderEntryModel.class);
		setEntryListKeyInData(new String[]{"entries"});
	}

	public List<AlpsCommerceOrderEntryParameter> converter(IncrementOrderData abstractOrderData, AbstractOrderModel order)
	{
		setEntryClass(ReturnRecordOrderEntryModel.class);
		return super.converter(abstractOrderData, order);
	}
}
