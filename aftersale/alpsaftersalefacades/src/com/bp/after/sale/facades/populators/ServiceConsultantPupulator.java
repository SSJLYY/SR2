package com.bp.after.sale.facades.populators;

import com.bp.after.sale.facades.data.ServiceConsultantData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class ServiceConsultantPupulator implements Populator<CustomerModel,ServiceConsultantData>
{
    @Override
    public void populate(CustomerModel source, ServiceConsultantData target) throws ConversionException {
        target.setCustomerId(source.getUid());
        target.setCustomerName(source.getName());
        target.setNofPickupToday(source.getNOfPickupToday()!=null?source.getNOfPickupToday():0);
        target.setCurrentWaitingVehicles(source.getCurrentWaitingVehicles()!=null?source.getCurrentWaitingVehicles():0);
    }
}
