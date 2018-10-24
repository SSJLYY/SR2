package com.bp.after.sale.facades.populators.serviceorder;

import com.bp.after.sale.facades.data.ServiceOrderData;
import com.bp.alps.after.sale.core.enums.*;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;


public class ServiceOrderPopulator implements Populator<ServiceOrderData, ServiceOrderModel> {
    @Resource
    private UserService userService;

    @Resource
    private VehicleService vehicleService;

    @Override
    public void populate(ServiceOrderData source, ServiceOrderModel target) throws ConversionException {
        if(source.getVehicle()!=null && StringUtils.isNoneBlank(source.getVehicle().getCode())){
            VehicleInfoModel vehicleInfoModel = vehicleService.getVehicleByCode(source.getVehicle().getCode());
            target.setVehicle(vehicleInfoModel);
        }

        if(StringUtils.isNoneBlank(source.getServiceTypeCode()))
        {
            target.setServiceType(ServiceOrderType.valueOf(source.getServiceTypeCode()));
        }

        if(source.getSender()!=null && StringUtils.isNoneBlank(source.getSender().getUid())){
            UserModel userModel = userService.getUserForUID(source.getSender().getUid());
            if(userModel instanceof CustomerModel){
                target.setUser((CustomerModel)userModel);
            }
        }

        if(StringUtils.isNoneBlank(source.getEstimatedDeliveryTime())) target.setEstimatedDeliveryTime(DateFormatUtils.getDate("date", source.getEstimatedDeliveryTime()));

        if(source.getMileageInFactory()!=null) target.setMileageInFactory(source.getMileageInFactory());

        if(source.getStatusCode()!=null) target.setStatus(OrderStatus.valueOf(source.getStatusCode()));
    }
}
