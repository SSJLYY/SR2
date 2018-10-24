package com.bp.alps.after.sale.core.service;

import com.bp.alps.after.sale.core.dao.AlpsafterSaleUserListDao;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

public class AlpsAfterSaleCountNumberOfPickupsJob extends AbstractJobPerformable<CronJobModel>
{
    private static final Logger LOG = Logger.getLogger(AlpsAfterSaleCountNumberOfPickupsJob.class);

    @Resource
    private AlpsafterSaleUserListDao alpsafterSaleUserListDao;

    @Resource
    private ModelService modelService;


    @Override
    public PerformResult perform(CronJobModel cronJobModel) {

        final PageableData pageableData = populatorPageable();
        SearchPageData<CustomerModel> data = alpsafterSaleUserListDao.getAllCustomerId(pageableData);
        List<CustomerModel> results = data.getResults();
        //判空
        if (results.isEmpty())
        {
            LOG.info("Customer Is Empty!");
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
            //循环遍历customerID
            for (CustomerModel customer:results) {
                customer.setCurrentWaitingVehicles(0);
                customer.setNOfPickupToday(0);
                modelService.save(customer);
            }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    public PageableData populatorPageable(){
        final PageableData pageableData = new PageableData();
        pageableData.setPageSize(100);
        pageableData.setCurrentPage(0);
        return pageableData;
    }
}
