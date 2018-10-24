package com.bp.alps.facades.populators.abstractOrder.output;

import com.bp.alps.core.model.FinanceInfoModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


public class FinanceOutputPopulator implements Populator<FinanceInfoModel, QuotationData>
{
	@Resource
	private ModelService modelService;

	@Resource
	private QuotationService quotationService;

	@Override
	public void populate(FinanceInfoModel financeInfoModel, QuotationData quotationData) throws ConversionException
	{
		quotationData.setFinanceCompany(financeInfoModel.getFinanceCompany());
		quotationData.setFinanceCycle(financeInfoModel.getFinanceCycle());
		quotationData.setFinanceProduct(financeInfoModel.getFinanceProduct());
		quotationData.setFinanceType(financeInfoModel.getFinanceType());
		quotationData.setFinanceUnitPrice(financeInfoModel.getUnitPrice());
		quotationData.setFinanceServiceCharge(financeInfoModel.getServicePrice());
		quotationData.setFinanceRate(financeInfoModel.getRate());
		quotationData.setFinanceMortgage(financeInfoModel.getMortgage());
		quotationData.setFinanceRemark(financeInfoModel.getDescription());
		if(financeInfoModel.getStartTime()!=null) quotationData.setFinanceStartTime(DateFormatUtils.getDateString("datetime", financeInfoModel.getStartTime()));
	}
}
