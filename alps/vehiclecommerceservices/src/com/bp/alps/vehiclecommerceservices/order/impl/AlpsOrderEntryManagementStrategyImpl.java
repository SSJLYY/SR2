package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.core.data.order.AlpsCommerceEntryResult;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.vehiclecommerceservices.order.AlpsCommerceEntryModificationStatus;
import com.bp.alps.vehiclecommerceservices.order.AlpsOrderEntryManagementStrategy;
import com.bp.alps.vehiclecommerceservices.order.CommerceOrderCalculationStrategy;
import com.bp.alps.vehiclecommerceservices.order.SaveEntryCallBackWithoutResult;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationStatus;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantProductModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;


public class AlpsOrderEntryManagementStrategyImpl extends DefaultCommerceAddToCartStrategy implements AlpsOrderEntryManagementStrategy
{
	private static Logger log = Logger.getLogger( AlpsOrderEntryManagementStrategyImpl.class.getName());

	@Resource
	private CommerceOrderCalculationStrategy alpsCommerceOrderCalculationStrategy;

	@Resource
	private ModelService modelService;

	public List<AlpsCommerceOrderEntryParameter> calculateIncrementEntry(AlpsCommercePlaceOrderParameter parameter){
		final AbstractOrderModel order = parameter.getOrder();
		final List<AlpsCommerceOrderEntryParameter> entryiesParameterList = parameter.getAdditionalEntryies();
		validateParameterNotNull(order, "Order cannot be null");
		List<AlpsCommerceOrderEntryParameter> incrementEntry = getNewEntryForOrder(entryiesParameterList);
		List<AlpsCommerceOrderEntryParameter> addQuantityEntry = getAddQuantityEntry(order, entryiesParameterList);
		incrementEntry.addAll(addQuantityEntry);
		return incrementEntry;
	}

	public List<AlpsCommerceEntryResult> updateEntry(AlpsCommercePlaceOrderParameter parameter){
		final AbstractOrderModel order = parameter.getOrder();
		final List<AlpsCommerceOrderEntryParameter> entryparameters = parameter.getAdditionalEntryies();
		SaveEntryCallBackWithoutResult saveEntryCallBackWithoutResult=null;
		if(parameter.getSaveEntryCallBack() instanceof SaveEntryCallBackWithoutResult){
			saveEntryCallBackWithoutResult = (SaveEntryCallBackWithoutResult)parameter.getSaveEntryCallBack();
		}
		validateParameterNotNull(order, "Order cannot be null");
		List<AlpsCommerceEntryResult> commerceEntryResult = new ArrayList<>(entryparameters.size());
		if(CollectionUtils.isNotEmpty(entryparameters))
		{
			List<AlpsCommerceOrderEntryParameter> newEntryForOrder = getNewEntryForOrder(entryparameters);
			if(CollectionUtils.isNotEmpty(newEntryForOrder)){
				commerceEntryResult = addEntryToOrder(order, newEntryForOrder, saveEntryCallBackWithoutResult);
				Optional<AlpsCommerceEntryResult> optional = commerceEntryResult.stream().filter(commerceCartModification -> !commerceCartModification.getStatusCode().equals(CommerceCartModificationStatus.SUCCESS)).findAny();
				if(optional.isPresent()){
					return commerceEntryResult;
				}
			}

			List<AlpsCommerceEntryResult> removeEntryResult = removeEntry(order, entryparameters);
			if(removeEntryResult != null) commerceEntryResult.addAll(removeEntryResult);

			entryparameters.removeAll(newEntryForOrder);
			if(CollectionUtils.isNotEmpty(entryparameters))
			{
				commerceEntryResult.addAll(changeEntryForOrder(order, entryparameters));
			}
		}
		return commerceEntryResult;
	}

	protected List<AlpsCommerceEntryResult> changeEntryForOrder(AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> changeEntryies){
		List<AlpsCommerceEntryResult> changeEntryResult = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(changeEntryies))
		{
			List<AbstractOrderEntryModel> needChangeEntry = new ArrayList<>();
			for (AbstractOrderEntryModel orderEntry : order.getEntries())
			{
				Optional<AlpsCommerceOrderEntryParameter> orderEntryParameter = changeEntryies.stream().filter(changeEntry->
						orderEntry.getProduct().getCode().equals(changeEntry.getProduct().getCode())).findAny();
				if(orderEntryParameter.isPresent())
				{
					AlpsCommerceOrderEntryParameter commerceOrderEntryParameter = orderEntryParameter.get();
					orderEntry.setBasePrice(commerceOrderEntryParameter.getPrice());
					orderEntry.setActualPrice(commerceOrderEntryParameter.getActualPrice());
					Double rate = commerceOrderEntryParameter.getActualPrice()/commerceOrderEntryParameter.getPrice()*100;
					DecimalFormat df = new DecimalFormat("0.00");
					orderEntry.setDiscountRate(rate);
					needChangeEntry.add(orderEntry);
					if (!orderEntry.getQuantity().equals(commerceOrderEntryParameter.getQuantity()))
					{
						if (orderEntry.getQuantity() > commerceOrderEntryParameter.getQuantity())
						{
							addEntryResult(changeEntryResult, orderEntry.getQuantity() - commerceOrderEntryParameter.getQuantity(), orderEntry, AlpsCommerceEntryModificationStatus.REDUCE_QUANTITY);
						}else{
							addEntryResult(changeEntryResult, commerceOrderEntryParameter.getQuantity() - orderEntry.getQuantity(), orderEntry, AlpsCommerceEntryModificationStatus.ADD_QUANTITY);
						}
						orderEntry.setQuantity(commerceOrderEntryParameter.getQuantity());
					}else{
						addEntryResult(changeEntryResult, orderEntry, AlpsCommerceEntryModificationStatus.CHANGE_ATTRIBUTES);
					}
				}
			}
			modelService.saveAll(needChangeEntry);
		}
		return changeEntryResult;
	}

	protected List<AlpsCommerceOrderEntryParameter> getAddQuantityEntry(AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> parameters){
		List<AlpsCommerceOrderEntryParameter> addQuantityEntry = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(order.getEntries()))
		{
			for (AbstractOrderEntryModel entryModel : order.getEntries())
			{
				for (AlpsCommerceOrderEntryParameter orderEntryParameter : parameters)
				{
					if (orderEntryParameter.getQuantity() > entryModel.getQuantity())
					{
						AlpsCommerceOrderEntryParameter alpsCommerceOrderEntryParameter = new AlpsCommerceOrderEntryParameter();
						alpsCommerceOrderEntryParameter.setProduct(orderEntryParameter.getProduct());
						alpsCommerceOrderEntryParameter.setQuantity(orderEntryParameter.getQuantity() - entryModel.getQuantity());
						alpsCommerceOrderEntryParameter.setPrice(orderEntryParameter.getPrice());
						alpsCommerceOrderEntryParameter.setActualPrice(orderEntryParameter.getActualPrice());
						alpsCommerceOrderEntryParameter.setOrder(orderEntryParameter.getOrder());
						addQuantityEntry.add(alpsCommerceOrderEntryParameter);
					}
				}
			}
		}
		return addQuantityEntry;
	}

	protected List<AlpsCommerceEntryResult> removeEntry(AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> parameters){
		List<AbstractOrderEntryModel> needRemoveEntryies = getRemoveEntryForOrder(order, parameters);
		List<AlpsCommerceEntryResult> commerceEntryResult = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(needRemoveEntryies))
		{
			List<AbstractOrderEntryModel> orderEntryModels = new ArrayList<>(order.getEntries());
			for(AbstractOrderEntryModel entryModel : needRemoveEntryies){
				addEntryResult(commerceEntryResult, entryModel, CommerceCartModificationStatus.SUCCESSFULLY_REMOVED);
			}
			orderEntryModels.removeAll(needRemoveEntryies);
			order.setEntries(orderEntryModels);
			modelService.removeAll(needRemoveEntryies);
		}
		return commerceEntryResult;
	}

	protected void addEntryResult(List<AlpsCommerceEntryResult> commerceEntryResult, AbstractOrderEntryModel entryModel, String statusCode){
		AlpsCommerceEntryResult alpsCommerceEntryResult = new AlpsCommerceEntryResult();
		alpsCommerceEntryResult.setProduct(entryModel.getProduct());
		alpsCommerceEntryResult.setQuantity(entryModel.getQuantity());
		alpsCommerceEntryResult.setStatusCode(statusCode);
		commerceEntryResult.add(alpsCommerceEntryResult);
	}

	protected void addEntryResult(List<AlpsCommerceEntryResult> commerceEntryResult, long quantity, AbstractOrderEntryModel entryModel, String statusCode){
		AlpsCommerceEntryResult alpsCommerceEntryResult = new AlpsCommerceEntryResult();
		alpsCommerceEntryResult.setProduct(entryModel.getProduct());
		alpsCommerceEntryResult.setQuantity(quantity);
		alpsCommerceEntryResult.setStatusCode(statusCode);
		commerceEntryResult.add(alpsCommerceEntryResult);
	}

	public List<AlpsCommerceEntryResult> addEntryToOrder(AlpsCommercePlaceOrderParameter parameter){
		final AbstractOrderModel orderModel = parameter.getOrder();
		final List<AlpsCommerceOrderEntryParameter> entryiesParameter = parameter.getAdditionalEntryies();
		validateParameterNotNull(orderModel, "Order cannot be null");
		SaveEntryCallBackWithoutResult saveEntryCallBackWithoutResult=null;
		if(parameter.getSaveEntryCallBack() instanceof SaveEntryCallBackWithoutResult){
			saveEntryCallBackWithoutResult = (SaveEntryCallBackWithoutResult)parameter.getSaveEntryCallBack();
		}
		return addEntryToOrder(orderModel, entryiesParameter, saveEntryCallBackWithoutResult);
	}

	protected List<AlpsCommerceEntryResult> addEntryToOrder(AbstractOrderModel orderModel, List<AlpsCommerceOrderEntryParameter> entryiesParameter, SaveEntryCallBackWithoutResult saveEntryCallBackWithoutResult){
		final List<AbstractOrderEntryModel> AbstractOrderEntryModels;
		if(orderModel.getEntries()!=null){
			AbstractOrderEntryModels = new ArrayList<>(orderModel.getEntries());
		}else{
			AbstractOrderEntryModels = new ArrayList<>();
		}
		List<AlpsCommerceEntryResult> modifications = new ArrayList<>(entryiesParameter.size());
		UnitModel defaultUnit = null;

		if (CollectionUtils.isNotEmpty(entryiesParameter))
		{
			for (final AlpsCommerceOrderEntryParameter entryParameter : entryiesParameter)
			{
				validateParameterNotNull(entryParameter.getOrderEntryClass(), "Entry type cannot be null");

				final AbstractOrderEntryModel entryModel = getModelService().create(entryParameter.getOrderEntryClass());
				entryModel.setQuantity(entryParameter.getQuantity());
				Double rate = 100d;
				if(entryParameter.getPrice()!=null)
				{
					rate = entryParameter.getActualPrice() / entryParameter.getPrice() * 100;
				}
				DecimalFormat df = new DecimalFormat("0.00");
				entryModel.setDiscountRate(Double.valueOf(df.format(rate)));
				entryModel.setProduct(entryParameter.getProduct());
				entryModel.setUnit(entryParameter.getUnit() == null ? entryParameter.getProduct().getUnit() : entryParameter.getUnit());
				entryModel.setBasePrice(entryParameter.getPrice());
				entryModel.setActualPrice(entryParameter.getActualPrice());
				entryModel.setDeliveryPointOfService(entryParameter.getPointOfService());
				entryModel.setOrder(orderModel);
				ProductModel checkTypeProduct = entryParameter.getProduct();
				//If the base product is included, then use base product
				if(checkTypeProduct!=null && checkTypeProduct instanceof VariantProductModel){
					checkTypeProduct = ((VariantProductModel) checkTypeProduct).getBaseProduct();
				}

				if(checkTypeProduct!=null && CollectionUtils.isNotEmpty(checkTypeProduct.getSupercategories()))
				{
					Optional<CategoryModel> optional = checkTypeProduct.getSupercategories().stream().findFirst();
					CategoryModel categoryModel = optional.get();
					entryModel.setProductType(categoryModel.getCode());
				}

				if(saveEntryCallBackWithoutResult!=null)
				{
					saveEntryCallBackWithoutResult.beforSaveEntry(entryModel, entryParameter);
				}

				AbstractOrderEntryModels.add(entryModel);

				AlpsCommerceEntryResult modification = createAddToOrderResp(entryParameter, CommerceCartModificationStatus.SUCCESS, entryModel, entryParameter.getQuantity());
				modifications.add(modification);
			}

			Collections.sort(AbstractOrderEntryModels, new Comparator<AbstractOrderEntryModel>()
			{
				@Override
				public int compare(final AbstractOrderEntryModel order1, final AbstractOrderEntryModel order2)
				{
					return order1.getProduct().getCode().compareTo(order2.getProduct().getCode());
				}
			});

			try
			{
				getModelService().saveAll(AbstractOrderEntryModels);
				orderModel.setEntries(AbstractOrderEntryModels);
			}catch (Exception e){
				log.info(e.getMessage(), e);
				modifications = new ArrayList<>();
				AlpsCommerceEntryResult modification = createAddToOrderResp(entryiesParameter.get(0), CommerceCartModificationStatus.UNAVAILABLE, null, 0l);
				modifications.add(modification);
				return modifications;
			}
		}
		return modifications;
	}

	public List<AlpsCommerceEntryResult> getUnavailableProduct(final List<AlpsCommerceOrderEntryParameter> parameterList){
		final List<AlpsCommerceEntryResult> modifications = new ArrayList<>();
		try
		{
			if (CollectionUtils.isNotEmpty(parameterList))
			{
				for (final AlpsCommerceOrderEntryParameter parameter : parameterList)
				{
					this.beforeAddToCart(parameter);
					validateAddToOrder(parameter);
					final ProductModel productModel = parameter.getProduct();
					final long quantityToAdd = parameter.getQuantity();
					final PointOfServiceModel deliveryPointOfService = parameter.getPointOfService();

					if (isProductLoaded(parameter).booleanValue())
					{
						long actualAllowedQuantityChange = getAllowedEmptyQutaionAdjustmentForProduct(productModel, quantityToAdd,
								deliveryPointOfService);
						final Integer maxOrderQuantity = productModel.getMaxOrderQuantity();
						if (actualAllowedQuantityChange <= 0)
						{
							// Not allowed to add any quantity, or maybe even asked to reduce the quantity
							// Do nothing!
							final String status = getStatusCodeForNotAllowedQuantityChange(maxOrderQuantity, maxOrderQuantity);

							AlpsCommerceEntryResult modification = createAddToOrderResp(parameter, status, createEmptyOrderEntry(parameter), 0);
							modifications.add(modification);
							return modifications;
						}
					}else {
						AlpsCommerceEntryResult modification = createAddToOrderResp(parameter, CommerceCartModificationStatus.UNAVAILABLE,
								null, 0);
						modifications.add(modification);
						return modifications;
					}
				}
			}
		}
		catch (final Exception e)
		{
			AlpsCommerceEntryResult modification = createAddToOrderResp(parameterList.get(0), e.getMessage(),
					null, 0);
			modifications.add(modification);
		}
		return modifications;
	}

	protected List<AbstractOrderEntryModel> getRemoveEntryForOrder(AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> parameters){
		List<AbstractOrderEntryModel> needRemoveEntry = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(order.getEntries()))
		{
			for (AbstractOrderEntryModel entryModel : order.getEntries())
			{
				Boolean includeEntry = false;
				for (AlpsCommerceOrderEntryParameter orderEntryParameter : parameters)
				{
					if (entryModel.getProduct().getCode().equals(orderEntryParameter.getProduct().getCode()))
					{
						includeEntry = true;
					}
				}
				if(!includeEntry){
					needRemoveEntry.add(entryModel);
				}
			}
		}
		return needRemoveEntry;
	}

	protected List<AlpsCommerceOrderEntryParameter> getNewEntryForOrder(List<AlpsCommerceOrderEntryParameter> parameters){
		List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameterList = parameters.stream().filter(parameter->{
			AbstractOrderModel order = parameter.getOrder();
			if(CollectionUtils.isNotEmpty(order.getEntries()))
			{
				for (AbstractOrderEntryModel entryModel : order.getEntries())
				{
					if(entryModel.getProduct().getCode().equals(parameter.getProduct().getCode())){
						return false;
					}
				}
			}
			return true;
		}).collect(Collectors.toList());
		return commerceOrderEntryParameterList;
	}

	protected void validateAddToOrder(final AlpsCommerceOrderEntryParameter parameters) throws CommerceCartModificationException
	{
		final AbstractOrderModel order = parameters.getOrder();
		final ProductModel productModel = parameters.getProduct();

		validateParameterNotNull(order, "Order model cannot be null");
		validateParameterNotNull(productModel, "Product model cannot be null");
		if (productModel.getVariantType() != null)
		{
			throw new CommerceCartModificationException("Choose a variant instead of the base product");
		}

		if (parameters.getQuantity() < 1)
		{
			throw new CommerceCartModificationException("Quantity must not be less than one");
		}
	}

	protected AbstractOrderEntryModel createEmptyOrderEntry(final AlpsCommerceOrderEntryParameter parameter)
	{

		final ProductModel productModel = parameter.getProduct();
		final PointOfServiceModel deliveryPointOfService = parameter.getPointOfService();

		final AbstractOrderEntryModel entry = modelService.create(parameter.getOrderEntryClass());

		entry.setProduct(productModel);
		entry.setDeliveryPointOfService(deliveryPointOfService);

		return entry;
	}

	protected AlpsCommerceEntryResult createAddToOrderResp(final AlpsCommerceOrderEntryParameter parameter, final String status,
			final AbstractOrderEntryModel entry, final long quantityAdded)
	{
		final long quantityToAdd = parameter.getQuantity();

		final AlpsCommerceEntryResult modification = new AlpsCommerceEntryResult();
		modification.setStatusCode(status);
		modification.setQuantityAdded(quantityAdded);
		modification.setQuantity(quantityToAdd);
		modification.setProduct(entry!=null?entry.getProduct():null);

		modification.setEntry(entry);

		return modification;
	}

	protected ProductModel getProduct(final AlpsCommerceOrderEntryParameter parameter){
		ProductModel productModel = parameter.getProduct();
		if(productModel!=null) return productModel;
		productModel = getProductService().getProductForCode(parameter.getProductCode());
		parameter.setProduct(productModel);
		return productModel;
	}

	protected long getAllowedEmptyQutaionAdjustmentForProduct(final ProductModel productModel,
			final long quantityToAdd, final PointOfServiceModel pointOfServiceModel)
	{
		final long cartLevel = 0;
		final long stockLevel = getAvailableStockLevel(productModel, pointOfServiceModel);

		// How many will we have in our cart if we add quantity
		final long newTotalQuantity = cartLevel + quantityToAdd;

		// Now limit that to the total available in stock
		final long newTotalQuantityAfterStockLimit = Math.min(newTotalQuantity, stockLevel);

		// So now work out what the maximum allowed to be added is (note that
		// this may be negative!)
		final Integer maxOrderQuantity = productModel.getMaxOrderQuantity();

		if (isMaxOrderQuantitySet(maxOrderQuantity))
		{
			final long newTotalQuantityAfterProductMaxOrder = Math
					.min(newTotalQuantityAfterStockLimit, maxOrderQuantity.longValue());
			return newTotalQuantityAfterProductMaxOrder - cartLevel;
		}
		return newTotalQuantityAfterStockLimit - cartLevel;
	}

	protected Boolean isProductLoaded(final CommerceCartParameter parameter)
	{
		final ProductModel productModel = parameter.getProduct();
		if(productModel.getPk()!=null){
			return true;
		}
		return false;
	}
}
