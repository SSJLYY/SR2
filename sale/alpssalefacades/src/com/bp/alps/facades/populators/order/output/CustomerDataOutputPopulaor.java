package com.bp.alps.facades.populators.order.output;

import com.bp.alps.core.model.VehicleOrderModel;
import com.bp.alps.facades.data.order.CustomerInfoData;
import com.bp.alps.facades.data.order.OrderInfoData;
import com.bp.alps.vehiclecommercefacade.populators.customer.output.CustomerBaseDataOutputPopulaor;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class CustomerDataOutputPopulaor implements Populator<VehicleOrderModel, OrderInfoData>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private CustomerBaseDataOutputPopulaor customerBaseDataOutputPopulaor;

	public void populate(VehicleOrderModel orderModel, OrderInfoData orderInfoData) throws ConversionException
	{
		orderInfoData.setLineItemName(orderModel.getDescription());
		final UserModel userModel = orderModel.getUser();
		if(userModel instanceof CustomerModel){
			CustomerModel customerModel = (CustomerModel)userModel;
			CustomerInfoData customerInfoData = new CustomerInfoData();
			customerBaseDataOutputPopulaor.populate(customerModel, customerInfoData);
			orderInfoData.setCustomer(customerInfoData);
		}
	}
}
