package com.bp.alps.vehiclecommercefacade.product.search.Impl;

import com.bp.alps.facades.data.product.ProductSearchRequest;
import com.bp.alps.facades.data.product.ProductSearchResponse;
import com.bp.alps.vehiclecommercefacade.constants.VehiclecommercefacadeConstants;
import com.bp.alps.vehiclecommercefacade.facade.DefaultAlpsCommerceFacade;
import com.bp.alps.vehiclecommercefacade.product.search.SalesProductSearchFacade;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commercefacades.search.solrfacetsearch.impl.DefaultSolrProductSearchFacade;
import de.hybris.platform.commerceservices.search.ProductSearchService;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryTermData;
import de.hybris.platform.commerceservices.threadcontext.ThreadContextService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class SalesProductSearchFacadeImpl<ITEM extends ProductData> extends DefaultSolrProductSearchFacade implements SalesProductSearchFacade
{
	@Resource
	private CategoryService categoryService;

	@Resource
	private Converter<CategoryModel, CategoryData> categoryConverter;

	@Resource
	private Converter<SearchPageData, ProductSearchResponse> productSearchResponseConverter;

	private final SearchQueryData searchQueryData = new SearchQueryData();

	public ProductSearchResponse getProductSearchResult(ProductSearchRequest productSearchRequest){
		if(StringUtils.isBlank(productSearchRequest.getCategoryCode())){
			productSearchRequest.setCategoryCode(Config.getString(VehiclecommercefacadeConstants.Store.ALPS_CATEGORY_CODE_KEY, VehiclecommercefacadeConstants.Store.ALPS_CATEGORY_CODE));
		}

		final PageableData pageableData = populatorPageable(productSearchRequest.getCurrentPage(), productSearchRequest.getPagesize());
		SearchPageData searchPageData = categorySearch(productSearchRequest, pageableData);
		return productSearchResponseConverter.convert(searchPageData);
	}

	protected SolrSearchQueryData decodeState(ProductSearchRequest productSearchRequest)
	{
		final SolrSearchQueryData searchQueryData = new SolrSearchQueryData();
		searchQueryData.setCategoryCode(productSearchRequest.getCategoryCode());
		StringBuilder builder = new StringBuilder(productSearchRequest.getSearchText()!=null?productSearchRequest.getSearchText():"");
		if(StringUtils.isNotBlank(productSearchRequest.getCode()))
		{
			builder.append(productSearchRequest.getCode());
		}
		if(StringUtils.isNotBlank(productSearchRequest.getName()))
		{
			builder.append(productSearchRequest.getName());
		}
		searchQueryData.setFreeTextSearch(builder.toString());

		List<SolrSearchQueryTermData> termList = new LinkedList<>();

		if(StringUtils.isNotBlank(productSearchRequest.getSpecificationModel()))
		{
			SolrSearchQueryTermData specificationModelTermData = new SolrSearchQueryTermData();
			specificationModelTermData.setKey("specificationModel");
			specificationModelTermData.setValue(productSearchRequest.getSpecificationModel());
			termList.add(specificationModelTermData);
		}

		if(StringUtils.isNotBlank(productSearchRequest.getServicePackStatus()))
		{
			SolrSearchQueryTermData servicePackStatusTermData = new SolrSearchQueryTermData();
			servicePackStatusTermData.setKey("servicePackStatus");
			servicePackStatusTermData.setValue(productSearchRequest.getServicePackStatus());
			termList.add(servicePackStatusTermData);
		}

		if(StringUtils.isNotBlank(productSearchRequest.getMaterialType()))
		{
			SolrSearchQueryTermData materialTypeTermData = new SolrSearchQueryTermData();
			materialTypeTermData.setKey("materialType");
			materialTypeTermData.setValue(productSearchRequest.getMaterialType());
			termList.add(materialTypeTermData);
		}

		if(StringUtils.isNotBlank(productSearchRequest.getMaintenanceItemCode()))
		{
			SolrSearchQueryTermData maintenanceItemCodeTermData = new SolrSearchQueryTermData();
			maintenanceItemCodeTermData.setKey("maintenanceItemCode");
			maintenanceItemCodeTermData.setValue(productSearchRequest.getMaintenanceItemCode());
			termList.add(maintenanceItemCodeTermData);
		}

		if(StringUtils.isNotBlank(productSearchRequest.getArtificialMainType()))
		{
			SolrSearchQueryTermData artificialMainTypeTermData = new SolrSearchQueryTermData();
			artificialMainTypeTermData.setKey("artificialMainType");
			artificialMainTypeTermData.setValue(productSearchRequest.getArtificialMainType());
			termList.add(artificialMainTypeTermData);
		}
		searchQueryData.setFilterTerms(termList);
		return searchQueryData;
	}

	public ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData> categorySearch(
			ProductSearchRequest productSearchRequest, PageableData pageableData){

		Assert.notNull(productSearchRequest, "productSearchRequest must not be null.");

		return getThreadContextService().executeInContext(
				new ThreadContextService.Executor<ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>, ThreadContextService.Nothing>()
				{
					@Override
					public ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData> execute()
					{
						SolrSearchQueryData solrSearchQueryData = decodeState(productSearchRequest);
						ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel> productCategorySearchPageData = getProductSearchService().searchAgain(solrSearchQueryData, pageableData);
						return getProductCategorySearchPageConverter().convert(productCategorySearchPageData);
					}
				});
	}

	public List<CategoryData> getSubCategoriesForCode(final String code){
		CategoryModel categoryModel = categoryService.getCategoryForCode(code);
		if(categoryModel!=null)
		{
			Collection<CategoryModel> categoryModels = categoryModel.getAllSubcategories();
			List<CategoryData> categoryDataList = categoryConverter.convertAll(categoryModels);
			return categoryDataList;
		}
		return new ArrayList<CategoryData>();
	}

	public PageableData populatorPageable(final Integer currentPage, Integer pagesize){
		final PageableData pageableData = new PageableData();
		pageableData.setPageSize(pagesize!=null?pagesize:20);
		pageableData.setCurrentPage(currentPage!=null?currentPage:0);
		return pageableData;
	}

	protected ProductSearchService<SolrSearchQueryData, SearchResultValueData, ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>> getProductSearchService()
	{
		return super.getProductSearchService();
	}

	protected Converter<ProductCategorySearchPageData<SolrSearchQueryData, SearchResultValueData, CategoryModel>, ProductCategorySearchPageData<SearchStateData, ITEM, CategoryData>> getProductCategorySearchPageConverter()
	{
		return super.getProductCategorySearchPageConverter();
	}
}
