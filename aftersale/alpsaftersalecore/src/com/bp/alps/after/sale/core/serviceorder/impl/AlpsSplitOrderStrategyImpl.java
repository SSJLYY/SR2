package com.bp.alps.after.sale.core.serviceorder.impl;

import com.bp.alps.after.sale.core.dao.ServiceOrderDao;
import com.bp.alps.after.sale.core.enums.ServiceSubType;
import com.bp.alps.after.sale.core.model.ServiceOrderEntryModel;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.after.sale.core.serviceorder.AlpsSplitOrderStrategy;
import com.bp.alps.after.sale.core.serviceorder.ServiceOrderCalculationService;
import com.bp.alps.core.data.order.*;
import com.bp.alps.vehiclecommerceservices.order.AlpsOrderEntryManagementStrategy;
import com.bp.alps.vehiclecommerceservices.order.OrderFactory;
import com.bp.alps.vehiclecommerceservices.order.SaveEntryCallBackWithoutResult;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class AlpsSplitOrderStrategyImpl implements AlpsSplitOrderStrategy
{
	@Resource
	private OrderFactory alpsDefaultOrderFactory;

	@Resource
	private ModelService modelService;

	@Resource
	private UserService userService;

	@Resource
	private ProductService productService;

	@Resource
	private ServiceOrderDao serviceOrderDao;

	@Resource
	private AlpsOrderEntryManagementStrategy alpsOrderEntryManagementStrategy;

	@Resource
	private ServiceOrderCalculationService serviceOrderCalculationService;

	public List<AlpsCommerceResult> splitOrder(List<SplitServiceOrderData> splitServiceOrderData, ServiceOrderModel serviceOrderModel){
		List<ServiceOrderModel> keepSubOrder = getKeepSubOrderAndRmoveOther(serviceOrderModel, splitServiceOrderData);

		List<AlpsCommerceResult> alpsCommerceResults = new ArrayList<>(splitServiceOrderData!=null?splitServiceOrderData.size():4);
		if(splitServiceOrderData!=null)
		{
			splitServiceOrderData.stream()
					.forEach(subOrderData -> {
						if (subOrderData.getServiceOrderCode() != null)
						{
							alpsCommerceResults.add(processSubOrder(subOrderData, serviceOrderModel));
						}
					});

			List<ServiceOrderModel> subServiceOrderModels = alpsCommerceResults.stream()
					.filter(result -> result.getSuccess())
					.filter(result -> result.getOrder() instanceof ServiceOrderModel)
					.map(result -> (ServiceOrderModel) result.getOrder()).collect(Collectors.toList());

			serviceOrderCalculationService.calculateReminPrice(serviceOrderModel, subServiceOrderModels);
			if (CollectionUtils.isNotEmpty(serviceOrderModel.getEntries()))
			{
				modelService.saveAll(serviceOrderModel.getEntries());
			}
			modelService.save(serviceOrderModel);

			return alpsCommerceResults;
		}

		serviceOrderCalculationService.calculateReminPrice(serviceOrderModel, new ArrayList<>());
		if (CollectionUtils.isNotEmpty(serviceOrderModel.getEntries()))
		{
			modelService.saveAll(serviceOrderModel.getEntries());
		}
		return alpsCommerceResults;
	}

	protected List<ServiceOrderModel> getKeepSubOrderAndRmoveOther(ServiceOrderModel serviceOrderModel, List<SplitServiceOrderData> splitServiceOrderData){
		final SearchResult<ServiceOrderModel> searchResult = serviceOrderDao.getSubOrder(serviceOrderModel);
		List<ServiceOrderModel> oldSubServiceOrder = new ArrayList<>(searchResult.getResult());
		if(splitServiceOrderData==null){
			modelService.removeAll(oldSubServiceOrder);
			return new ArrayList<>();
		}
		List<ServiceOrderModel> keepSubOrder = oldSubServiceOrder.stream().filter(suborder -> {
			Optional optional = splitServiceOrderData.stream()
					.filter(subOrderData -> subOrderData.getCode()!=null)
					.filter(subOrderData -> subOrderData.getCode().equals(suborder.getCode()))
					.findAny();
			return optional.isPresent();
		}).collect(Collectors.toList());

		oldSubServiceOrder.removeAll(keepSubOrder);
		modelService.removeAll(oldSubServiceOrder);
		return keepSubOrder;
	}

	protected ServiceOrderModel getSubOrder(ServiceOrderModel serviceOrderModel, String subCode){
		final SearchResult<ServiceOrderModel> searchResult = serviceOrderDao.getSubOrder(serviceOrderModel);
		List<ServiceOrderModel> oldSubServiceOrder = new ArrayList<>(searchResult.getResult());
		Optional<ServiceOrderModel> optional = oldSubServiceOrder.stream()
				.filter(suborder -> suborder.getCode().equals(subCode))
				.findAny();
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}

	protected AlpsCommerceResult processSubOrder(SplitServiceOrderData subOrderData, ServiceOrderModel serviceOrderModel){
		AlpsCommerceResult alpsCommerceResult = new AlpsCommerceResult();
		if(serviceOrderModel!=null)
		{
			ServiceOrderModel newOrder = null;
			if(StringUtils.isNotBlank(subOrderData.getCode())){
				newOrder = getSubOrder(serviceOrderModel, subOrderData.getCode());
				checkEntryUpdate(subOrderData, newOrder);
			}
			if(newOrder == null)
			{
				newOrder = alpsDefaultOrderFactory.cloneWithoutEntry(null, serviceOrderModel, ServiceOrderModel.class);
				newOrder.setParentOrder(serviceOrderModel);
				List<AlpsCommerceEntryResult> entryResultList = addEntryToOrder(newOrder, serviceOrderModel, subOrderData);

				alpsCommerceResult = getFinalResult(entryResultList);
				if (!alpsCommerceResult.getSuccess())
				{
					return alpsCommerceResult;
				}

				if (StringUtils.isNotBlank(subOrderData.getServiceSubTypeCode()))
					newOrder.setServiceSubType(ServiceSubType.valueOf(subOrderData.getServiceSubTypeCode()));

				if (StringUtils.isNotBlank(subOrderData.getBuyer()))
				{
					UserModel userModel = userService.getUserForUID(subOrderData.getBuyer());
					if (userModel instanceof CustomerModel)
					{
						newOrder.setUser(userModel);
					}
				}
			}

			try{
				serviceOrderCalculationService.calculate(newOrder);
			}
			catch (final CalculationException calculationException)
			{
				throw new IllegalStateException("order model " + newOrder.getCode() + " was not calculated due to: "
						+ calculationException.getMessage(), calculationException);
			}

			modelService.save(newOrder);
			alpsCommerceResult.setOrder(newOrder);
			alpsCommerceResult.setSuccess(true);
			return alpsCommerceResult;
		}
		alpsCommerceResult.setSuccess(false);
		alpsCommerceResult.setErrorMessage("invalid order");
		return alpsCommerceResult;
	}

	protected void checkEntryUpdate(SplitServiceOrderData subOrderData, ServiceOrderModel suborder){
		List<AbstractOrderEntryModel> entryModels = suborder.getEntries();
		final List<AbstractOrderEntryModel> changeOrderEntries = new LinkedList<>();
		entryModels.stream().forEach(entry ->{
			if(CollectionUtils.isNotEmpty(subOrderData.getItem())){
				subOrderData.getItem().stream()
					.filter(entrydata -> entrydata.getProductCode()!=null)
					.filter(entrydata  -> entrydata.getProductCode().equals(entry.getProduct().getCode()))
					.forEach(entrydata -> {
						if(!entrydata.getRate().equals(entry.getDiscountRate()) || !entrydata.getActualPrice().equals(entry.getActualPrice()))
						{
							entry.setDiscountRate(entrydata.getRate());
							entry.setActualPrice(entrydata.getActualPrice());
							changeOrderEntries.add(entry);
						}
					});
			}
		});


		if(CollectionUtils.isNotEmpty(changeOrderEntries))
		{
			modelService.saveAll(changeOrderEntries);
		}
	}

	protected AlpsCommerceResult getFinalResult(List<AlpsCommerceEntryResult> commerceCartModifications){
		AlpsCommerceResult commerceOrderResult = new AlpsCommerceResult();
		commerceOrderResult.setSuccess(false);
		Optional<AlpsCommerceEntryResult> optional = commerceCartModifications.stream()
				.filter(commerceCartModification -> !commerceCartModification.getStatusCode().equals(CommerceCartModificationStatus.SUCCESS))
				.findAny();

		if(optional.isPresent()){
			CommerceCartModification commerceCartModification = optional.get();
			commerceOrderResult.setErrorMessage(commerceCartModification.getStatusCode());
			return commerceOrderResult;
		}
		commerceOrderResult.setSuccess(true);
		return commerceOrderResult;
	}

	protected List<AlpsCommerceEntryResult> addEntryToOrder(ServiceOrderModel serviceOrderModel, ServiceOrderModel parentOrder, SplitServiceOrderData subOrderData){
		AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter = new AlpsCommercePlaceOrderParameter();
		alpsCommercePlaceOrderParameter.setManagementStock(false);
		alpsCommercePlaceOrderParameter.setOrder(serviceOrderModel);
		final List<AbstractOrderEntryModel> parentEntrys = parentOrder.getEntries();
		List<ServiceOrderSplitEntryData> alpsCommerceOrderEntryParameters = convertToEntryParameters(subOrderData, parentEntrys);
		alpsCommercePlaceOrderParameter.setAdditionalEntryies(alpsCommerceOrderEntryParameters);
		setParameterCallback(alpsCommercePlaceOrderParameter, parentEntrys);
		return alpsOrderEntryManagementStrategy.addEntryToOrder(alpsCommercePlaceOrderParameter);
	}

	protected List<ServiceOrderSplitEntryData> convertToEntryParameters(SplitServiceOrderData subOrderData, final List<AbstractOrderEntryModel> parentEntrys){
		List<ServiceOrderSplitEntryData> alpsCommerceOrderEntryParameters = subOrderData.getItem().stream()
				.filter(subitem -> subitem.getProductCode()!=null || subitem.getProduct()!=null)
				.map(subitem -> {
					if(subitem.getProduct()==null && CollectionUtils.isNotEmpty(parentEntrys)){
						Optional<ProductModel> optional = parentEntrys.stream()
								.filter(entryModel -> subitem.getProductCode().equals(entryModel.getProduct().getCode()))
								.map(entry -> entry.getProduct())
								.findAny();
						if (optional.isPresent())
						{
							subitem.setProduct(optional.get());
						}
					}
					subitem.setOrderEntryClass(ServiceOrderEntryModel.class);
					return subitem;
				}).collect(Collectors.toList());
		return alpsCommerceOrderEntryParameters;
	}

	protected void setParameterCallback(AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter, final List<AbstractOrderEntryModel> parentEntrys){
		alpsCommercePlaceOrderParameter.setSaveEntryCallBack(new SaveEntryCallBackWithoutResult(){
			@Override
			public void beforSaveEntry(final AbstractOrderEntryModel entry, AlpsCommerceOrderEntryParameter entryParameter)
			{
				if(entry instanceof ServiceOrderEntryModel)
				{
					ServiceOrderEntryModel subEntry = (ServiceOrderEntryModel) entry;
					if(CollectionUtils.isNotEmpty(parentEntrys))
					{
						Optional<AbstractOrderEntryModel> optional = parentEntrys.stream()
								.filter(entryModel -> entry.getProduct().getCode().equals(entryModel.getProduct().getCode())).findAny();
						if (optional.isPresent())
						{
							AbstractOrderEntryModel entryModel = optional.get();
							if (entryModel instanceof ServiceOrderEntryModel)
							{
								subEntry.setEntryType(((ServiceOrderEntryModel) entryModel).getEntryType());
								subEntry.setProductType(entryModel.getProductType());
								subEntry.setQuantity(entryModel.getQuantity());
							}
						}
					}
					if(entryParameter instanceof ServiceOrderSplitEntryData){
						subEntry.setDiscountRate(((ServiceOrderSplitEntryData) entryParameter).getRate());
					}
				}
			}
		});
	}
}
