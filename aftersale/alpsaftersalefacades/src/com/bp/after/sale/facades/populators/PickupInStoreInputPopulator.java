package com.bp.after.sale.facades.populators;

import com.bp.after.sale.facades.data.PickupInStoreData;
import com.bp.alps.after.sale.core.enums.PickupInStoreErrandStatus;
import com.bp.alps.after.sale.core.enums.PickupInStorePurpose;
import com.bp.alps.after.sale.core.enums.PickupInStoreVehicleStatus;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import com.thoughtworks.xstream.converters.ConversionException;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

public class PickupInStoreInputPopulator implements Populator<PickupInStoreData,PickupInStoreModel> {
    @Resource
    private UserService userService;

    @Override
    public void populate(PickupInStoreData source, PickupInStoreModel target) throws ConversionException {
        target.setCode(source.getCode());
        UserModel userModel = null;

        if(StringUtils.isEmpty(source.getServiceConsultant())){
            userModel = userService.getCurrentUser();
        }else{
            String uid = source.getServiceConsultant();
            userModel = userService.getUserForUID(uid);
        }

        if(userModel instanceof CustomerModel)
        {
            target.setServiceConsultant((CustomerModel)userModel);
        }

        if(source.getClosedReason()!=null){
            target.setClosedReason(source.getClosedReason());
            target.setStatus(PickupInStoreErrandStatus.valueOf("close"));
        }

        if(source.getClosedReason()==null&&StringUtils.isNotBlank(source.getStatus())){
            target.setStatus(PickupInStoreErrandStatus.valueOf(source.getStatus()));
        }else if(source.getClosedReason()==null&&StringUtils.isBlank(source.getStatus())){
            target.setStatus(PickupInStoreErrandStatus.valueOf("new"));
        }

        if(source.getPhoneNumber()!=null) target.setPhoneNumber(source.getPhoneNumber());

        if(StringUtils.isNotBlank(source.getPurpose()))  target.setPickupInStorePurpose(PickupInStorePurpose.valueOf(source.getPurpose()));

        if(StringUtils.isNotBlank(source.getVehicleStatus())) target.setVehicleStatus(PickupInStoreVehicleStatus.valueOf(source.getVehicleStatus()));

        target.setArrivalTime(new Date());

        target.setReserve(false);
        if(source.getSender()!=null) target.setSender(source.getSender());
        if(source.getVehicleNumber()!=null) target.setVehicleNumber(source.getVehicleNumber());




    }
}
