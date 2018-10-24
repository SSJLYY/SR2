package com.bp.after.sale.facades.populators.increment.output;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.data.IncrementOrderEntryData;
import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;


public class IncrementOrderOutputPopulator implements Populator<IncrementOrderModel, IncrementOrderData>
{
    @Resource
    private Converter<AbstractOrderEntryModel, IncrementOrderEntryData> incrementOrderEntryOutputConverter;

    @Override
    public void populate(IncrementOrderModel source, IncrementOrderData target) throws ConversionException
    {
        target.setTotal(source.getSubtotal());
        if(CollectionUtils.isNotEmpty(source.getEntries()))
        {
            target.setEntries(incrementOrderEntryOutputConverter.convertAll(source.getEntries()));
        }
    }
}
