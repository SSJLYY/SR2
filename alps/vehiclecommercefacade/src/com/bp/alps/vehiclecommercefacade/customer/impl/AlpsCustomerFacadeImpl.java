package com.bp.alps.vehiclecommercefacade.customer.impl;

import com.bp.alps.vehiclecommerceservices.enums.BuyerCategory;
import com.bp.alps.vehiclecommerceservices.enums.CustomerRole;
import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import com.bp.alps.vehiclecommercefacade.customer.AlpsCustomerFacade;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.customer.CustomerBaseData;
import com.bp.alps.facades.data.customer.CustomerSearchRequest;
import com.bp.alps.facades.data.customer.CustomerSearchResponse;
import com.bp.alps.facades.data.order.CustomerInfoData;
import com.bp.alps.vehiclecommercefacade.facade.DefaultAlpsCommerceFacade;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


public class AlpsCustomerFacadeImpl extends DefaultAlpsCommerceFacade implements AlpsCustomerFacade
{
	@Resource
	private AlpsCustomerService alpsCustomerService;

	@Resource
	private Converter<CustomerModel, CustomerBaseData> customerBaseDataOutputConverter;

	@Resource(name="enumConverter")
	private Converter<HybrisEnumValue, OptionData> enumerationValueModelConverter;

	@Resource
	private EnumerationService enumerationService;

	@Resource
	private Converter<CustomerInfoData, CustomerModel> customerBaseDataConverter;

	@Resource
	private Converter<CustomerModel, CustomerInfoData> customerOutputDataConverter;

	public List<OptionData> getBuyerType(){
		List<CustomerType> enumValue = enumerationService.getEnumerationValues(CustomerType.class);
		enumValue.remove(CustomerType.GUEST);
		enumValue.remove(CustomerType.REGISTERED);
		return enumerationValueModelConverter.convertAll(enumValue);
	}

	public List<OptionData> getBuyerCategory(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(BuyerCategory.class));
	}

	public List<OptionData> getRole(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(CustomerRole.class));
	}

	public CustomerInfoData getCustomerByUid(String code){
		CustomerModel customerModel = alpsCustomerService.getCustomerForUid(code);
		if(customerModel!=null){
			CustomerInfoData customerInfoData = customerOutputDataConverter.convert(customerModel);
			return customerInfoData;
		}
		return null;
	}

	public CustomerSearchResponse searchCustomer(CustomerSearchRequest customerSearchRequest){
		PageableData pageableData = populatorPageable(customerSearchRequest);
		SearchPageData<CustomerModel> customerModelList = alpsCustomerService.searchCustomer(customerSearchRequest, pageableData);
		CustomerSearchResponse customerSearchResponse = new CustomerSearchResponse();
		if(CollectionUtils.isNotEmpty(customerModelList.getResults()))
		{
			List<CustomerBaseData> customerBaseDataList = customerBaseDataOutputConverter.convertAll(customerModelList.getResults());
			customerSearchResponse.setCustomerList(customerBaseDataList);
			customerSearchResponse.setTotalPage(customerModelList.getPagination().getNumberOfPages());
		}
		customerSearchResponse.setSuccess(true);
		return customerSearchResponse;
	}

	public DefaultResponse createOrUpdateRequest(CustomerInfoData customerInfoData){
		createOrUpdate(customerInfoData);
		DefaultResponse defaultResponse = new DefaultResponse();
		defaultResponse.setSuccess(true);
		return defaultResponse;
	}

	public CustomerModel createOrUpdate(CustomerInfoData customerInfoData){
		CustomerModel customerModel = alpsCustomerService.getCustomerForMobileNumber(customerInfoData.getMobileNumber());
		if(customerModel == null){
			customerModel = alpsCustomerService.createCustomer();
		}
		customerBaseDataConverter.convert(customerInfoData, customerModel);
		alpsCustomerService.saveCustomer(customerModel);
		return customerModel;
	}
}
