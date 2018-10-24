package com.bp.alps.after.sale.core.dao.impl;

import com.bp.alps.after.sale.core.enums.PickupInStoreErrandStatus;
import com.bp.alps.after.sale.core.model.PickupInStoreModel;
import com.bp.alps.after.sale.core.dao.AlpsAfterSalePickupInStoreDao;
import com.bp.alps.after.sale.core.model.ServiceOrderModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.commerceservices.search.flexiblesearch.PagedFlexibleSearchService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

public class AlpsAfterSalePickupInStoreDaoImpl extends DefaultGenericDao<PickupInStoreModel> implements AlpsAfterSalePickupInStoreDao {

    @Resource
    private PagedFlexibleSearchService pagedFlexibleSearchService;

    protected static final String GET_PICKUPINSTORE = "SELECT {" + PickupInStoreModel.PK + "}"
            + " FROM {" + PickupInStoreModel._TYPECODE + " as " + PickupInStoreModel._TYPECODE + "}";


    protected static final String GET_PICKUPINSTORE_BY_USER = "SELECT {" + PickupInStoreModel.PK + "}"
            + " FROM {" + PickupInStoreModel._TYPECODE
            + " left join "+ ServiceOrderModel._TYPECODE +" on {"+ServiceOrderModel._TYPECODE+":"+ServiceOrderModel.PK+"}={"+PickupInStoreModel._TYPECODE+":"+PickupInStoreModel.WORKORDER+"} and {"+PickupInStoreModel._TYPECODE+":"+PickupInStoreModel.WORKORDER+"} is not null"
            + "}"
            + " WHERE {" + PickupInStoreModel._TYPECODE + ":" + PickupInStoreModel.SERVICECONSULTANT + "} = ?"+PickupInStoreModel.SERVICECONSULTANT+" ";


    public AlpsAfterSalePickupInStoreDaoImpl(String typecode) {
        super(typecode);
    }


    /**
     *  list By 服务顾问
     * @param customerModel
     * @param pageableData
     * @return
     */
    @Override
    public SearchPageData<PickupInStoreModel> getPickupInStoreByServices(CustomerModel customerModel,Boolean isToday, String sender, String vehicleNumber, PickupInStoreErrandStatus status, OrderStatus workOrderStatus, PageableData pageableData) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(GET_PICKUPINSTORE_BY_USER);
        Map<String, Object> queryParameterMap = new HashMap<>();
        queryParameterMap.put(PickupInStoreModel.SERVICECONSULTANT, customerModel);

        if(status != null){
            queryBuilder.append("AND {"+PickupInStoreModel._TYPECODE+":"+PickupInStoreModel.STATUS+"} = ?"+PickupInStoreModel.STATUS+" ");
            queryParameterMap.put(PickupInStoreModel.STATUS, status);
        }

        if(workOrderStatus != null){
            queryBuilder.append("AND {"+ServiceOrderModel._TYPECODE+":"+ServiceOrderModel.STATUS+"} = ?"+ServiceOrderModel.STATUS+" ");
            queryParameterMap.put(ServiceOrderModel.STATUS, workOrderStatus);
        }

        if(StringUtils.isNotBlank(sender)){
            queryBuilder.append("AND {"+ PickupInStoreModel._TYPECODE + ":" + PickupInStoreModel.SENDER + "} = ?"+PickupInStoreModel.SENDER+" ");
            queryParameterMap.put(PickupInStoreModel.SENDER, sender);
        }

        if(StringUtils.isNotBlank(vehicleNumber)){
            queryBuilder.append("AND {"+ PickupInStoreModel._TYPECODE + ":" + PickupInStoreModel.VEHICLENUMBER + "} = ?"+PickupInStoreModel.VEHICLENUMBER+" ");
            queryParameterMap.put(PickupInStoreModel.VEHICLENUMBER, vehicleNumber);
        }

        /**
         * 今日预约看板
         */
        if(isToday!=null){
            if(StringUtils.isBlank(sender) && StringUtils.isBlank(vehicleNumber) && isToday){
                queryBuilder.append("AND {"+PickupInStoreModel._TYPECODE+ ":"+PickupInStoreModel.ARRIVALTIME + "} >= ?"+PickupInStoreModel.ARRIVALTIME+" ");
                queryParameterMap.put(PickupInStoreModel.ARRIVALTIME, DateFormatUtils.getToday());

                queryBuilder.append("AND {"+PickupInStoreModel._TYPECODE+ ":"+PickupInStoreModel.ARRIVALTIME + "} < ?"+PickupInStoreModel.MODIFIEDTIME+" ");
                queryParameterMap.put(PickupInStoreModel.MODIFIEDTIME, DateFormatUtils.getTomorrow());

                queryBuilder.append("AND {"+PickupInStoreModel._TYPECODE+":"+PickupInStoreModel.STATUS+"} != ?status2 ");
                queryParameterMap.put("status2", PickupInStoreErrandStatus.CLOSE);
            }
        }

        queryBuilder.append(" ORDER BY {"+PickupInStoreModel.ARRIVALTIME+"} DESC");
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryBuilder);
        query.addQueryParameters(queryParameterMap);
        query.setResultClassList(Collections.singletonList(PickupInStoreModel.class));
        return pagedFlexibleSearchService.search(query, pageableData);
    }




    /**
     *  detail By Code
     * @param key
     * @return
     */
    @Override
    public PickupInStoreModel getPickupInStoreByKey(Map<String, Object> key) {
        List<PickupInStoreModel> pickupInStoreModel = find(key);
        return pickupInStoreModel.size()>0?pickupInStoreModel.get(0):null;
    }

    /**
     *list all
     * @return
     */
    @Override
    public SearchPageData<PickupInStoreModel> getPickupInStore(PageableData pageableData) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(GET_PICKUPINSTORE);
        queryBuilder.append(" ORDER BY {"+PickupInStoreModel.ARRIVALTIME+"} DESC");
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryBuilder);
        query.setResultClassList(Collections.singletonList(PickupInStoreModel.class));
        return pagedFlexibleSearchService.search(query, pageableData);
    }



}
