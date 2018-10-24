package com.bp.after.sale.facades.populators.increment;


import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;


public class IncrementOrderPopulator implements Populator<IncrementOrderData, IncrementOrderModel>
{
    @Resource
    private UserService userService;

    @Resource
    private VehicleService vehicleService;

    @Override
    public void populate(IncrementOrderData source, IncrementOrderModel target) throws ConversionException
    {
        if(source.getCustomer()!=null && StringUtils.isNotBlank(source.getCustomer().getUid()))
        {
            target.setUser(userService.getUserForUID(source.getCustomer().getUid()));
        }
        if(source.getVehicle()!=null && StringUtils.isNotBlank(source.getVehicle().getCode()))
        {
            target.setVehicle(vehicleService.getVehicleByCode(source.getVehicle().getCode()));
        }
        if(StringUtils.isNotBlank(source.getStatusCode())) target.setStatus(OrderStatus.valueOf(source.getStatusCode()));
    }
}
