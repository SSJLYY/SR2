package com.bp.alps.vehiclecommerceservices.order;

import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;


public interface AlpsCommerceEntryModificationStatus extends CommerceCartModificationStatus
{
	String ADD_QUANTITY = "successAddQuantity";

	String REDUCE_QUANTITY = "successReduceQuantity";

	String CHANGE_ATTRIBUTES = "successChangeAttributes";
}
