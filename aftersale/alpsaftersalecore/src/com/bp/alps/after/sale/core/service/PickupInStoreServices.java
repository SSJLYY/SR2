package com.bp.alps.after.sale.core.service;

import com.bp.alps.after.sale.core.enums.PickupInStoreErrandStatus;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface PickupInStoreServices
{

    /**
     * 创建
     * @param pickupInStoreModel
     * @return
     */
    PickupInStoreModel savePickupInStore(PickupInStoreModel pickupInStoreModel);


    /**
     * 条件列表
     * @param customerModel
     * @param pageableData
     * @return
     */
    SearchPageData<PickupInStoreModel> getPickupInStoreByServices(CustomerModel customerModel, Boolean isToday, String sender, String vehicleNumber, PickupInStoreErrandStatus status, OrderStatus workOrderStatus, PageableData pageableData);

    /**
     * 详情
     * @param code
     * @return
     */
    PickupInStoreModel getPickupInStoreByCode(String code);

    /**
     * 全部列表
     * @param pageableData
     * @return
     */
    SearchPageData<PickupInStoreModel> getPickupInStore(PageableData pageableData);

    /**
     * 更新
     * @param pickupInStoreModel
     * @return
     */
    PickupInStoreModel updatePickupInStore(PickupInStoreModel pickupInStoreModel);

    /**
     * 车牌号查询
     * @param vehicleNumber
     * @return
     */
    PickupInStoreModel getPickupInStoreByVehicle(String vehicleNumber);

    /**
     * 获取员工列表
     * @param groupKeys
     * @param pageableData
     * @return
     */
    SearchPageData<CustomerModel> findAllCustomersByGroups(final List<String> groupKeys, final PageableData pageableData);
}
