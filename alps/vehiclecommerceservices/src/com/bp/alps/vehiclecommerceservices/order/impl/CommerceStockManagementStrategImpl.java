package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.core.data.order.AlpsCommerceEntryResult;
import com.bp.alps.core.data.order.AlpsStockHandleParameter;
import com.bp.alps.vehiclecommerceservices.order.AlpsCommerceEntryModificationStatus;
import com.bp.alps.vehiclecommerceservices.order.CommerceStockManagementStrategy;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.stock.StockService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CommerceStockManagementStrategImpl implements CommerceStockManagementStrategy
{
	private static Logger log = Logger.getLogger( CommerceStockManagementStrategImpl.class.getName() );

	@Resource
	private StockService stockService;

	public void reserveStock(final AbstractOrderModel orderModel){
		reserveStockByEntry(orderModel, orderModel.getEntries());
	}

	public void reserveStockByEntry(final AbstractOrderModel orderModel, final List<AbstractOrderEntryModel> orderEntryModels){
		List<AlpsStockHandleParameter> alpsStockHandleParameters = orderEntryModels.stream().map(abstractOrderEntryModel -> {
			AlpsStockHandleParameter alpsStockHandleParameter = new AlpsStockHandleParameter();
			alpsStockHandleParameter.setProduct(abstractOrderEntryModel.getProduct());
			alpsStockHandleParameter.setQuantity(abstractOrderEntryModel.getQuantity().intValue());
			return alpsStockHandleParameter;
		}).collect(Collectors.toList());
		reserveStock(orderModel, alpsStockHandleParameters);
	}

	public void releaseExcessStockByHandleResult(final AbstractOrderModel orderModel, final List<AlpsCommerceEntryResult> orderEntryModels){
		List<AlpsStockHandleParameter> alpsStockHandleParameters = orderEntryModels.stream().filter(alpsCommerceEntryResult ->{
			return AlpsCommerceEntryModificationStatus.REDUCE_QUANTITY.equals(alpsCommerceEntryResult.getStatusCode())
					|| AlpsCommerceEntryModificationStatus.SUCCESSFULLY_REMOVED.equals(alpsCommerceEntryResult.getStatusCode());
		}).map(alpsCommerceEntryResult -> {
			AlpsStockHandleParameter alpsStockHandleParameter = new AlpsStockHandleParameter();
			alpsStockHandleParameter.setProduct(alpsCommerceEntryResult.getProduct());
			alpsStockHandleParameter.setQuantity(Long.valueOf(alpsCommerceEntryResult.getQuantity()).intValue());
			return alpsStockHandleParameter;
		}).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(alpsStockHandleParameters)) releaseStock(orderModel, alpsStockHandleParameters);
	}

	public void reserveIncrementStockByHandleResult(final AbstractOrderModel orderModel, final List<AlpsCommerceEntryResult> orderEntryModels){
		List<AlpsStockHandleParameter> alpsStockHandleParameters = orderEntryModels.stream()
				.filter(alpsCommerceEntryResult -> {
					return AlpsCommerceEntryModificationStatus.ADD_QUANTITY.equals(alpsCommerceEntryResult.getStatusCode())
						|| AlpsCommerceEntryModificationStatus.SUCCESS.equals(alpsCommerceEntryResult.getStatusCode());
				})
				.filter(alpsCommerceEntryResult -> alpsCommerceEntryResult.getQuantity() > 0)
				.map(alpsCommerceEntryResult -> {
			AlpsStockHandleParameter alpsStockHandleParameter = new AlpsStockHandleParameter();
			alpsStockHandleParameter.setProduct(alpsCommerceEntryResult.getProduct());
			alpsStockHandleParameter.setQuantity(Long.valueOf(alpsCommerceEntryResult.getQuantity()).intValue());
			return alpsStockHandleParameter;
		}).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(alpsStockHandleParameters)) reserveStock(orderModel, alpsStockHandleParameters);
	}

	public void reserveStock(final AbstractOrderModel orderModel, final List<AlpsStockHandleParameter> alpsStockHandleParameters){
		List<WarehouseModel> warehouseModels = orderModel.getStore().getWarehouses();
		List<AlpsStockHandleParameter> backupStockHandleParameter = new ArrayList<>();
		for(AlpsStockHandleParameter alpsStockHandleParameter : alpsStockHandleParameters){
			for(WarehouseModel warehouseModel : warehouseModels){
				try
				{
					stockService.reserve(alpsStockHandleParameter.getProduct(), warehouseModel, alpsStockHandleParameter.getQuantity(), "order Num:"+orderModel.getCode());
					alpsStockHandleParameter.setWarehouse(warehouseModel);
					backupStockHandleParameter.add(alpsStockHandleParameter);
					break;
				}catch (Exception e){
					log.error("order code: "+orderModel.getCode()+", reserve Stock fail:"+e.getMessage(), e);
				}
			}
		}
		if(backupStockHandleParameter.size() < alpsStockHandleParameters.size()){
			rollbackStock(orderModel, backupStockHandleParameter);
			orderModel.setStatus(OrderStatus.CHECKED_INVALID);
		}
	}

	protected void rollbackStock(final AbstractOrderModel orderModel, List<AlpsStockHandleParameter> alpsStockHandleParameters){
		for(AlpsStockHandleParameter alpsStockHandleParameter : alpsStockHandleParameters){
			try
			{
				stockService.release(alpsStockHandleParameter.getProduct(), alpsStockHandleParameter.getWarehouse(), alpsStockHandleParameter.getQuantity(), " some entry out of stock in order Num:"+orderModel.getCode());
			}catch (Exception e){
				log.error("order code: "+orderModel.getCode()+", release Stock fail:"+e.getMessage(), e);
			}
		}
	}

	public void releaseStockByEntry(AbstractOrderModel order, List<AbstractOrderEntryModel> needRemoveEntry){
		List<WarehouseModel> warehouseModels = order.getStore().getWarehouses();
		for(AbstractOrderEntryModel entryModel : needRemoveEntry){
			try
			{
				WarehouseModel warehouseModel = warehouseModels.get(0);
				stockService.release(entryModel.getProduct(), warehouseModel, entryModel.getQuantity().intValue(), " release the stock because of the update of the order:"+order.getCode());
			}catch (Exception e){
				log.error("order code: "+order.getCode()+", release Stock fail:"+e.getMessage(), e);
			}
		}
	}

	protected void releaseStock(AbstractOrderModel order, List<AlpsStockHandleParameter> releaseParameters){
		List<WarehouseModel> warehouseModels = order.getStore().getWarehouses();
		int i =0;
		for(AlpsStockHandleParameter releaseParameter : releaseParameters){
			i++;
			for(WarehouseModel warehouseModel : warehouseModels)
			{
				try
				{
					stockService.release(releaseParameter.getProduct(), warehouseModel, releaseParameter.getQuantity(),
							" release the stock because of the update of the order:" + order.getCode());
					break;
				}
				catch (Exception e)
				{
					log.error("order code: " + order.getCode() + ", release Stock fail:" + e.getMessage(), e);
				}
			}
		}
		if(releaseParameters.size() < i){
			order.setStatus(OrderStatus.CHECKED_INVALID);
		}
	}
}
