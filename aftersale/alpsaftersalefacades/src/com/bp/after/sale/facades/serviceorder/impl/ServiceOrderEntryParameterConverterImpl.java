package com.bp.after.sale.facades.serviceorder.impl;

import com.bp.after.sale.facades.data.ServiceOrderData;
import com.bp.alps.after.sale.core.model.ServiceOrderEntryModel;
import com.bp.alps.vehiclecommercefacade.abstractOrder.impl.AbstractOrderEntryParameterConverterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServiceOrderEntryParameterConverterImpl extends AbstractOrderEntryParameterConverterImpl<ServiceOrderData>
{
	private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderEntryParameterConverterImpl.class);

	public ServiceOrderEntryParameterConverterImpl(){
		setEntryClass(ServiceOrderEntryModel.class);
		setEntryListKeyInData(new String[]{"entries"});
	}
}
