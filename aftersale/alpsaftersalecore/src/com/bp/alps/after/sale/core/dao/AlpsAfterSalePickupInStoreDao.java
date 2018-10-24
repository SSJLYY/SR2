package com.bp.alps.after.sale.core.dao;

import com.bp.alps.after.sale.core.enums.PickupInStoreErrandStatus;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Map;

public interface AlpsAfterSalePickupInStoreDao {
    SearchPageData<PickupInStoreModel> getPickupInStoreByServices(CustomerModel customerModel,Boolean isToday, String sender, String vehicleNumber, PickupInStoreErrandStatus status, OrderStatus workOrderStatus, PageableData pageableData);

    PickupInStoreModel getPickupInStoreByKey(Map<String, Object> key);

    SearchPageData<PickupInStoreModel> getPickupInStore(PageableData pageableData);

}
