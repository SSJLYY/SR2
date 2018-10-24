package com.bp.alps.facades.quotation.impl;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.model.StoreVoucherDescriptionModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.core.service.StoreVoucherService;
import com.bp.alps.vehiclecommercefacade.abstractOrder.AbstractOrderEntryOutputConverter;
import com.bp.alps.vehiclecommercefacade.abstractOrder.AbstractOrderEntryParameterConverter;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.quotation.*;
import com.bp.alps.facades.data.voucher.StoreVoucherData;
import com.bp.alps.facades.quotation.QuotationFacade;
import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.commerceservices.order.CommerceCartCalculationStrategy;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.media.MediaModel;
import com.bp.alps.core.model.QuotationInfoModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.site.BaseSiteService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;


public class QuotationFacadeImpl implements QuotationFacade
{
	private static final Logger LOGGER = Logger.getLogger(QuotationFacadeImpl.class);

	@Resource
	private AbstractOrderEntryParameterConverter<QuotationData> quotationEntryParameterConverter;

	@Resource
	private QuotationService quotationService;

	@Resource
	private Converter<QuotationData, QuotationInfoModel> quotationInfoConverter;

	@Resource
	private StoreVoucherService storeVoucherService;

	@Resource
	private CommerceCartCalculationStrategy commerceCartCalculationStrategy;

	@Resource
	private AbstractOrderEntryOutputConverter<QuotationData> defaultEntryOutputConverter;

	@Resource
	private Converter<QuotationInfoModel,QuotationData> quotationInfoOutputConverter;

	@Resource
	private Converter<StoreVoucherData, StoreVoucherDescriptionModel> voucherInputForQuotationConverter;

	@Resource
	private Converter<QuotationInfoModel, QuotationListData> quotationListDataConverter;

	@Resource
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;

	@Resource
	private EnumerationService enumerationService;

	@Resource
	private BaseSiteService baseSiteService;

	public SendCustomerToConfirmResponse sendCustomerToConfirm(final String quotationCode){
		SendCustomerToConfirmResponse sendCustomerToConfirmResponse = new SendCustomerToConfirmResponse();
		QuotationInfoModel quotationInfoModel = quotationService.getQuotationInfoByCode(quotationCode);
		if(quotationInfoModel!=null)
		{
			quotationInfoModel.setStatus(OrderStatus.SNEDTOCUSTOMER);
			quotationService.saveQuoaion(quotationInfoModel);
			sendCustomerToConfirmResponse.setShareUrl(siteBaseUrlResolutionService
					.getWebsiteUrlForSite(baseSiteService.getCurrentBaseSite(), true, "/quotation/confirmation",
							"quotationCode=" + quotationInfoModel.getCode()));
			sendCustomerToConfirmResponse.setSuccess(true);
			return sendCustomerToConfirmResponse;
		}
		sendCustomerToConfirmResponse.setSuccess(false);
		sendCustomerToConfirmResponse.setMessageCode("not found");
		return sendCustomerToConfirmResponse;
	}

	public DefaultResponse customerConfirmAndSaveInfo(final QuotationConfirmFrom quotationConfirmFrom){
		DefaultResponse defaultResponse = new DefaultResponse();
		if(StringUtils.isNotBlank(quotationConfirmFrom.getQuotationCode())){
			QuotationInfoModel quotationInfoModel = quotationService.getQuotationInfoByCode(quotationConfirmFrom.getQuotationCode());
			if(quotationInfoModel!=null){
				if(quotationConfirmFrom.getCustomerConfirm())
				{
					if(quotationConfirmFrom.getSignMedia()!=null){
						MediaModel mediaModel = quotationService.saveMediaInLocal(quotationConfirmFrom.getSignMedia());
						quotationInfoModel.setSignMedia(mediaModel);

					}
					quotationService.setQuotationStatusToConfrim(quotationInfoModel);
				}
				defaultResponse.setSuccess(true);
				return defaultResponse;
			}
		}
		defaultResponse.setSuccess(false);
		defaultResponse.setMessageCode("not found");
		return defaultResponse;
	}

	public DefaultResponse updateStatus(final String quotationCode, final String status){
		DefaultResponse defaultResponse = new DefaultResponse();
		if(StringUtils.isNotBlank(quotationCode) && StringUtils.isNotBlank(status) && OrderStatus.valueOf(status)!=null)
		{
			final QuotationInfoModel quotation = quotationService.getQuotationInfoByCode(quotationCode);
			quotation.setStatus(OrderStatus.valueOf(status));
			quotationService.saveQuoaion(quotation);
			defaultResponse.setSuccess(true);
			return defaultResponse;
		}
		defaultResponse.setSuccess(false);
		defaultResponse.setMessageCode("invalid parameter oppoCode or followOppoCode");
		return defaultResponse;
	}

	public QuotationListResponse getQuotationList(final String oppoCode, final String followOppoCode){
		QuotationListResponse quotationListResponse = new QuotationListResponse();
		if(StringUtils.isBlank(oppoCode) && StringUtils.isBlank(followOppoCode)){
			quotationListResponse.setSuccess(false);
			quotationListResponse.setMessageCode("invalid parameter oppoCode or followOppoCode");
			return quotationListResponse;
		}
		List<QuotationInfoModel> quotationInfoModels = quotationService.getQuotationList(oppoCode, followOppoCode);
		List<QuotationListData> quotationDataList = quotationListDataConverter.convertAll(quotationInfoModels);
		quotationListResponse.setSuccess(true);
		quotationListResponse.setQuotationList(quotationDataList);
		return quotationListResponse;
	}

	public QuotationDetailsResponse getConfirmQuotation(final String oppoCode){
		final QuotationInfoModel quotation = quotationService.getConfirmQuotation(oppoCode);
		return detailsResponsePopulator(quotation);
	}

	public QuotationDetailsResponse getQuotationByCode(final String code){
		final QuotationInfoModel quotation = quotationService.getQuotationInfoByCode(code);
		return detailsResponsePopulator(quotation);
	}

	protected QuotationDetailsResponse detailsResponsePopulator(QuotationInfoModel quotation){
		final QuotationDetailsResponse quotationDetailsResponse = new QuotationDetailsResponse();
		if(quotation!=null)
		{
			QuotationData quotationData = quotationInfoOutputConverter.convert(quotation);
			if(quotation.getStatus()!=null)
			{
				quotationData.setStatus(enumerationService.getEnumerationName(quotation.getStatus()));
				quotationData.setStatusCode(quotation.getStatus().getCode());
			}
			defaultEntryOutputConverter.converter(quotation, quotationData);
			quotationDetailsResponse.setQuotation(quotationData);
			quotationDetailsResponse.setSuccess(true);
			return quotationDetailsResponse;
		}
		quotationDetailsResponse.setSuccess(false);
		quotationDetailsResponse.setMessageCode("NOT FOUND");
		return quotationDetailsResponse;
	}

	public CreateQuotationResponse createQuotaion(final QuotationData quotationData){
		final QuotationInfoModel quotation = quotationService.createQuotaion();
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = getHandleQuotationParameter(quotation, quotationData);
		AlpsCommerceResult commerceOrderResult = quotationService.placeQuotation(alpsCommercePlaceOrderParameter);

		CreateQuotationResponse defaultResponse = new CreateQuotationResponse();
		defaultResponse.setSuccess(commerceOrderResult.getSuccess());
		defaultResponse.setMessage(commerceOrderResult.getErrorMessage());
		defaultResponse.setQuotationCode(commerceOrderResult.getOrder()!=null?commerceOrderResult.getOrder().getCode():"");
		return defaultResponse;
	}

	protected AlpsCommercePlaceOrderParameter getHandleQuotationParameter(final QuotationInfoModel quotation, final QuotationData quotationData){
		quotationInfoConverter.convert(quotationData, quotation);
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = new AlpsCommercePlaceOrderParameter();
		List<AlpsCommerceOrderEntryParameter> commerceQuotationEntryParameterList = quotationEntryParameterConverter
				.converter(quotationData, quotation);
		alpsCommercePlaceOrderParameter.setAdditionalEntryies(commerceQuotationEntryParameterList);
		alpsCommercePlaceOrderParameter.setManagementStock(false);
		alpsCommercePlaceOrderParameter.setOrder(quotation);
		return alpsCommercePlaceOrderParameter;
	}
}
