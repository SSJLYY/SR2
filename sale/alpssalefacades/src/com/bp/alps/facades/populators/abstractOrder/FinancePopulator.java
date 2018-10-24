package com.bp.alps.facades.populators.abstractOrder;

import com.bp.alps.core.model.FinanceInfoModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


public class FinancePopulator implements Populator<QuotationData, FinanceInfoModel>
{
	@Resource
	private ModelService modelService;

	@Resource
	private QuotationService quotationService;

	@Override
	public void populate(QuotationData quotationData, FinanceInfoModel financeInfoModel) throws ConversionException
	{
		financeInfoModel.setFinanceCompany(quotationData.getFinanceCompany());
		financeInfoModel.setFinanceCycle(quotationData.getFinanceCycle());
		financeInfoModel.setFinanceProduct(quotationData.getFinanceProduct());
		financeInfoModel.setFinanceType(quotationData.getFinanceType());
		financeInfoModel.setUnitPrice(quotationData.getFinanceUnitPrice()!=null?quotationData.getFinanceUnitPrice():0d);
		financeInfoModel.setServicePrice(quotationData.getFinanceServiceCharge()!=null?quotationData.getFinanceServiceCharge():0d);
		if(quotationData.getFinanceStartTime()!=null) financeInfoModel.setStartTime(DateFormatUtils.getDate("datetime", quotationData.getFinanceStartTime()));
		financeInfoModel.setRate(quotationData.getFinanceRate());
		financeInfoModel.setMortgage(quotationData.getFinanceMortgage());
		financeInfoModel.setDescription(quotationData.getFinanceRemark());
	}
}
