package com.bp.alps.after.sale.storefront.controllers.pages;

import com.bp.after.sale.facades.data.StockLevelData;
import com.bp.after.sale.facades.data.WorkhoursData;
import com.bp.after.sale.facades.facade.WorkHoursConfigurationFacade;
import com.bp.after.sale.facades.stocklevel.StockLevelFacade;
import com.bp.alps.facades.data.category.CategoryListData;
import com.bp.alps.facades.data.product.ProductData;
import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.product.ProductSearchResponse;
import com.bp.alps.vehiclecommercefacade.product.search.SalesProductSearchFacade;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/configuration")
public class ConfigurationController extends AbstractPageController
{
    @Resource
    private SalesProductSearchFacade salesProductSearchFacade;

    @Resource
    private StockLevelFacade stockLevelFacade;

    @Resource
    private WorkHoursConfigurationFacade workHoursConfigurationFacade;

    private static final String COMPONENTS_LIST = "componentsList";
    private static final String MAINTENANCE_PROJECT_LIST = "maintenanceProjectList";
    private static final String SERVICE_PACKAGE_LIST = "servicePackageList";

    /**
     * 零部件
     * @param request
     * @param productSearchRequest
     * @param model
     * @return
     * @throws CMSItemNotFoundException
     */
    @RequestMapping(value = "/components", method = {RequestMethod.GET, RequestMethod.POST})
    public String getComponents(final HttpServletRequest request, final ProductSearchRequest productSearchRequest, final Model model) throws CMSItemNotFoundException
    {
        productSearchRequest.setCategoryCode("components");
        ProductSearchResponse productSearchResponse = salesProductSearchFacade.getProductSearchResult(productSearchRequest);
        List<CategoryListData> categorylist = productSearchResponse.getCategoryList();
        List<ProductData> productList = productSearchResponse.getProductList();
        model.addAttribute("productList",productList);
        model.addAttribute("categorylist",categorylist);

        storeCmsPageInModel(model, getContentPageForLabelOrId(COMPONENTS_LIST));
        return getViewForPage(model);
    }

    @RequestMapping(value="/component/stock", method = RequestMethod.GET)
    public String getComponentStock(final String productCode, final Model model){
        Collection<StockLevelData> stocklevels = stockLevelFacade.getAllStockLevelByProductCode(productCode);
        model.addAttribute("stocklevels",stocklevels);
        return "pages/stock/list";
    }

    @RequestMapping(value="/maintenanceProject/configuration", method = RequestMethod.GET)
    public String getMaintenanceProductConfig(final String productCode, final Model model){
        List<WorkhoursData> workhoursData = workHoursConfigurationFacade.getWorkHoursDataByProductCode(productCode);
        model.addAttribute("workhoursData",workhoursData);
        return "pages/workhours/list";
    }

    /**
     * 维修项目
     * @param request
     * @param productSearchRequest
     * @param model
     * @return
     * @throws CMSItemNotFoundException
     */
    @RequestMapping(value = "/maintenanceProject", method = {RequestMethod.GET, RequestMethod.POST})
    public String getMaintenanceProject(final HttpServletRequest request, final ProductSearchRequest productSearchRequest, final Model model) throws CMSItemNotFoundException
    {
        productSearchRequest.setCategoryCode("maintenanceproject");
        ProductSearchResponse productSearchResponse = salesProductSearchFacade.getProductSearchResult(productSearchRequest);
        List<CategoryListData> categorylist = productSearchResponse.getCategoryList();
        List<ProductData> productList = productSearchResponse.getProductList();
        model.addAttribute("productList",productList);
        model.addAttribute("categorylist",categorylist);

        storeCmsPageInModel(model, getContentPageForLabelOrId(MAINTENANCE_PROJECT_LIST));
        return getViewForPage(model);
    }


    /**
     * 服务包
     * @param request
     * @param productSearchRequest
     * @param model
     * @return
     * @throws CMSItemNotFoundException
     */
    @RequestMapping(value = "/servicePackage", method = {RequestMethod.GET, RequestMethod.POST})
    public String getServicePackage(final HttpServletRequest request, final ProductSearchRequest productSearchRequest, final Model model) throws CMSItemNotFoundException
    {
        productSearchRequest.setCategoryCode("servicepackage");
        ProductSearchResponse productSearchResponse = salesProductSearchFacade.getProductSearchResult(productSearchRequest);
        List<CategoryListData> categorylist = productSearchResponse.getCategoryList();
        List<ProductData> productList = productSearchResponse.getProductList();
        model.addAttribute("productList",productList);
        model.addAttribute("categorylist",categorylist);

        storeCmsPageInModel(model, getContentPageForLabelOrId(SERVICE_PACKAGE_LIST));
        return getViewForPage(model);
    }





}
