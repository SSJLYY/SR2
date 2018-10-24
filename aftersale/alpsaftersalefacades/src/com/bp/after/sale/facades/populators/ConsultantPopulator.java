package com.bp.after.sale.facades.populators;

import de.hybris.platform.commercefacades.user.converters.populator.CustomerPopulator;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;


public class ConsultantPopulator extends CustomerPopulator implements Populator<CustomerModel, CustomerData>
{
	@Override
	public void populate(final CustomerModel source, final CustomerData target)
	{
		super.populate(source, target);
		source.getGroups().stream()
				.filter(group-> !group.getUid().equals("customergroup"))
				.forEach(group-> target.setGroup(group.getName()));
		target.setMobileNumber(source.getMobileNumber());
		target.setDescription(source.getDescription());
		if(source.getStore()!=null)
		{
			target.setStore(source.getStore().getName());
		}
	}
}
