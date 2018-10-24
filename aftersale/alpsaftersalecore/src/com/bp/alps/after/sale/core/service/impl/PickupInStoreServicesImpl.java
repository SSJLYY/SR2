package com.bp.alps.after.sale.core.service.impl;

import com.bp.alps.after.sale.core.dao.AlpsAfterSalePickupInStoreDao;
import com.bp.alps.after.sale.core.dao.AlpsafterSaleUserListDao;
import com.bp.alps.after.sale.core.enums.PickupInStoreErrandStatus;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import com.bp.alps.after.sale.core.service.PickupInStoreServices;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public class PickupInStoreServicesImpl extends DefaultAlpsaftersalecoreService implements PickupInStoreServices
{
    private static final Logger LOG = Logger.getLogger(PickupInStoreServicesImpl.class);

    private String keyPrefix;

    @Resource
    private AlpsAfterSalePickupInStoreDao alpsAfterSalePickupInStoreDao;

    @Resource
    private AlpsafterSaleUserListDao alpsafterSaleUserListDao;

    @Resource
    private ModelService modelService;


    @Resource
    private KeyGenerator pickupInStoreGenerator;

    /**
     *  创建新的到店接车
     * @param pickupInStoreModel
     * @return
     */
    @Override
    public PickupInStoreModel savePickupInStore(PickupInStoreModel pickupInStoreModel)
    {
        List<CustomerModel> results = this.getCustomersResults();

        //判空
        if (results.isEmpty()){
            LOG.info("Customer Is Empty!");
        }
        //创建:如果没有code并且status:new  那么当前等待车辆数加1
        if(pickupInStoreModel.getCode()==null){
            //循环遍历customerID
            for (CustomerModel customer:results) {
                //排除匿名用户
                if(customer.getCurrentWaitingVehicles()!=null){
                    if(customer.getCustomerID()==pickupInStoreModel.getServiceConsultant().getCustomerID()){
                        customer.setCurrentWaitingVehicles(customer.getCurrentWaitingVehicles()+1);
                        modelService.save(customer);
                    }
                }
            }
        }
        //创建一个code
        generateCode(pickupInStoreModel);
        return updatePickupInStore(pickupInStoreModel);
    }

    private List<CustomerModel> getCustomersResults() {
        final PageableData pageableData = populatorPageable();
        SearchPageData<CustomerModel> data = alpsafterSaleUserListDao.getAllCustomerId(pageableData);
        List<CustomerModel> results = data.getResults();
        return results;
    }

    /**
     * 更新
     * @param pickupInStoreModel
     * @return
     */
    @Override
    public PickupInStoreModel updatePickupInStore(PickupInStoreModel pickupInStoreModel)
    {

        List<CustomerModel> results = this.getCustomersResults();
        String customerID = pickupInStoreModel.getServiceConsultant().getCustomerID();
        //已开单
        if("billed".equals(pickupInStoreModel.getStatus().toString())){
            for (CustomerModel customer:results) {
                //排除匿名用户
                if(customer.getCurrentWaitingVehicles()!=null){
                    if(customer.getCustomerID()!=null){
                        if(customer.getCustomerID().equals(customerID)){
                            //等待车辆-1
                            customer.setCurrentWaitingVehicles(customer.getCurrentWaitingVehicles()-1);
                            modelService.save(customer);
                            //对应id的customer今日接车数加1
                            customer.setNOfPickupToday(customer.getNOfPickupToday()+1);
                            modelService.save(customer);
                        }
                    }
                }
            }
        }
        //关闭
        if("close".equals(pickupInStoreModel.getStatus().toString())){
            for (CustomerModel customer:results) {
                //排除匿名用户
                if(customer.getCurrentWaitingVehicles()!=null){
                    if(customer.getCustomerID()!=null) {
                        if (customer.getCustomerID().equals(customerID)) {
                            //等待车辆-1
                            customer.setCurrentWaitingVehicles(customer.getCurrentWaitingVehicles() - 1);
                            modelService.save(customer);
                        }
                    }
                }

            }
        }

        modelService.save(pickupInStoreModel);
        return pickupInStoreModel;
    }




    protected PickupInStoreModel generateCode(PickupInStoreModel pickupInStoreModel)
    {
        String keycode = String.valueOf(pickupInStoreGenerator.generate());
        pickupInStoreModel.setCode(keyPrefix+keycode);
        return pickupInStoreModel;
    }



    /**
     *   list by 服务顾问
     * @param customerModel
     * @param pageableData
     * @return
     */
    @Override
    public SearchPageData<PickupInStoreModel> getPickupInStoreByServices(CustomerModel customerModel, Boolean isToday, String sender, String vehicleNumber, PickupInStoreErrandStatus status, OrderStatus workOrderStatus, PageableData pageableData) {
        return alpsAfterSalePickupInStoreDao.getPickupInStoreByServices(customerModel, isToday, sender, vehicleNumber, status, workOrderStatus, pageableData);
    }


    /**
     * details by code
     * @param code
     * @return
     */
    @Override
    public PickupInStoreModel getPickupInStoreByCode(String code) {
        return alpsAfterSalePickupInStoreDao.getPickupInStoreByKey(Collections.singletonMap(PickupInStoreModel.CODE, code));
    }

    /**
     * 车牌号查询
     * @param vehicleNumber
     * @return
     */
    @Override
    public PickupInStoreModel getPickupInStoreByVehicle(String vehicleNumber) {
        return alpsAfterSalePickupInStoreDao.getPickupInStoreByKey(Collections.singletonMap(PickupInStoreModel.VEHICLENUMBER, vehicleNumber));
    }

    /**
     * 获取员工列表
     * @param groupKeys
     * @param pageableData
     * @return
     */
    @Override
    public SearchPageData<CustomerModel> findAllCustomersByGroups(List<String> groupKeys, PageableData pageableData) {
        return alpsafterSaleUserListDao.findAllCustomersByGroups(groupKeys,pageableData);
    }


    /**
     * 获取全部列表
     * @return
     */
    @Override
    public SearchPageData<PickupInStoreModel> getPickupInStore(PageableData pageableData) {
        return alpsAfterSalePickupInStoreDao.getPickupInStore(pageableData);
    }


    public String getKeyPrefix()
    {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix){
        this.keyPrefix=keyPrefix;
    }

    public PageableData populatorPageable(){
        final PageableData pageableData = new PageableData();
        pageableData.setPageSize(100);
        pageableData.setCurrentPage(0);
        return pageableData;
    }


}
