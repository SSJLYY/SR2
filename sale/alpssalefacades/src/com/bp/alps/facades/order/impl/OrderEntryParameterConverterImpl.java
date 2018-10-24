package com.bp.alps.facades.order.impl;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.facades.data.order.OrderInfoData;
import com.bp.alps.facades.populators.abstractOrder.VehicleProductDataPopulator;
import com.bp.alps.vehiclecommercefacade.abstractOrder.impl.AbstractOrderEntryParameterConverterImpl;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;


public class OrderEntryParameterConverterImpl extends AbstractOrderEntryParameterConverterImpl<OrderInfoData>
{
	private static final Logger LOG = LoggerFactory.getLogger(OrderEntryParameterConverterImpl.class);

	@Resource
	private VehicleProductDataPopulator vehicleProductDataPopulator;

	protected void attachmentParameters(AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters){
		commerceOrderEntryParameters.stream().forEach(entry->{
			entry.setOrder(order);
		});
	}

	public OrderEntryParameterConverterImpl(){
		setEntryClass(OrderEntryModel.class);
		setEntryListKeyInData(new String[]{"optionalProduct", "upholsteryProduct", "insuranceProduct", "extendedWarrantyProduct", "coupon"});
	}

	protected void populatorCustomData(final OrderInfoData orderInfoData, List<AlpsCommerceOrderEntryParameter> commerceQuotaionEntryParameters){
		try
		{
			AlpsCommerceOrderEntryParameter commerceOrderEntryParameter = new AlpsCommerceOrderEntryParameter();
			vehicleProductDataPopulator.populate(orderInfoData, commerceOrderEntryParameter);
			if(commerceOrderEntryParameter.getProduct()!=null)
			{
				commerceOrderEntryParameter.setOrderEntryClass(OrderEntryModel.class);
				commerceQuotaionEntryParameters.add(commerceOrderEntryParameter);
			}
		}catch (ConversionException e){
		}
	}
}
