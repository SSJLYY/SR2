package com.bp.alps.core.dao;

import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.model.QuotationInfoModel;

import java.util.List;
import java.util.Map;


public interface QuotationDao
{
	QuotationInfoModel getQuotaionInfoByCode(final String code);

	QuotationInfoModel getConfirmQuotation(final String code);

	List<QuotationInfoModel> getQuotationListByParameter(Map<String,Object> paramenter);

	void setQuotationStatusToUnconfirmByOpportunity(OpportunityModel Opportunity);
}
