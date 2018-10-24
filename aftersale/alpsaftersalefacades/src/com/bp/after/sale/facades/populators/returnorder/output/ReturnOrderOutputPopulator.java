package com.bp.after.sale.facades.populators.returnorder.output;

import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.data.IncrementOrderEntryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.returns.model.ReturnEntryModel;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;


public class ReturnOrderOutputPopulator implements Populator<ReturnRequestModel, IncrementOrderData>
{
    @Resource
    private Converter<ReturnEntryModel, IncrementOrderEntryData> returnOrderEntryOutputConverter;

    @Override
    public void populate(ReturnRequestModel source, IncrementOrderData target) throws ConversionException
    {
        target.setReturnOrderCode(source.getCode());
        target.setTotal(source.getSubtotal().doubleValue());
        if(CollectionUtils.isNotEmpty(source.getReturnEntries()))
        {
            target.setEntries(returnOrderEntryOutputConverter.convertAll(source.getReturnEntries()));
        }
    }
}
