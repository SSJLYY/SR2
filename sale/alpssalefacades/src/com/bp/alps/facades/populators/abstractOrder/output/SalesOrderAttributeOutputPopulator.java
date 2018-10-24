package com.bp.alps.facades.populators.abstractOrder.output;

import com.bp.alps.core.model.FinanceInfoModel;
import com.bp.alps.core.model.LicensePlateInfoModel;
import com.bp.alps.core.model.SalesOrderAttributeModel;
import com.bp.alps.core.model.UsedcarInfoModel;
import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;


public class SalesOrderAttributeOutputPopulator implements Populator<SalesOrderAttributeModel, QuotationData>
{
	@Resource
	private Converter<LicensePlateInfoModel, QuotationData> licensePlateInfoOutputConverter;

	@Resource
	private Converter<FinanceInfoModel, QuotationData> financeOutputConverter;

	@Resource
	private Converter<UsedcarInfoModel, QuotationData> usedcarOutputConverter;

	@Override
	public void populate(SalesOrderAttributeModel salesOrderAttribute, QuotationData quotationData) throws ConversionException
	{
		quotationData.setCarInsideColor(salesOrderAttribute.getCarInsideColor());
		quotationData.setServiceInfo(salesOrderAttribute.getServiceInfo());
		quotationData.setServicePrice(salesOrderAttribute.getServicePrice());
		quotationData.setOtherIncomInfo(salesOrderAttribute.getOtherIncomInfo());
		quotationData.setOtherPrice(salesOrderAttribute.getOtherPrice());
		quotationData.setOptionalProductTotalPrice(salesOrderAttribute.getOptionalProductTotalPrice());
		quotationData.setDecorProductTotalPrice(salesOrderAttribute.getDecorProductTotalPrice());
		quotationData.setInsuranceProductTotalPrice(salesOrderAttribute.getInsuranceProductTotalPrice());
		quotationData.setExtendedWarrantyTotalPrice(salesOrderAttribute.getExtendedWarrantyTotalPrice());
		quotationData.setVoucherTotalPrice(salesOrderAttribute.getVoucherTotalPrice());

		if(salesOrderAttribute.getDeliveryDate()!=null) quotationData.setDeliveryDate(DateFormatUtils.getDateString("datetime", salesOrderAttribute.getDeliveryDate()));
		if(salesOrderAttribute.getFollowOpportunity()!=null) quotationData.setFollowOpportunityCode(salesOrderAttribute.getFollowOpportunity().getCode());
		if(salesOrderAttribute.getOpportunity()!=null) quotationData.setOpportunityCode(salesOrderAttribute.getOpportunity().getCode());

		final FinanceInfoModel financeInfoModel = salesOrderAttribute.getFinanceInfo();
		if(financeInfoModel!=null) financeOutputConverter.convert(financeInfoModel, quotationData);

		final LicensePlateInfoModel licensePlateInfoModel = salesOrderAttribute.getLicensePlateInfo();
		if(licensePlateInfoModel!=null) licensePlateInfoOutputConverter.convert(licensePlateInfoModel, quotationData);

		final UsedcarInfoModel usedcarInfoModel = salesOrderAttribute.getUsedcarInfo();
		if(usedcarInfoModel!=null) usedcarOutputConverter.convert(usedcarInfoModel, quotationData);
	}
}
