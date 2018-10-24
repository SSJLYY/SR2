package com.bp.alps.vehiclecommerceservices.order;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.order.strategies.ordercloning.CloneAbstractOrderStrategy;
import de.hybris.platform.returns.model.ReturnEntryModel;


public interface AlpsCloneAbstractOrderStrategy extends CloneAbstractOrderStrategy
{
	<T extends AbstractOrderModel> T cloneWithoutEntry(ComposedTypeModel orderType, AbstractOrderModel original, String code, final Class abstractOrderClassResult);
}
