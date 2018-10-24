package com.bp.alps.facades.opportunity.impl;

import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.service.OpportunityServices;
import com.bp.alps.core.service.PlatFormService;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.OpportunityDetailResponse;
import com.bp.alps.facades.data.OpportunityListRequest;
import com.bp.alps.facades.data.OpportunityListResponse;
import com.bp.alps.facades.data.opp.OpportunityData;
import com.bp.alps.facades.data.opp.OpportunityListData;
import com.bp.alps.facades.facade.DefaultAlpsSalesFacade;
import com.bp.alps.facades.opportunity.OpportunityFacade;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;


public class OpportunityFacadeImpl extends DefaultAlpsSalesFacade implements OpportunityFacade
{
	@Resource
	private Converter<OpportunityData,OpportunityModel> opportunityInputConverter;

	@Resource
	private Converter<OpportunityModel,OpportunityData> opportunityOutputConverter;

	@Resource
	private Converter<OpportunityModel,OpportunityListData> opportunityListOutputConverter;

	@Resource
	private OpportunityServices opportunityServices;

	@Resource
	private UserService userService;

	@Resource
	private PlatFormService platFormService;

	public DefaultResponse saveOpportunity(OpportunityData opportunityData){
		DefaultResponse defaultResponse = new DefaultResponse();
		OpportunityModel opportunityModel;
		if(StringUtils.isEmpty(opportunityData.getCode()))
		{
			opportunityModel = opportunityInputConverter.convert(opportunityData);
			if(opportunityModel.getPlatform()==null){
				opportunityModel.setPlatform(platFormService.getInStoreModel());
			}
		}else {
			opportunityModel = opportunityServices.getOpportunityByKey(opportunityData.getCode(),null);
			opportunityModel = opportunityInputConverter.convert(opportunityData, opportunityModel);
		}
		try{
			opportunityServices.saveOpportunity(opportunityModel);
			defaultResponse.setSuccess(true);
		}catch (Exception e){
			defaultResponse.setSuccess(false);
			defaultResponse.setMessage(e.getMessage());
		}
		return defaultResponse;
	}

	public OpportunityDetailResponse getOpportunityByKey(final String code, final String mobile){
		OpportunityDetailResponse opportunityDetailReponse = new OpportunityDetailResponse();
		if(StringUtils.isBlank(code)&&StringUtils.isBlank(mobile)){
			opportunityDetailReponse.setSuccess(false);
			opportunityDetailReponse.setMessageCode("not_found");
			return opportunityDetailReponse;
		}
		OpportunityModel opportunityModel = opportunityServices.getOpportunityByKey(code, mobile);

		if(opportunityModel!=null){
			OpportunityData opportunityData = opportunityOutputConverter.convert(opportunityModel);
			opportunityDetailReponse.setOpportunityList(Collections.singletonList(opportunityData));
			opportunityDetailReponse.setSuccess(true);
		}else{
			opportunityDetailReponse.setSuccess(false);
			opportunityDetailReponse.setMessageCode("not_found");
		}
		return opportunityDetailReponse;
	}

	@Override
	public OpportunityListResponse getOpportunityList(final OpportunityListRequest opportunityListRequest)
	{
		OpportunityListResponse opportunityListResponse = new OpportunityListResponse();
		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel){
			final PageableData pageableData = populatorPageable(opportunityListRequest);

			SearchPageData<OpportunityModel> searchPageData = opportunityServices.getOpportunityBySales((CustomerModel) userModel, opportunityListRequest.getMobile(), opportunityListRequest.getLevel(), pageableData);
			if(CollectionUtils.isNotEmpty(searchPageData.getResults()))
			{
				List<OpportunityListData> opportunityListData = opportunityListOutputConverter.convertAll(searchPageData.getResults());
				opportunityListResponse.setOpportunityList(opportunityListData);
				opportunityListResponse.setTotalPage(searchPageData.getPagination() != null ? searchPageData.getPagination().getNumberOfPages() : 0);
			}
			opportunityListResponse.setSuccess(true);
			return opportunityListResponse;
		}

		opportunityListResponse.setSuccess(false);
		opportunityListResponse.setMessageCode("not_found");
		return opportunityListResponse;
	}
}
