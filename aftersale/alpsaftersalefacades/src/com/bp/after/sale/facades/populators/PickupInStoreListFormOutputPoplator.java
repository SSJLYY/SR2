package com.bp.after.sale.facades.populators;

import com.bp.after.sale.facades.data.aftersales.PickupInStoreListData;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PickupInStoreListFormOutputPoplator implements Populator<PickupInStoreModel,PickupInStoreListData> {

    @Override
    public void populate(PickupInStoreModel source, PickupInStoreListData target) throws ConversionException {

        Date arrivalTime = source.getArrivalTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String arrivalTimeString = formatter.format(arrivalTime);
        target.setArrivalTime(arrivalTimeString);

    }
}
