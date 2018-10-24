package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.vehiclecommerceservices.order.CommerceReturnRequestCalculationStrategy;
import de.hybris.platform.returns.model.ReturnEntryModel;
import de.hybris.platform.returns.model.ReturnRequestModel;

import java.math.BigDecimal;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


public class CommerceReturnRequestCalculationStrategyImpl implements CommerceReturnRequestCalculationStrategy
{
	@Override
	public boolean calculateReturnRequest(ReturnRequestModel returnRequestModel)
	{
		validateParameterNotNull(returnRequestModel, "order model cannot be null");

		calculateEntries(returnRequestModel);
		return true;
	}

	public void calculateEntries(final ReturnRequestModel returnRequestModel)
	{
		BigDecimal subtotal = new BigDecimal("0");
		for (final ReturnEntryModel e : returnRequestModel.getReturnEntries())
		{
			if(e.getOrderEntry()!=null)
			{
				BigDecimal price = BigDecimal.valueOf(e.getOrderEntry().getActualPrice());
				BigDecimal quantity = BigDecimal.valueOf(e.getReceivedQuantity());
				subtotal.add(price.multiply(quantity));
			}
		}
		returnRequestModel.setSubtotal(subtotal);
	}
}
