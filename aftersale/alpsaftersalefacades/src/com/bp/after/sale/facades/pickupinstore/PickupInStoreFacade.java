package com.bp.after.sale.facades.pickupinstore;

import com.bp.after.sale.facades.data.*;
import com.bp.alps.facades.data.DefaultResponse;
import de.hybris.platform.cmsfacades.data.OptionData;

import java.util.List;

public interface PickupInStoreFacade
{

    /**
     * 创建
     * @param pickupInStoreData
     * @return
     */
    DefaultResponse createPickupInStore(PickupInStoreData pickupInStoreData);

    /**
     * 列表
     * @param pickupInStoreListRequest
     * @return
     */
    PickupInStoreListResponse getPickupInStoreList(PickupInStoreListRequest pickupInStoreListRequest);

    PickupInStoreListResponse getPickupInStoreTodayList(PickupInStoreListRequest pickupInStoreListRequest);

    /**
     * 详情
     * @param code
     * @return
     */
    PickupInStoreDetailResponse getPickupInStoreByCode(String code);

    /**
     * 更新
     * @param pickupInStoreData
     * @return
     */
    DefaultResponse savePickupInStore(PickupInStoreData pickupInStoreData);

    /**
     * 车牌号查询
     * @param vehicleNumber
     * @return
     */
    PickupInStoreDetailResponse getPickupInStoreByVehicle(String vehicleNumber);


    /**
     * 获取员工列表
     * @param currentPage
     * @param pagesize
     * @return
     */
    ServiceConsultantListResponse getServiceConsultant(final Integer currentPage, Integer pagesize);


    /**
     * 获取任务状态
     * @return
     */
    List<OptionData> getAllPickupStatus();


    /**
     * 获取到店目的
     * @return
     */
    List<OptionData> getAllPickupInStorePurpose();


    /**
     * 获取车辆状态
     * @return
     */
    List<OptionData> getAllVehicleStatus();
}
