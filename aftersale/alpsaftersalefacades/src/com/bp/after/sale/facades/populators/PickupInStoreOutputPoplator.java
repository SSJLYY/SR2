package com.bp.after.sale.facades.populators;

import com.bp.after.sale.facades.data.PickupInStoreData;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickupInStoreOutputPoplator implements Populator<PickupInStoreModel,PickupInStoreData>
{
    @Resource
    private EnumerationService enumerationService;

    @Override
    public void populate(PickupInStoreModel source, PickupInStoreData target) throws ConversionException {

        Date arrivalTime = source.getArrivalTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String arrivalTimeString = formatter.format(arrivalTime);
        target.setArrivalTime(arrivalTimeString);

        target.setCode(source.getCode());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setSender(source.getSender());
        target.setVehicleNumber(source.getVehicleNumber());
        target.setClosedReason(source.getClosedReason());
        target.setCode(source.getCode());
        target.setPurpose(source.getPickupInStorePurpose()!=null?source.getPickupInStorePurpose().getCode():null);
        target.setReserve(source.getReserve());
        target.setServiceConsultant(source.getServiceConsultant()!=null?source.getServiceConsultant().getUid():null);
        if(source.getStatus()!=null)
        {
            target.setStatusCode(source.getStatus().getCode());
            target.setStatus(enumerationService.getEnumerationName(source.getStatus()));
        }
        target.setVehicleStatus(source.getVehicleStatus()!=null?source.getVehicleStatus().getCode():null);
        if(source.getWorkOrder()!=null) target.setWorkOrderNumber(source.getWorkOrder().getCode());
        if(source.getWorkOrder()!=null && source.getWorkOrder().getStatus()!=null) target.setWorkOrderStatus(enumerationService.getEnumerationName(source.getWorkOrder().getStatus()));


    }




}
