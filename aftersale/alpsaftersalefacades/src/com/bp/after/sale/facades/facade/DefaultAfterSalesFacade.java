package com.bp.after.sale.facades.facade;

import com.bp.alps.facades.data.DefaultPageableRequest;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;

public class DefaultAfterSalesFacade
{
    public PageableData populatorPageable(DefaultPageableRequest defaultPageableRequest){
        return populatorPageable(defaultPageableRequest.getCurrentPage(),defaultPageableRequest.getPagesize());
    }

    public PageableData populatorPageable(final Integer currentPage, Integer pagesize){
        final PageableData pageableData = new PageableData();
        pageableData.setPageSize(pagesize!=null?pagesize:100);
        pageableData.setCurrentPage(currentPage!=null?currentPage:0);
        return pageableData;
    }
}
