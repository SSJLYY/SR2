package com.bp.alps.facades.populators.order;

import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import com.bp.alps.vehiclecommercefacade.customer.AlpsCustomerFacade;
import com.bp.alps.facades.data.order.CustomerInfoData;
import com.bp.alps.facades.data.order.OrderInfoData;
import com.bp.alps.vehiclecommercefacade.populators.customer.CustomerBaseDataPopulaor;
import de.hybris.platform.converters.Populator;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;


public class CustomerDataPopulator implements Populator<OrderInfoData, VehicleOrderModel>
{
	@Resource
	private CustomerBaseDataPopulaor customerBaseDataPopulaor;

	@Resource
	private AlpsCustomerService alpsCustomerService;

	@Resource
	private AlpsCustomerFacade alpsCustomerFacade;

	@Override
	public void populate(OrderInfoData source, VehicleOrderModel target)
			throws ConversionException
	{
		final CustomerInfoData customerInfoData = source.getCustomer();
		if(customerInfoData!=null)
		{
			if (StringUtils.isNotBlank(customerInfoData.getUid()) && StringUtils.isBlank(customerInfoData.getMobileNumber()))
			{
				CustomerModel customerModel = alpsCustomerService.getCustomerForUid(customerInfoData.getUid());
				if (customerModel != null)
				{
					target.setUser(customerModel);
				}
			}

			if (StringUtils.isNotBlank(customerInfoData.getMobileNumber()))
			{
				CustomerModel customerModel = alpsCustomerFacade.createOrUpdate(customerInfoData);
				if (customerModel != null)
				{
					target.setUser(customerModel);
				}
			}
		}
		target.setConsultant(alpsCustomerService.getCurrentSalesConsultant());
	}
}
