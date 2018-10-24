package com.bp.alps.facades.followopportunity.impl;

import com.bp.alps.core.model.MarketActivityModel;
import com.bp.alps.core.model.type.FollowContentModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.service.FollowOpportunityService;
import com.bp.alps.core.service.OpportunityServices;
import com.bp.alps.facades.data.*;
import com.bp.alps.facades.data.opp.CreatedFollowOpportunityResponse;
import com.bp.alps.facades.data.opp.FollowContentData;
import com.bp.alps.facades.data.opp.FollowOpportunityData;
import com.bp.alps.facades.data.opp.FollowOpportunityListData;
import com.bp.alps.facades.data.quotation.QuotationListResponse;
import com.bp.alps.facades.followopportunity.FollowOpportunityFacade;
import com.bp.alps.facades.quotation.QuotationFacade;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;


public class FollowOpportunityFacadeImpl implements FollowOpportunityFacade
{
	private static final Logger LOGGER = Logger.getLogger(FollowOpportunityFacadeImpl.class);

	@Resource
	private FollowOpportunityService followOpportunityService;

	@Resource
	private OpportunityServices opportunityServices;

	@Resource
	private Converter<MarketActivityModel,MarketActivityData> marketActivityOutputConverter;

	@Resource
	private Converter<FollowOpportunityData,FollowOpportunityModel> followOpportunityInputConverter;

	@Resource
	private Converter<FollowContentData,FollowContentModel> followContentInputConverter;

	@Resource
	private Converter<FollowOpportunityModel,FollowOpportunityListData> followOpportunityListOutputConverter;

	@Resource
	private Converter<FollowOpportunityModel,FollowOpportunityData> followOpportunityOutputConverter;

	@Resource
	private QuotationFacade quotationFacade;

	public DefaultResponse customerConfirmAndSaveInfo(FollowConfirmForm followConfirmFrom){
		DefaultResponse defaultResponse = new DefaultResponse();
		if(StringUtils.isNotBlank(followConfirmFrom.getFollowCode())){
			FollowOpportunityModel followOpportunityModel = followOpportunityService.getFollowOpportunityByCode(followConfirmFrom.getFollowCode());
			if(followOpportunityModel!=null){
				if(followConfirmFrom.getLicense()!=null){
					final MediaModel mediaModel = opportunityServices.saveMediaInLocal(followConfirmFrom.getLicense());
					followOpportunityModel.setLicense(mediaModel);
				}
				followOpportunityModel.setLicenseNumber(followConfirmFrom.getLicenseNumber());
				followOpportunityModel.setCustomerName(followConfirmFrom.getCustomerName());
				followOpportunityModel.setCustomerConfirm(followConfirmFrom.getCustomerConfirm());
				if(followConfirmFrom.getCustomerConfirm())
				{
					opportunityServices.setTestDriveConfrimStatus(followOpportunityModel.getOpportunity());
				}
				followOpportunityService.saveFollowOpportunity(followOpportunityModel);
				defaultResponse.setSuccess(true);
				return defaultResponse;
			}
		}
		defaultResponse.setSuccess(false);
		defaultResponse.setMessageCode("not found");
		return defaultResponse;
	}

	public MarketActivityListResponse getAllMarketActivity(){
		MarketActivityListResponse marketActivityListResponse = new MarketActivityListResponse();
		marketActivityListResponse.setSuccess(true);
		List<MarketActivityData> marketActivityData = marketActivityOutputConverter.convertAll(followOpportunityService.getAllMarketActivity());
		marketActivityListResponse.setMarketActivityList(marketActivityData);
		return marketActivityListResponse;
	}

	public MarketActivityListResponse searchMarketActivity(final String name){
		MarketActivityListResponse marketActivityListResponse = new MarketActivityListResponse();
		marketActivityListResponse.setSuccess(true);
		List<MarketActivityData> marketActivityData = marketActivityOutputConverter.convertAll(followOpportunityService.searchMarketActivity(name));
		marketActivityListResponse.setMarketActivityList(marketActivityData);
		return marketActivityListResponse;
	}

	public CreatedFollowOpportunityResponse saveFollowOpportunity(FollowOpportunitySaveRequest followOpportunitySaveRequest){
		FollowOpportunityModel followOpportunityModel = followOpportunityInputConverter.convert(followOpportunitySaveRequest.getFollowOpportunity());
		OpportunityModel opportunityModel = opportunityServices.getOpportunityByKey(followOpportunitySaveRequest.getOpportunityCode(),null);
		CreatedFollowOpportunityResponse defaultResponse = new CreatedFollowOpportunityResponse();
		if(opportunityModel != null){
			followOpportunityService.saveFollowOpportunity(opportunityModel, followOpportunityModel);
			firstSaveFollowContentByParent(followOpportunitySaveRequest.getFollowOpportunity(), followOpportunityModel);
			defaultResponse.setSuccess(true);
			defaultResponse.setFollowOpportunityCode(followOpportunityModel.getCode());
		}else{
			defaultResponse.setSuccess(false);
			defaultResponse.setMessageCode("error");
		}
		return defaultResponse;
	}

	protected void firstSaveFollowContentByParent(FollowOpportunityData followOpportunityData, FollowOpportunityModel followOpportunityModel){
		if(followOpportunityData.getContentList()!=null && followOpportunityData.getContentList().size()>0){
			FollowContentData followContentData = followOpportunityData.getContentList().get(0);
			saveFollowContentByData(followContentData, followOpportunityModel);
		}
	}

	protected void saveFollowContentByData(FollowContentData followContentData, FollowOpportunityModel followOpportunityModel){
		FollowContentModel followContentModel = followContentInputConverter.convert(followContentData);
		followOpportunityService.saveFollowContent(followContentModel,followOpportunityModel);
	}

	public FollowOpportunityListResponse getFollowOpportunityList(final String opportunityCode){
		OpportunityModel opportunityModel = opportunityServices.getOpportunityByKey(opportunityCode,null);
		FollowOpportunityListResponse followOpportunityListResponse = new FollowOpportunityListResponse();
		if(opportunityModel != null){
			List<FollowOpportunityModel> followOpportunityModels = followOpportunityService.getFollowOpportunityList(opportunityModel);
			List<FollowOpportunityListData> followOpportunityData = followOpportunityListOutputConverter.convertAll(followOpportunityModels);
			followOpportunityListResponse.setSuccess(true);
			followOpportunityListResponse.setFollowOpportunityList(followOpportunityData);
			followOpportunityListResponse.setTotalPage(1);
			return followOpportunityListResponse;
		}

		followOpportunityListResponse.setSuccess(false);
		followOpportunityListResponse.setMessageCode("not_found");
		return followOpportunityListResponse;
	}

	public FollowOpportunityResponse getFollowOpportunityByCode(final String code){
		FollowOpportunityResponse followOpportunityResponse = new FollowOpportunityResponse();
		FollowOpportunityModel followOpportunityModel = followOpportunityService.getFollowOpportunityByCode(code);
		if(followOpportunityModel!=null)
		{
			FollowOpportunityData followOpportunityData = followOpportunityOutputConverter.convert(followOpportunityModel);
			followOpportunityResponse.setSuccess(true);
			followOpportunityResponse.setFollowOpportunity(followOpportunityData);
			QuotationListResponse quotationListResponse = quotationFacade.getQuotationList( null, followOpportunityData.getCode());
			followOpportunityResponse.setQuotationList(quotationListResponse.getQuotationList());
			return followOpportunityResponse;
		}

		followOpportunityResponse.setSuccess(false);
		followOpportunityResponse.setMessageCode("not_found");
		return followOpportunityResponse;
	}

	public FollowContentListResponse appendFollowContent(FollowContentSaveRequest followContentSaveRequest){
		FollowContentListResponse followContentListResponse = new FollowContentListResponse();
		FollowOpportunityModel followOpportunityModel = followOpportunityService.getFollowOpportunityByCode(followContentSaveRequest.getFollowOpportunityCode());
		if(followOpportunityModel!=null)
		{
			FollowContentData followContentData = followContentSaveRequest.getFollowContentData();
			saveFollowContentByData(followContentData, followOpportunityModel);
			followContentListResponse.setSuccess(true);
			followContentListResponse.setMessageCode("");
			return followContentListResponse;
		}

		followContentListResponse.setSuccess(false);
		followContentListResponse.setMessageCode("not_found");
		return followContentListResponse;
	}
}
