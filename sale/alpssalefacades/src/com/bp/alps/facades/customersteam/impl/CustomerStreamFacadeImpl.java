package com.bp.alps.facades.customersteam.impl;

import com.bp.alps.core.enums.CustomerStreamStatus;
import com.bp.alps.core.enums.CustomerStreamType;
import com.bp.alps.core.model.type.CustomerStreamModel;
import com.bp.alps.core.service.CustomerSteamService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.customersteam.CustomerStreamFacade;
import com.bp.alps.facades.data.CustomerFlowData;
import com.bp.alps.facades.data.CustomerStreamDetail;
import com.bp.alps.facades.data.CustomerStreamListResponse;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.facade.DefaultAlpsSalesFacade;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.Gender;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


public class CustomerStreamFacadeImpl extends DefaultAlpsSalesFacade implements CustomerStreamFacade
{
	@Resource
	private Converter<CustomerFlowData,CustomerStreamModel> customerSteamInputConverter;

	@Resource
	private CustomerSteamService customerSteamService;

	@Resource
	private DateFormatUtils dateFormatUtils;

	@Resource
	private UserService userService;

	@Resource
	private Converter<CustomerStreamModel,CustomerFlowData> customerSteamOutputConverter;

	@Resource
	private EnumerationService enumerationService;

	//EnumerationValueModel
	@Resource(name="enumConverter")
	private Converter<HybrisEnumValue,OptionData> enumerationValueModelConverter;

	public List<OptionData> getTypes(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(CustomerStreamType.class));
	}

	public List<OptionData> getAllStatus(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(CustomerStreamStatus.class));
	}

	public List<OptionData> getGenders(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(Gender.class));
	}

	public DefaultResponse saveCustomerSteam(final CustomerFlowData customerFlowData)
	{
		DefaultResponse defaultResponse = new DefaultResponse();

			if(StringUtils.isEmpty(customerFlowData.getId())){
				CustomerStreamModel customerStreamModel = customerSteamInputConverter.convert(customerFlowData);
				UserModel userModel = userService.getCurrentUser();
				if(userModel instanceof CustomerModel)
				{
					customerStreamModel.setCreatedUser((CustomerModel)userModel);
				}
				try
				{
					customerStreamModel = customerSteamService.createCustomerSteam(customerStreamModel);
				}catch (Exception e){
					defaultResponse.setSuccess(false);
					defaultResponse.setMessage(e.getMessage());
					return defaultResponse;
				}
			}else{
				final List<CustomerStreamModel> customerStreamModels = customerSteamService.getCustomerStreamByCode(customerFlowData.getId());
				CustomerStreamModel customerStreamModel = customerSteamInputConverter.convert(customerFlowData,customerStreamModels.get(0));
				try
				{
					customerStreamModel = customerSteamService.updateCustomerSteam(customerStreamModel);
				}catch (Exception e){
					defaultResponse.setSuccess(false);
					defaultResponse.setMessage(e.getMessage());
					return defaultResponse;
				}
			}
			defaultResponse.setSuccess(true);
		return defaultResponse;
	}

	public CustomerStreamListResponse getTodayCustomerStreamByCUser(final Integer currentPage, Integer pagesize){
		CustomerStreamListResponse customerStreamListResponse = new CustomerStreamListResponse();
		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel){
			final PageableData pageableData = populatorPageable(currentPage, pagesize);
			SearchPageData<CustomerStreamModel> streamModelSearchPageData = customerSteamService.getCustomerStreamByUserBeforDate((CustomerModel)userModel, dateFormatUtils.getToday(), pageableData);
			List<CustomerFlowData> customerFlowDataList = customerSteamOutputConverter.convertAll(streamModelSearchPageData.getResults());
			customerStreamListResponse.setCustomerFlowList(customerFlowDataList);
			customerStreamListResponse.setTotalPage(streamModelSearchPageData.getPagination().getNumberOfPages());
			customerStreamListResponse.setSuccess(true);
		}else{
			customerStreamListResponse.setSuccess(false);
			customerStreamListResponse.setMessageCode("invalid.user");
		}
		return customerStreamListResponse;
	}

	public CustomerStreamDetail getCustomerStreamByCode(String code){
		CustomerStreamDetail customerStreamDetail = new CustomerStreamDetail();
		List<CustomerStreamModel> customerStreamModels = customerSteamService.getCustomerStreamByCode(code);
		List<CustomerFlowData> customerFlowDataList = customerSteamOutputConverter.convertAll(customerStreamModels);
		customerStreamDetail.setCustomerFlowList(customerFlowDataList);
		if(customerFlowDataList.size()>0)
		{
			customerStreamDetail.setTotalPage(1);
		}else {
			customerStreamDetail.setTotalPage(0);
		}
		customerStreamDetail.setSuccess(true);
		return customerStreamDetail;
	}
}
