package com.bp.alps.core.order.impl;

import com.bp.alps.core.dao.AlpsOrderDao;
import com.bp.alps.core.data.order.*;
import com.bp.alps.core.model.QuotationInfoModel;
import com.bp.alps.core.service.impl.DefaultAlpssalecoreService;
import com.bp.alps.vehiclecommerceservices.order.AlpsCommerceHandleOrderStrategy;
import com.bp.alps.core.order.OrderService;
import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import com.bp.alps.core.model.VehicleOrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import java.util.*;


public class OrderServiceImpl extends DefaultAlpssalecoreService implements OrderService
{
	@Resource
	private ModelService modelService;

	@Resource
	private AlpsOrderDao alpsOrderDao;

	@Resource
	private AlpsCustomerService alpsCustomerService;

	@Resource
	private EnumerationService enumerationService;

	@Resource
	private AlpsCommerceHandleOrderStrategy alpsCommerceHandleOrderStrategy;

	public VehicleOrderModel createOrder(){
		return (VehicleOrderModel)alpsCommerceHandleOrderStrategy.initializeOrderByType(VehicleOrderModel.class);
	}

	public AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter parameter){
		if(parameter.getOrder()!=null && parameter.getOrder() instanceof VehicleOrderModel)
		{
			saveSubsidiaryTable((VehicleOrderModel)parameter.getOrder());
		}
		return alpsCommerceHandleOrderStrategy.placeOrder(parameter);
	}

	protected void saveSubsidiaryTable(VehicleOrderModel orderModel){
		if(orderModel.getSalesAttribute()!=null)
		{
			List<Object> changeAttribute = new LinkedList<>();
			changeAttribute.add(orderModel.getSalesAttribute());
			if(orderModel.getSalesAttribute().getFinanceInfo()!=null)
			{
				changeAttribute.add(orderModel.getSalesAttribute().getFinanceInfo());
			}
			if(orderModel.getSalesAttribute().getUsedcarInfo()!=null)
			{
				changeAttribute.add(orderModel.getSalesAttribute().getUsedcarInfo());
			}
			if(orderModel.getSalesAttribute().getLicensePlateInfo()!=null)
			{
				changeAttribute.add(orderModel.getSalesAttribute().getLicensePlateInfo());
			}
			modelService.saveAll(changeAttribute);
		}
	}

	public AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter parameter){
		AlpsCommerceResult alpsCommerceResult = alpsCommerceHandleOrderStrategy.updateOrder(parameter);
		saveSubsidiaryTable((VehicleOrderModel)alpsCommerceResult.getOrder());
		return alpsCommerceResult;
	}

	public void updateOrderStatus(VehicleOrderModel order, OrderStatus orderStatus){
		order.setStatus(orderStatus);
		saveOrder(order);
	}

	public SearchPageData<VehicleOrderModel> getOrderListByCurrentSales(String orderType, PageableData pageableData){
		CustomerModel customerModel = alpsCustomerService.getCurrentSalesConsultant();
		if(customerModel != null){
			return alpsOrderDao.getOrderListByCurrentSales(orderType, customerModel, pageableData);
		}
		return null;
	}

	public SearchPageData<VehicleOrderModel> searchForCurrentSalesOrders(SearchOrderParameter searchOrderParameter, PageableData pageableData){
		CustomerModel customerModel = alpsCustomerService.getCurrentSalesConsultant();
		if(customerModel != null){
			return alpsOrderDao.searchForCurrentSalesOrders(searchOrderParameter, customerModel, pageableData);
		}
		return null;
	}

	public VehicleOrderModel getOrderByCode(String orderCode){
		return alpsOrderDao.getOrderByCode(orderCode);
	}

	public OrderStatus getOrderStatus(String statusCode){
		return enumerationService.getEnumerationValue(OrderStatus.class, statusCode);
	}

	public void saveOrder(VehicleOrderModel order){
		saveSubsidiaryTable(order);
		modelService.save(order);
	}
}
