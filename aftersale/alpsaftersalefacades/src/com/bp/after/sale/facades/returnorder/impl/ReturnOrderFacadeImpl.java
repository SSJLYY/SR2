package com.bp.after.sale.facades.returnorder.impl;

import com.bp.after.sale.facades.IncrementOrderBaseData;
import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.after.sale.facades.SearchIncrementOrderRequest;
import com.bp.after.sale.facades.data.IncrementOrderEntryData;
import com.bp.after.sale.facades.returnorder.ReturnOrderFacade;
import com.bp.alps.after.sale.core.increment.IncrementOrderService;
import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import com.bp.alps.after.sale.core.returnorder.ReturnOrderService;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.CommerceReturnRequestParameter;
import com.bp.alps.facades.data.order.CreateIncrementOrderResponse;
import com.bp.alps.facades.data.order.IncrementOrderListResponse;
import com.bp.alps.vehiclecommercefacade.facade.DefaultAlpsCommerceFacade;
import de.hybris.platform.basecommerce.enums.ReturnStatus;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


public class ReturnOrderFacadeImpl extends DefaultAlpsCommerceFacade implements ReturnOrderFacade
{
	@Resource
	private ReturnOrderService returnOrderService;

	@Resource
	private IncrementOrderService incrementOrderService;

	@Resource
	private Converter<ReturnRequestModel, IncrementOrderBaseData> returnOrderBaseOutputConverter;

	@Resource
	private Converter<ReturnRequestModel, IncrementOrderData> returnOrderOutputConverter;

	@Resource
	private ReturnOrderEntryParameterConverterImpl returnOrderEntryParameterConverter;

	@Resource(name="enumConverter")
	private Converter<HybrisEnumValue, OptionData> enumerationValueModelConverter;

	@Resource
	private EnumerationService enumerationService;

	public CreateIncrementOrderResponse createReturnOrder(IncrementOrderData incrementOrderData){
		IncrementOrderModel incrementOrder = incrementOrderService.getIncrementOrderByCode(incrementOrderData.getCode());
		CreateIncrementOrderResponse createOrderResponse = new CreateIncrementOrderResponse();
		if(incrementOrder!=null)
		{
			if(CollectionUtils.isNotEmpty(incrementOrderData.getEntries())){
				List<IncrementOrderEntryData> incrementOrderEntryData = incrementOrderData.getEntries().stream()
						.filter(entry -> entry.getOperationBox()!=null)
						.collect(Collectors.toList());
				incrementOrderData.setEntries(incrementOrderEntryData);
			}
			CommerceReturnRequestParameter commerceReturnRequestParameter = getHandleOrderEntryParameter(incrementOrderData, incrementOrder, null);
			AlpsCommerceResult alpsCommerceResult = returnOrderService.returnProcess(commerceReturnRequestParameter);
			createOrderResponse.setSuccess(alpsCommerceResult.getSuccess());
			createOrderResponse.setMessageCode(alpsCommerceResult.getErrorMessage());
			createOrderResponse.setCode(alpsCommerceResult.getReturnRequest()!=null?alpsCommerceResult.getReturnRequest().getCode():null);
			return createOrderResponse;
		}
		createOrderResponse.setSuccess(false);
		createOrderResponse.setMessageCode("not.found");
		return createOrderResponse;
	}

	public CreateIncrementOrderResponse updateOrder(IncrementOrderData incrementOrderData){
		CreateIncrementOrderResponse createIncrementOrderResponse = new CreateIncrementOrderResponse();
		createIncrementOrderResponse.setSuccess(false);
		createIncrementOrderResponse.setMessageCode("not.found");
		if(StringUtils.isNotBlank(incrementOrderData.getReturnOrderCode())){
			createIncrementOrderResponse.setCode(incrementOrderData.getReturnOrderCode());
			ReturnRequestModel returnOrderModel = returnOrderService.getReturnOrderByCode(incrementOrderData.getReturnOrderCode());
			returnOrderModel.setStatus(ReturnStatus.valueOf(incrementOrderData.getStatusCode()));
			if(returnOrderModel!=null){
				CommerceReturnRequestParameter commerceReturnRequestParameter = getHandleOrderEntryParameter(incrementOrderData, null, returnOrderModel);
				AlpsCommerceResult alpsCommerceResult = returnOrderService.update(commerceReturnRequestParameter);
				createIncrementOrderResponse.setSuccess(alpsCommerceResult.getSuccess());
				createIncrementOrderResponse.setMessageCode(alpsCommerceResult.getErrorMessage());
			}
		}
		return createIncrementOrderResponse;
	}

	protected CommerceReturnRequestParameter getHandleOrderEntryParameter(IncrementOrderData incrementOrderData, IncrementOrderModel incrementOrder, ReturnRequestModel returnOrderModel){
		List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters = returnOrderEntryParameterConverter.converter(incrementOrderData, incrementOrder);
		CommerceReturnRequestParameter commerceReturnRequestParameter = new CommerceReturnRequestParameter();
		commerceReturnRequestParameter.setAdditionalEntryies(commerceOrderEntryParameters);
		commerceReturnRequestParameter.setOrder(incrementOrder);
		commerceReturnRequestParameter.setReturnRequest(returnOrderModel);
		return commerceReturnRequestParameter;
	}

	public IncrementOrderListResponse getReturnOrderByCurrentConsultant(SearchIncrementOrderRequest searchIncrementOrderRequest){
		final PageableData pageableData = populatorPageable(searchIncrementOrderRequest);
		OrderStatus orderStatus = null;
		if(StringUtils.isNotBlank(searchIncrementOrderRequest.getStatus())) orderStatus = OrderStatus.valueOf(searchIncrementOrderRequest.getStatus());
		SearchPageData<ReturnRequestModel> searchPageData = returnOrderService.getReturnOrderByCurrentConsultant(
				searchIncrementOrderRequest.getStartTime(),
				searchIncrementOrderRequest.getEndTime(),
				searchIncrementOrderRequest.getCode(),
				orderStatus,
				searchIncrementOrderRequest.getCustomerName(),
				searchIncrementOrderRequest.getCustomerMobileNumber(),
				pageableData);
		List<IncrementOrderBaseData> incrementOrderBaseData = returnOrderBaseOutputConverter.convertAll(searchPageData.getResults());

		IncrementOrderListResponse incrementOrderListResponse = new IncrementOrderListResponse();
		incrementOrderListResponse.setSuccess(true);
		incrementOrderListResponse.setTotalPage(searchPageData.getPagination().getNumberOfPages());
		incrementOrderListResponse.setOrders(incrementOrderBaseData);
		return incrementOrderListResponse;
	}

	public IncrementOrderData getReturnOrderByCode(String code){
		ReturnRequestModel returnOrderModel = returnOrderService.getReturnOrderByCode(code);
		IncrementOrderData incrementOrderData = returnOrderOutputConverter.convert(returnOrderModel);
		return incrementOrderData;
	}

	public List<OptionData> getOrderStatus(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(ReturnStatus._TYPECODE));
	}
}
