package com.bp.alps.core.dao.impl;

import com.bp.alps.core.dao.QuotationDao;
import com.bp.alps.core.model.SalesOrderAttributeModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.model.QuotationInfoModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class QuotationDaoImpl extends DefaultGenericDao<QuotationInfoModel> implements QuotationDao
{
	@Resource
	private ModelService modelService;

	protected static final String BASE_GET_QUTATION_BY_OPPO = "SELECT {" + QuotationInfoModel.PK + "}"
			+ " FROM {" + QuotationInfoModel._TYPECODE
			+" join " + SalesOrderAttributeModel._TYPECODE + " on "
			+ " {" + QuotationInfoModel._TYPECODE + ":" + QuotationInfoModel.SALESATTRIBUTE + "} = {" + SalesOrderAttributeModel._TYPECODE + ":" + SalesOrderAttributeModel.PK + "} ";

	protected static final String GET_CONFIRM_QUTATION_BY_OPPO = BASE_GET_QUTATION_BY_OPPO
			+" join " + OpportunityModel._TYPECODE + " on"
			+ " {" + SalesOrderAttributeModel._TYPECODE + ":" + SalesOrderAttributeModel.OPPORTUNITY + "} = {" + OpportunityModel._TYPECODE + ":" + OpportunityModel.PK + "}} "
			+ "  WHERE {" + OpportunityModel._TYPECODE + ":" + OpportunityModel.CODE + "} = ?oppocode "
			+ "  AND {" + QuotationInfoModel._TYPECODE + ":" + QuotationInfoModel.STATUS + "} = ?quotationstatus";

	protected static final String GET_QUTATION_BY_OPPO = BASE_GET_QUTATION_BY_OPPO
			+ " } "
			+ "  WHERE {" + SalesOrderAttributeModel._TYPECODE + ":" + SalesOrderAttributeModel.OPPORTUNITY + "} = ?opportunity "
			+ " order by {"+QuotationInfoModel.CODE+"} desc";

	public QuotationDaoImpl(String typeCode){
		super(typeCode);
	}

	public QuotationInfoModel getQuotaionInfoByCode(final String code){
		List<QuotationInfoModel> quotationInfoModelList = find(Collections.singletonMap(QuotationInfoModel.CODE, code));
		if(CollectionUtils.isNotEmpty(quotationInfoModelList)) return quotationInfoModelList.get(0);
		return null;
	}

	public QuotationInfoModel getConfirmQuotation(final String oppoCode){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(GET_CONFIRM_QUTATION_BY_OPPO);
		query.addQueryParameter("oppocode",oppoCode);
		query.addQueryParameter("quotationstatus",OrderStatus.CONFIRM);
		SearchResult<QuotationInfoModel> searchResult = getFlexibleSearchService().search(query);
		return CollectionUtils.isNotEmpty(searchResult.getResult()) ? searchResult.getResult().get(0) : null;
	}

	public void setQuotationStatusToUnconfirmByOpportunity(OpportunityModel Opportunity){
		final FlexibleSearchQuery query = new FlexibleSearchQuery(GET_QUTATION_BY_OPPO);
		query.addQueryParameter("opportunity",Opportunity);
		SearchResult<QuotationInfoModel> searchResult = getFlexibleSearchService().search(query);
		if(CollectionUtils.isNotEmpty(searchResult.getResult()))
		{
			searchResult.getResult().stream().forEach(quotationInfoModel -> {
				quotationInfoModel.setStatus(OrderStatus.CREATED);
			});
			modelService.saveAll(searchResult.getResult());
		}
	}

	public List<QuotationInfoModel> getQuotationListByParameter(Map<String,Object> paramenter){
		StringBuilder joinString = new StringBuilder();
		StringBuilder whereString = new StringBuilder();
		if(paramenter.get(OpportunityModel._TYPECODE+OpportunityModel.CODE)!=null){
			joinString.append(" join "+OpportunityModel._TYPECODE+" on {" + SalesOrderAttributeModel._TYPECODE + ":" + SalesOrderAttributeModel.OPPORTUNITY + "} = {" + OpportunityModel._TYPECODE + ":" + OpportunityModel.PK + "}");
			whereString.append(" AND {"+OpportunityModel._TYPECODE+":"+OpportunityModel.CODE+"}=?"+OpportunityModel._TYPECODE+OpportunityModel.CODE);
		}

		if(paramenter.get(FollowOpportunityModel._TYPECODE+ FollowOpportunityModel.CODE)!=null){
			joinString.append(" join "+FollowOpportunityModel._TYPECODE+" on {"+FollowOpportunityModel._TYPECODE+":"+FollowOpportunityModel.PK+"}={"+SalesOrderAttributeModel._TYPECODE+":"+SalesOrderAttributeModel.FOLLOWOPPORTUNITY+"}");
			whereString.append(" AND {"+FollowOpportunityModel._TYPECODE+":"+FollowOpportunityModel.CODE+"}=?"+FollowOpportunityModel._TYPECODE+FollowOpportunityModel.CODE);
		}

		StringBuilder sql = new StringBuilder(BASE_GET_QUTATION_BY_OPPO);
		sql.append(joinString).append(" }").append(" where 1=1 ").append(whereString).append(" ORDER BY {"+QuotationInfoModel._TYPECODE+":"+QuotationInfoModel.CREATIONTIME+"} DESC");
		final FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
		query.addQueryParameters(paramenter);
		SearchResult<QuotationInfoModel> searchResult = getFlexibleSearchService().search(query);
		return searchResult.getResult();
	}
}
