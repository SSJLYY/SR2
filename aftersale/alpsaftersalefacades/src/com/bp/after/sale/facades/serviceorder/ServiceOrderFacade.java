package com.bp.after.sale.facades.serviceorder;

import com.bp.after.sale.facades.data.ServiceOrderData;
import com.bp.after.sale.facades.data.SplitServiceOrderRequest;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.facades.order.SubServiceOrderData;
import com.bp.alps.facades.data.order.CreateServiceOrderResponse;
import com.bp.alps.facades.data.order.SearchServiceOrderRequest;
import com.bp.alps.facades.data.order.ServiceOrderListResponse;
import de.hybris.platform.cmsfacades.data.OptionData;

import java.util.List;


public interface ServiceOrderFacade
{
	CreateServiceOrderResponse createServiceOrder(ServiceOrderData serviceOrderData);

	List<OptionData> getServiceOrderStatus();

	List<OptionData> getServiceOrderType();

	List<OptionData> getServiceSubType();

	List<OptionData> getServiceOrderEntryType();

	ServiceOrderListResponse getServiceOrderByCurrentServiceConsultant(SearchServiceOrderRequest searchServiceOrderRequest);

	CreateServiceOrderResponse updateServiceOrder(ServiceOrderData serviceOrderData);

	ServiceOrderData getServiceOrderByCode(String code);

	List<AlpsCommerceResult> splitOrder(SplitServiceOrderRequest splitServiceOrderRequest, String defualtCode);

	List<SubServiceOrderData> getSubOrderByParentCode(String code);
}
