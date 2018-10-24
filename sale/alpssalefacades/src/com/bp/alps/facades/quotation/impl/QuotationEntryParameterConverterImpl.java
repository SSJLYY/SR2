package com.bp.alps.facades.quotation.impl;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.vehiclecommercefacade.abstractOrder.impl.AbstractOrderEntryParameterConverterImpl;
import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.facades.order.impl.OrderEntryParameterConverterImpl;
import com.bp.alps.facades.populators.abstractOrder.VehicleProductDataPopulator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartEntryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;


public class QuotationEntryParameterConverterImpl extends AbstractOrderEntryParameterConverterImpl<QuotationData>
{
	private static final Logger LOG = LoggerFactory.getLogger(OrderEntryParameterConverterImpl.class);

	@Resource
	private VehicleProductDataPopulator vehicleProductDataPopulator;

	public QuotationEntryParameterConverterImpl(){
		setEntryClass(CartEntryModel.class);
		setEntryListKeyInData(new String[]{"optionalProduct", "upholsteryProduct", "insuranceProduct", "extendedWarrantyProduct", "coupon"});
	}

	protected void attachmentParameters(AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters){
		commerceOrderEntryParameters.stream().forEach(entry->{
			entry.setOrder(order);
		});
	}

	@Override
	protected void populatorCustomData(final QuotationData quotationData, List<AlpsCommerceOrderEntryParameter> commerceQuotaionEntryParameters){
		try
		{
			AlpsCommerceOrderEntryParameter commerceOrderEntryParameter = new AlpsCommerceOrderEntryParameter();
			vehicleProductDataPopulator.populate(quotationData, commerceOrderEntryParameter);
			if(commerceOrderEntryParameter.getProduct()!=null)
			{
				commerceOrderEntryParameter.setOrderEntryClass(CartEntryModel.class);
				commerceQuotaionEntryParameters.add(commerceOrderEntryParameter);
			}
		}catch (ConversionException e){
		}
	}
}
