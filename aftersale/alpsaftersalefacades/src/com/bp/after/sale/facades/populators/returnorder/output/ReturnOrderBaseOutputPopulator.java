package com.bp.after.sale.facades.populators.returnorder.output;

import com.bp.after.sale.facades.IncrementOrderBaseData;
import com.bp.alps.facades.data.customer.CustomerBaseData;
import com.bp.alps.facades.data.vehicle.VehicleInfoBaseData;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.returns.model.ReturnOrderModel;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;


public class ReturnOrderBaseOutputPopulator implements Populator<ReturnRequestModel, IncrementOrderBaseData>
{
    @Resource
    private UserService userService;

    @Resource
    private VehicleService vehicleService;

    @Resource
    private EnumerationService enumerationService;

    @Override
    public void populate(ReturnRequestModel source, IncrementOrderBaseData target) throws ConversionException
    {
        target.setCode(source.getCode());
        if(source.getVehicle()!=null)
        {
            VehicleInfoBaseData vehicleInfoBaseData = new VehicleInfoBaseData();
            vehicleInfoBaseData.setLicensePlateNumber(source.getVehicle().getLicensePlateNumber());
            vehicleInfoBaseData.setCode(source.getVehicle().getCode());
            target.setVehicle(vehicleInfoBaseData);
        }

        if(source.getUser()!=null && source.getUser() instanceof CustomerModel)
        {
            CustomerModel customer = (CustomerModel)source.getUser();
            CustomerBaseData customerBaseData = new CustomerBaseData();
            customerBaseData.setUid(customer.getUid());
            customerBaseData.setName(customer.getName());
            customerBaseData.setMobileNumber(customer.getMobileNumber());
            target.setCustomer(customerBaseData);
        }

        if(source.getConsultant()!=null && source.getConsultant() instanceof CustomerModel)
        {
            CustomerModel customer = (CustomerModel)source.getConsultant();
            CustomerBaseData customerBaseData = new CustomerBaseData();
            customerBaseData.setName(customer.getName());
            customerBaseData.setMobileNumber(customer.getMobileNumber());
            target.setServiceConsultant(customerBaseData);
        }

        target.setStatus(enumerationService.getEnumerationName(source.getStatus()));
        target.setStatusCode(source.getStatus().getCode());
        target.setCreationtime(DateFormatUtils.getDateString("date", source.getCreationtime()));
    }
}
