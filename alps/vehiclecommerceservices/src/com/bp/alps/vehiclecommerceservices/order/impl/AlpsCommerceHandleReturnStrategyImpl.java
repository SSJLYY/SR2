package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.CommerceReturnRequestParameter;
import com.bp.alps.vehiclecommerceservices.model.ReturnEntryRecordModel;
import com.bp.alps.vehiclecommerceservices.order.*;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.returns.model.ReturnEntryModel;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class AlpsCommerceHandleReturnStrategyImpl implements
		AlpsCommerceHandleReturnStrategy
{
	@Resource
	private CommerceReturnRequestCalculationStrategy commerceReturnRequestCalculationStrategy;

	@Resource
	private AlpsOrderEntryManagementStrategy alpsOrderEntryManagementStrategy;

	@Resource
	private CommerceStockManagementStrategy alpsCommerceStockManagementStrategy;

	@Resource
	private ReturnRequestFactory returnRequestFactory;

	@Resource
	private ModelService modelService;

	@Resource
	private AlpsCloneAbstractOrderStrategy alpsCloneAbstractOrderStrategy;

	public ReturnRequestModel initializeReturnRequest(AbstractOrderModel orderModel){
		return returnRequestFactory.initializeReturnRequest(orderModel);
	}

	public AlpsCommerceResult returnProcess(CommerceReturnRequestParameter parameter){
		AlpsCommerceResult alpsCommerceResult = new AlpsCommerceResult();
		alpsCommerceResult.setSuccess(false);
		final AbstractOrderModel order = parameter.getOrder();
		if(order instanceof OrderModel)
		{
			final ReturnRequestModel returnRequest = initializeReturnRequest(order);

			if (CollectionUtils.isNotEmpty(parameter.getAdditionalEntryies()))
			{
				addReturnEntryies(parameter, returnRequest, order.getEntries());
			}
			commerceReturnRequestCalculationStrategy.calculateReturnRequest(returnRequest);
			alpsCommerceResult.setSuccess(true);
			alpsCommerceResult.setOrder(order);
			alpsCommerceResult.setReturnRequest(returnRequest);
			List<ReturnRequestModel> returnRequestModels = new LinkedList<>(((OrderModel) order).getReturnRequests());
			returnRequestModels.add(returnRequest);
			((OrderModel) order).setReturnRequests(returnRequestModels);
			modelService.save(order);
		}
		return alpsCommerceResult;
	}

	protected void addReturnEntryies(CommerceReturnRequestParameter<AlpsCommerceOrderEntryParameter> parameter, ReturnRequestModel returnRequest, List<AbstractOrderEntryModel> entryModels){
		List<ReturnEntryModel> recordModels = new LinkedList<>();
		parameter.getAdditionalEntryies().stream()
			.filter(entryData -> entryData.getProductCode()!=null)
			.forEach(entryData -> {
				Optional<AbstractOrderEntryModel> optional = entryModels.stream()
						.filter(entryModel -> entryData.getProductCode().equals(entryModel.getProduct().getCode()))
						.findAny();
				if(optional.isPresent()){
					ReturnEntryRecordModel returnEntryRecordModel = returnRequestFactory.initializeReturnEntry(optional.get());
					recordModels.add(returnEntryRecordModel);
				}
			});
		returnRequest.setReturnEntries(recordModels);
	}

	public AlpsCommerceResult updateReturn(CommerceReturnRequestParameter<AlpsCommerceOrderEntryParameter> parameter){
		changeReturnEntryies(parameter);
		modelService.save(parameter.getReturnRequest());
		AlpsCommerceResult alpsCommerceResult = new AlpsCommerceResult();
		alpsCommerceResult.setSuccess(true);
		return alpsCommerceResult;
	}

	protected void changeReturnEntryies(CommerceReturnRequestParameter<AlpsCommerceOrderEntryParameter> parameter){
		ReturnRequestModel returnRequest = parameter.getReturnRequest();
		if(returnRequest!=null)
		{
			List<ReturnEntryModel> recordModels = new LinkedList<>();
			parameter.getAdditionalEntryies().stream().filter(entryData -> entryData.getProductCode() != null).forEach(entryData -> {
				Optional<ReturnEntryModel> optional = returnRequest.getReturnEntries().stream()
						.filter(entryModel -> entryData.getProductCode().equals(entryModel.getOrderEntry().getProduct().getCode())).findAny();
				if (optional.isPresent() && optional.get() instanceof ReturnEntryRecordModel)
				{
					ReturnEntryRecordModel returnEntryRecordModel = (ReturnEntryRecordModel) optional.get();
					returnEntryRecordModel.setReceivedQuantity(entryData.getQuantity());
					recordModels.add(returnEntryRecordModel);
				}
			});
			modelService.saveAll(recordModels);
			returnRequest.setReturnEntries(recordModels);
		}
	}
}
