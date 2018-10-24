package com.bp.alps.facades.populators.abstractOrder;

import com.bp.alps.core.model.FinanceInfoModel;
import com.bp.alps.core.model.LicensePlateInfoModel;
import com.bp.alps.core.model.SalesOrderAttributeModel;
import com.bp.alps.core.model.UsedcarInfoModel;
import com.bp.alps.core.service.FollowOpportunityService;
import com.bp.alps.core.service.OpportunityServices;
import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.vehiclecommerceservices.utils.ValidateClassUtils;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


public class SalesOrderAttributePopulator implements Populator<QuotationData, SalesOrderAttributeModel>
{
	@Resource
	private FollowOpportunityService followOpportunityService;

	@Resource
	private OpportunityServices opportunityServices;

	@Resource
	private Converter<QuotationData, LicensePlateInfoModel> licensePlateInfoConverter;

	@Resource
	private Converter<QuotationData, FinanceInfoModel> financeConverter;

	@Resource
	private Converter<QuotationData, UsedcarInfoModel> usedcarConverter;

	@Resource
	private ModelService modelService;

	@Override
	public void populate(QuotationData quotationData, SalesOrderAttributeModel salesOrderAttributeModel) throws ConversionException
	{
		salesOrderAttributeModel.setCarInsideColor(quotationData.getCarInsideColor());
		salesOrderAttributeModel.setServiceInfo(quotationData.getServiceInfo());
		salesOrderAttributeModel.setServicePrice(quotationData.getServicePrice());
		salesOrderAttributeModel.setOtherIncomInfo(quotationData.getOtherIncomInfo());
		salesOrderAttributeModel.setOtherPrice(quotationData.getOtherPrice());
		salesOrderAttributeModel.setOptionalProductTotalPrice(quotationData.getOptionalProductTotalPrice());
		salesOrderAttributeModel.setDecorProductTotalPrice(quotationData.getDecorProductTotalPrice());
		salesOrderAttributeModel.setInsuranceProductTotalPrice(quotationData.getInsuranceProductTotalPrice());
		salesOrderAttributeModel.setExtendedWarrantyTotalPrice(quotationData.getExtendedWarrantyTotalPrice());
		salesOrderAttributeModel.setVoucherTotalPrice(quotationData.getVoucherTotalPrice());

		if(quotationData.getDeliveryDate()!=null) salesOrderAttributeModel.setDeliveryDate(DateFormatUtils.getDate("datetime", quotationData.getDeliveryDate()));
		if(quotationData.getFollowOpportunityCode()!=null) salesOrderAttributeModel.setFollowOpportunity(followOpportunityService.getFollowOpportunityByCode(quotationData.getFollowOpportunityCode()));
		if(quotationData.getOpportunityCode()!=null) salesOrderAttributeModel.setOpportunity(opportunityServices.getOpportunityByKey(quotationData.getOpportunityCode(),null));

		FinanceInfoModel financeInfoModel = salesOrderAttributeModel.getFinanceInfo();
		if(financeInfoModel == null) financeInfoModel = modelService.create(FinanceInfoModel.class);
		financeConverter.convert(quotationData, financeInfoModel);
		if(!ValidateClassUtils.isAllFieldNull(financeInfoModel)){
			salesOrderAttributeModel.setFinanceInfo(financeInfoModel);
		}

		LicensePlateInfoModel licensePlateInfoModel = salesOrderAttributeModel.getLicensePlateInfo();
		if(licensePlateInfoModel == null) licensePlateInfoModel = modelService.create(LicensePlateInfoModel.class);
		licensePlateInfoConverter.convert(quotationData, licensePlateInfoModel);
		if(!ValidateClassUtils.isAllFieldNull(licensePlateInfoModel)){
			salesOrderAttributeModel.setLicensePlateInfo(licensePlateInfoModel);
		}

		UsedcarInfoModel usedcarInfoModel = salesOrderAttributeModel.getUsedcarInfo();
		if(usedcarInfoModel == null) usedcarInfoModel = modelService.create(UsedcarInfoModel.class);
		usedcarConverter.convert(quotationData, usedcarInfoModel);
		if(!ValidateClassUtils.isAllFieldNull(usedcarInfoModel)){
			salesOrderAttributeModel.setUsedcarInfo(usedcarInfoModel);
		}
	}
}
