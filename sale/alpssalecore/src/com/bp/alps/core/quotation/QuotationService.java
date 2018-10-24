package com.bp.alps.core.quotation;

import com.bp.alps.core.data.order.AlpsCommerceEntryResult;
import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.core.data.order.AlpsCommercePlaceOrderParameter;
import com.bp.alps.core.data.order.AlpsCommerceResult;
import com.bp.alps.core.model.FinanceInfoModel;
import com.bp.alps.core.model.LicensePlateInfoModel;
import com.bp.alps.core.model.UsedcarInfoModel;
import com.bp.alps.core.service.AlpssalecoreService;
import com.bp.alps.core.model.QuotationInfoModel;

import java.util.List;


public interface QuotationService extends AlpssalecoreService
{
	QuotationInfoModel getQuotationInfoByCode(final String code);

	QuotationInfoModel getConfirmQuotation(final String oppoCode);

	QuotationInfoModel createQuotaion();

	List<QuotationInfoModel> getQuotationList(final String oppoCode, final String followOppoCode);

	void saveQuoaion(QuotationInfoModel quotaion);

	void saveUsedCarInfo(UsedcarInfoModel usedcarInfoModel);

	void saveFinanceInfo(FinanceInfoModel financeInfoModel);

	void saveLicensePlateInfo(LicensePlateInfoModel licensePlateInfoModel);

	void setQuotationStatusToConfrim(QuotationInfoModel quotationInfoModel);

	AlpsCommerceResult placeQuotation(AlpsCommercePlaceOrderParameter alpsCommercePlaceOrderParameter);
}
