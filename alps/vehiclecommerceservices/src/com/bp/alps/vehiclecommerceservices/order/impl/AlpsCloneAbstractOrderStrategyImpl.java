package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.vehiclecommerceservices.order.AlpsCloneAbstractOrderStrategy;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.order.AbstractOrderEntryTypeService;
import de.hybris.platform.order.strategies.ordercloning.impl.DefaultCloneAbstractOrderStrategy;
import de.hybris.platform.returns.jalo.ReturnEntry;
import de.hybris.platform.returns.model.ReturnEntryModel;
import de.hybris.platform.servicelayer.internal.model.ModelCloningContext;
import de.hybris.platform.servicelayer.internal.model.impl.ItemModelCloneCreator;
import de.hybris.platform.servicelayer.type.TypeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


public class AlpsCloneAbstractOrderStrategyImpl extends DefaultCloneAbstractOrderStrategy implements
		AlpsCloneAbstractOrderStrategy
{
	private final TypeService typeService;
	private final ItemModelCloneCreator itemModelCloneCreator;
	private final AbstractOrderEntryTypeService abstractOrderEntryTypeService;

	public AlpsCloneAbstractOrderStrategyImpl(final TypeService typeService, final ItemModelCloneCreator itemModelCloneCreator,
			final AbstractOrderEntryTypeService abstractOrderEntryTypeService)
	{
		super(typeService, itemModelCloneCreator, abstractOrderEntryTypeService);
		this.typeService = typeService;
		this.itemModelCloneCreator = itemModelCloneCreator;
		this.abstractOrderEntryTypeService = abstractOrderEntryTypeService;
	}

	public <T extends AbstractOrderModel> T cloneWithoutEntry(final ComposedTypeModel _orderType,
			final AbstractOrderModel original, final String code, final Class abstractOrderClassResult)
	{
		validateParameterNotNull(original, "original must not be null!");
		validateParameterNotNull(abstractOrderClassResult, "abstractOrderClassResult must not be null!");

		final ComposedTypeModel orderType = getOrderType(_orderType, original, abstractOrderClassResult);

		final ItemModelCloneCreator.CopyContext copyContext = new ItemModelCloneCreator.CopyContext(new ModelCloningContext(){
			 public boolean skipAttribute(Object var1, String var2){
			 	 if(var2.equals(AbstractOrderModel.ENTRIES)) return true;
			 	 return false;
			 }

			 public boolean treatAsPartOf(Object var1, String var2){
			 	 return false;
			 }

			 public boolean usePresetValue(Object var1, String var2){
				 return false;
			 }

			 public Object getPresetValue(Object var1, String var2){
			 	return null;
			 }
		});

		final T orderClone = (T) itemModelCloneCreator.copy(orderType, original, copyContext);
		if (code != null)
		{
			orderClone.setCode(code);
		}
		postProcessWithoutEntry(original, orderClone);
		return orderClone;
	}

	protected void postProcessWithoutEntry(final AbstractOrderModel original, final AbstractOrderModel copy)
	{
		copyTotalTaxValues(original, copy);
		copyCalculatedFlagWithoutEntry(original, copy);
	}

	protected void copyCalculatedFlagWithoutEntry(final AbstractOrderModel original, final AbstractOrderModel copy)
	{
		copy.setCalculated(original.getCalculated());
	}

	protected  <T extends AbstractOrderModel> ComposedTypeModel getOrderType(final ComposedTypeModel orderType,
			final AbstractOrderModel original, final Class<T> clazz)
	{
		if (orderType != null)
		{
			return orderType;
		}

		if (clazz.isAssignableFrom(original.getClass()))
		{
			return typeService.getComposedTypeForClass(original.getClass());

		}

		return typeService.getComposedTypeForClass(clazz);
	}
}
