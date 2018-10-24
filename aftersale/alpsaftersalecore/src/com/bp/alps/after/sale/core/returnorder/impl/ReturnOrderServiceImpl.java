package com.bp.alps.after.sale.core.returnorder.impl;

import com.bp.after.sale.facades.IncrementOrderBaseData;
import com.bp.after.sale.facades.IncrementOrderData;
import com.bp.alps.after.sale.core.dao.ReturnOrderDao;
import com.bp.alps.after.sale.core.increment.IncrementOrderService;
import com.bp.alps.after.sale.core.model.IncrementOrderModel;
import com.bp.alps.after.sale.core.returnorder.ReturnOrderService;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.CommerceReturnRequestParameter;
import com.bp.alps.vehiclecommerceservices.order.AlpsCommerceHandleReturnStrategy;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.returns.impl.DefaultReturnService;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;
import java.util.List;


public class ReturnOrderServiceImpl extends DefaultReturnService implements ReturnOrderService
{
	@Resource
	private ReturnOrderDao returnOrderDao;

	@Resource
	private IncrementOrderService incrementOrderService;

	@Resource
	private AlpsCommerceHandleReturnStrategy alpsCommerceHandleReturnStrategy;

	@Resource
	private Converter<IncrementOrderModel, IncrementOrderBaseData> incrementOrderBaseOutputConverter;

	@Resource
	private Converter<IncrementOrderModel, IncrementOrderData> incrementOrderOutputConverter;

	public ReturnRequestModel createReturnOrder(String orderCode){
		IncrementOrderModel afterSaleOrderModel = incrementOrderService.getIncrementOrderByCode(orderCode);
		if(afterSaleOrderModel != null)
		{
			ReturnRequestModel returnOrderModel = alpsCommerceHandleReturnStrategy.initializeReturnRequest( afterSaleOrderModel);
			return returnOrderModel;
		}
		return null;
	}

	public AlpsCommerceResult returnProcess(CommerceReturnRequestParameter commerceReturnRequestParameter){
		AlpsCommerceResult alpsCommerceResult = alpsCommerceHandleReturnStrategy.returnProcess(commerceReturnRequestParameter);
		return alpsCommerceResult;
	}

	public AlpsCommerceResult update(CommerceReturnRequestParameter commerceReturnRequestParameter){
		return alpsCommerceHandleReturnStrategy.updateReturn(commerceReturnRequestParameter);
	}

	public SearchPageData<ReturnRequestModel> getReturnOrderByCurrentConsultant(String startTime, String endTime, String orderCode, OrderStatus status, String username, String mobile, PageableData pageableData){
		return returnOrderDao.getReturnOrderByCurrentServiceConsultant(startTime,
				endTime,
				orderCode,
				status,
				username,
				mobile,
				pageableData);
	}

	public ReturnRequestModel getReturnOrderByCode(String code){
		return returnOrderDao.getReturnOrderByCode(code);
	}
}
