package com.bp.after.sale.facades.populators;

import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import com.bp.after.sale.facades.data.aftersales.PickupInStoreListData;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PickupInStoreListOutputPoplator implements Populator<PickupInStoreModel,PickupInStoreListData> {
    @Resource
    private EnumerationService enumerationService;

    @Override
    public void populate(PickupInStoreModel source, PickupInStoreListData target) throws ConversionException {

        target.setCode(source.getCode());
        target.setServiceConsultant(source.getServiceConsultant()!=null?source.getServiceConsultant().getUid():null);

        Date arrivalTime = source.getArrivalTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String arrivalTimeString = formatter.format(arrivalTime);
        target.setArrivalTime(arrivalTimeString);

        target.setClosedReason(source.getClosedReason()!=null?source.getClosedReason():"");
        target.setPhoneNumber(source.getPhoneNumber()!=null?source.getPhoneNumber():"");
        target.setPurpose(source.getPickupInStorePurpose()!=null?source.getPickupInStorePurpose().getCode():null);
        target.setReserve(source.getReserve()!=null?source.getReserve():false);
        target.setSender(source.getSender() != null ? source.getSender() : "");
        if(source.getStatus()!=null)
        {

            target.setStatusCode(source.getStatus().getCode());
            target.setStatus(enumerationService.getEnumerationName(source.getStatus()));
        }
        target.setVehicleNumber(source.getVehicleNumber()!=null?source.getVehicleNumber():"");
        target.setVehicleStatus(source.getVehicleStatus()!=null?source.getVehicleStatus().getCode():null);
        if(source.getWorkOrder()!=null) target.setWorkOrderNumber(source.getWorkOrder().getCode());
        if(source.getWorkOrder()!=null && source.getWorkOrder().getStatus()!=null) target.setWorkOrderStatus(enumerationService.getEnumerationName(source.getWorkOrder().getStatus()));

    }
}
