package com.bp.alps.vehiclecommerceservices.order;

import de.hybris.platform.returns.model.ReturnRequestModel;


public interface CommerceReturnRequestCalculationStrategy
{
	boolean calculateReturnRequest(final ReturnRequestModel returnRequestModel);
}
