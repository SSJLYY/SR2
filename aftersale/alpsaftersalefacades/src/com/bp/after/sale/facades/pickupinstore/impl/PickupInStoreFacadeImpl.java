package com.bp.after.sale.facades.pickupinstore.impl;

import com.bp.after.sale.facades.constants.AlpsaftersalefacadesConstants;
import com.bp.after.sale.facades.data.*;
import com.bp.after.sale.facades.data.aftersales.PickupInStoreListData;
import com.bp.after.sale.facades.facade.DefaultAfterSalesFacade;
import com.bp.after.sale.facades.pickupinstore.PickupInStoreFacade;
import com.bp.alps.after.sale.core.enums.*;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import com.bp.alps.after.sale.core.service.PickupInStoreServices;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.vehiclecommercefacade.consultant.AlpsConsultantFacade;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public class PickupInStoreFacadeImpl extends DefaultAfterSalesFacade implements PickupInStoreFacade
{
    private static final String TEXT_AFTERSALES_FIND_FAILED ="text.aftersales.find.failed";
    private static final String TEXT_AFTERSALES_FIND_NOTFIND ="text.aftersales.find.notfind";

    @Resource
    private Converter<PickupInStoreData,PickupInStoreModel> pickupInStoreInputConverter;

    @Resource
    private Converter<PickupInStoreModel,PickupInStoreListData> pickupInStoreListOutputConverter;

    @Resource
    private Converter<PickupInStoreModel,PickupInStoreListData> pickupInStoreListFormOutputConverter;

    @Resource
    private Converter<PickupInStoreModel,PickupInStoreListData> pickupInStoreListOutputTodayConverter;

    @Resource
    private Converter<PickupInStoreModel,PickupInStoreData> pickupInStoreOutputConverter;

    @Resource
    private Converter<CustomerModel,ServiceConsultantData> serviceConsultantConverter;

    @Resource
    private PickupInStoreServices pickupInStoreServices;

    @Resource
    private UserService userService;

    @Resource
    private EnumerationService enumerationService;

    @Resource
    private AlpsConsultantFacade alpsConsultantFacade;

    //EnumerationValueModel
    @Resource(name="enumConverter")
    private Converter<HybrisEnumValue,OptionData> enumerationValueModelConverter;


    /**
     *  创建
     * @param pickupInStoreData
     * @return
     */
    @Override
    public DefaultResponse createPickupInStore(PickupInStoreData pickupInStoreData) {
        PickupInStoreModel pickupInStoreModel = null;
        DefaultResponse defaultResponse = new DefaultResponse();
        //data转model
        pickupInStoreModel = pickupInStoreInputConverter.convert(pickupInStoreData);
        try{
            pickupInStoreServices.savePickupInStore(pickupInStoreModel);
            defaultResponse.setSuccess(true);
        }catch (Exception e){
            defaultResponse.setSuccess(false);
            defaultResponse.setMessage(e.getMessage());
        }
        return defaultResponse;
    }



    /**
     * 更新
     * @param pickupInStoreData
     * @return
     */
    @Override
    public DefaultResponse savePickupInStore(PickupInStoreData pickupInStoreData) {
        DefaultResponse defaultResponse = new DefaultResponse();
        PickupInStoreModel pickupInStoreModel = null;

        //获取原始model
        pickupInStoreModel = pickupInStoreServices.getPickupInStoreByCode(pickupInStoreData.getCode());
        PickupInStoreModel newPickupInStoreModel =  pickupInStoreInputConverter.convert(pickupInStoreData,pickupInStoreModel);

        try {
            pickupInStoreServices.updatePickupInStore(newPickupInStoreModel);
        } catch (Exception e) {
            defaultResponse.setSuccess(false);
            defaultResponse.setMessage(e.getMessage());
            return defaultResponse;
        }

        defaultResponse.setSuccess(true);
        return defaultResponse;
    }


    /**
     * 车牌号查询
     * @param vehicleNumber
     * @return
     */
    @Override
    public PickupInStoreDetailResponse getPickupInStoreByVehicle(String vehicleNumber) {
        PickupInStoreDetailResponse pickupInStoreDetailResponse = new PickupInStoreDetailResponse();

        if(StringUtils.isNotBlank(vehicleNumber)){
            PickupInStoreModel pickupInStoreModel = pickupInStoreServices.getPickupInStoreByVehicle(vehicleNumber);
            if(pickupInStoreModel!=null){
                //model=>data
                PickupInStoreData pickupInStoreData = pickupInStoreOutputConverter.convert(pickupInStoreModel);
                pickupInStoreDetailResponse.setPickupInStoreList(Collections.singletonList(pickupInStoreData));
                pickupInStoreDetailResponse.setSuccess(true);
            }
        }

        pickupInStoreDetailResponse.setSuccess(false);
        pickupInStoreDetailResponse.setMessageCode(TEXT_AFTERSALES_FIND_NOTFIND);
        return pickupInStoreDetailResponse;
    }


    /**
     * 获取员工列表
     * @param currentPage
     * @param pagesize
     * @return
     */
    @Override
    public ServiceConsultantListResponse getServiceConsultant(final Integer currentPage, Integer pagesize) {
        SearchPageData<CustomerModel> searchPageData = alpsConsultantFacade.getUserByGroupUid(currentPage, pagesize,  AlpsaftersalefacadesConstants.Customer.serviceConsultantGroup);
        List<ServiceConsultantData> serviceConsultantDatas = serviceConsultantConverter.convertAll(searchPageData.getResults());

        ServiceConsultantListResponse serviceConsultantListResponse = new ServiceConsultantListResponse();
        serviceConsultantListResponse.setSuccess(true);
        serviceConsultantListResponse.setServiceList(serviceConsultantDatas);
        return serviceConsultantListResponse;
    }


    /**
     * 获取任务状态
     * @return
     */
    @Override
    public List<OptionData> getAllPickupStatus() {
        return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(PickupInStoreErrandStatus.class));
    }

    /**
     * 获取到店目的
     * @return
     */
    @Override
    public List<OptionData> getAllPickupInStorePurpose() {
        return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(PickupInStorePurpose.class));
    }

    /**
     * 获取车辆状态
     * @return
     */
    @Override
    public List<OptionData> getAllVehicleStatus() {
        return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(PickupInStoreVehicleStatus.class));
    }

    /**
     *  接车列表
     * @param pickupInStoreListRequest
     * @return
     */
    @Override
    public PickupInStoreListResponse getPickupInStoreList(PickupInStoreListRequest pickupInStoreListRequest) {
        PickupInStoreListResponse pickupInStoreListResponse = new PickupInStoreListResponse();
        SearchPageData<PickupInStoreModel> searchPageData = searchPickupInStore(pickupInStoreListRequest);

        if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
            //model=>data
            List<PickupInStoreListData> pickupInStoreListData = pickupInStoreListFormOutputConverter.convertAll(searchPageData.getResults());
            pickupInStoreListResponse.setPickupInStoreList(pickupInStoreListData);
            pickupInStoreListResponse.setTotalPage(searchPageData.getPagination() != null ? searchPageData.getPagination().getNumberOfPages() : 0);
        }
        pickupInStoreListResponse.setSuccess(true);
        return pickupInStoreListResponse;
    }

    /**
     *  今日预约列表
     * @param pickupInStoreListRequest
     * @return
     */
    public PickupInStoreListResponse getPickupInStoreTodayList(PickupInStoreListRequest pickupInStoreListRequest) {
        PickupInStoreListResponse pickupInStoreListResponse = new PickupInStoreListResponse();
        SearchPageData<PickupInStoreModel> searchPageData = searchPickupInStore(pickupInStoreListRequest);
        if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
            //model=>data
            List<PickupInStoreListData> pickupInStoreListData = pickupInStoreListOutputTodayConverter.convertAll(searchPageData.getResults());
            pickupInStoreListResponse.setPickupInStoreList(pickupInStoreListData);
            pickupInStoreListResponse.setTotalPage(searchPageData.getPagination() != null ? searchPageData.getPagination().getNumberOfPages() : 0);
        }
        pickupInStoreListResponse.setSuccess(true);
        return pickupInStoreListResponse;
    }

    protected SearchPageData<PickupInStoreModel> searchPickupInStore(PickupInStoreListRequest pickupInStoreListRequest){
        if (null!=userService.getCurrentUser()) {
            //获取model
            CustomerModel customerModel = (CustomerModel)userService.getCurrentUser();
            //获取分页参数
            final PageableData pageableData = populatorPageable(pickupInStoreListRequest);

            PickupInStoreErrandStatus status = null;
            if(StringUtils.isNotBlank(pickupInStoreListRequest.getStatus())){
                status = PickupInStoreErrandStatus.valueOf(pickupInStoreListRequest.getStatus());
            }

            OrderStatus workOrderStatus = null;
            if(StringUtils.isNotBlank(pickupInStoreListRequest.getWorkOrderStatus())){
                workOrderStatus = OrderStatus.valueOf(pickupInStoreListRequest.getWorkOrderStatus());
            }

            //获取指定顾问的list
            SearchPageData<PickupInStoreModel> searchPageData = pickupInStoreServices
                  .getPickupInStoreByServices(customerModel,
                        pickupInStoreListRequest.getIsToday(),
                        pickupInStoreListRequest.getSender(),
                        pickupInStoreListRequest.getVehicleNumber(),
                        status,
                        workOrderStatus,
                        pageableData);
            return searchPageData;
        }
        return null;
    }

    /**
     * detail by code
     * @param code
     * @return
     */
    @Override
    public PickupInStoreDetailResponse getPickupInStoreByCode(String code) {
        PickupInStoreDetailResponse pickupInStoreDetailResponse = new PickupInStoreDetailResponse();
        if(StringUtils.isNotBlank(code))
        {
            PickupInStoreModel pickupInStoreModel = pickupInStoreServices.getPickupInStoreByCode(code);
            if (pickupInStoreModel != null)
            {
                //model=>data
                PickupInStoreData pickupInStoreData = pickupInStoreOutputConverter.convert(pickupInStoreModel);
                pickupInStoreDetailResponse.setPickupInStoreList(Collections.singletonList(pickupInStoreData));
                pickupInStoreDetailResponse.setSuccess(true);
                return pickupInStoreDetailResponse;
            }
        }

        pickupInStoreDetailResponse.setSuccess(false);
        pickupInStoreDetailResponse.setMessageCode(TEXT_AFTERSALES_FIND_FAILED);
        return pickupInStoreDetailResponse;
    }
}

