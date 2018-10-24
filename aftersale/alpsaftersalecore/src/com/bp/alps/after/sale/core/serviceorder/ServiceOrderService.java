package com.bp.alps.after.sale.core.serviceorder;

import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.after.sale.core.model.ServiceOrderRelatedRolesModel;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.data.order.SplitServiceOrderData;
import com.bp.alps.facades.data.order.SearchServiceOrderRequest;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;


public interface ServiceOrderService
{
	ServiceOrderModel createServiceOrder();

	AlpsCommerceResult placeOrder(AlpsCommercePlaceOrderParameter parameter);

	SearchPageData<ServiceOrderModel> getServiceOrderByCurrentServiceConsultant(SearchServiceOrderRequest searchServiceOrderRequest, PageableData pageableData);

	ServiceOrderModel getServiceOrderByCode(String code);

	List<ServiceOrderRelatedRolesModel> getRelatedRoleByOrder(ServiceOrderModel serviceOrderModel);

	AlpsCommerceResult updateOrder(AlpsCommercePlaceOrderParameter parameter);

	ServiceOrderRelatedRolesModel createServiceOrder2User();

	void saveOrder2User(ServiceOrderRelatedRolesModel serviceOrderRelatedRoles);

	Boolean deleteOrder2UserByPks(List<String> pks);

	void associatedPickupInStore(String code, ServiceOrderModel serviceOrderModel);

	List<ServiceOrderModel> getSubOrder(ServiceOrderModel serviceOrderModel);

	List<AlpsCommerceResult> splitOrder(List<SplitServiceOrderData> splitServiceOrderData, String defualtCode);
}
