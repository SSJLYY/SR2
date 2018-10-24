package com.bp.alps.vehiclecommercefacade.customer;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.facades.data.customer.CustomerSearchResponse;
import com.bp.alps.facades.data.order.CustomerInfoData;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;


public interface AlpsCustomerFacade
{
	CustomerSearchResponse searchCustomer(CustomerSearchRequest customerSearchRequest);

	DefaultResponse createOrUpdateRequest(CustomerInfoData customerInfoData);

	CustomerModel createOrUpdate(CustomerInfoData customerInfoData);

	List<OptionData> getBuyerType();

	List<OptionData> getBuyerCategory();

	List<OptionData> getRole();

	CustomerInfoData getCustomerByUid(String code);
}
