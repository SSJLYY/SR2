package com.bp.alps.facades.quotation;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.quotation.*;


public interface QuotationFacade
{
	CreateQuotationResponse createQuotaion(final QuotationData quotaionData);

	QuotationDetailsResponse getQuotationByCode(final String code);

	QuotationDetailsResponse getConfirmQuotation(final String oppoCode);

	QuotationListResponse getQuotationList(final String oppoCode, final String followOppoCode);

	DefaultResponse updateStatus(final String quotationCode, final String status);

	DefaultResponse customerConfirmAndSaveInfo(final QuotationConfirmFrom quotationConfirmFrom);

	SendCustomerToConfirmResponse sendCustomerToConfirm(final String qutationCode);
}
