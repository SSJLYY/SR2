package com.bp.alps.facades.populators.abstractOrder.output;

import com.bp.alps.core.model.LicensePlateInfoModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import java.math.BigDecimal;


public class LicensePlateInfoOutputPopulator implements Populator<LicensePlateInfoModel, QuotationData>
{
	@Resource
	private ModelService modelService;

	@Resource
	private QuotationService quotationService;

	@Resource
	private RegionService regionService;

	@Override
	public void populate(LicensePlateInfoModel licensePlateInfoModel, QuotationData quotationData) throws ConversionException
	{
		if (licensePlateInfoModel.getCity() != null) quotationData.setCity(licensePlateInfoModel.getCity().getCode());
		if (licensePlateInfoModel.getProvince() != null) quotationData.setProvince(licensePlateInfoModel.getProvince().getCode());
		quotationData.setLicensePlateRemake(licensePlateInfoModel.getDescription());
		quotationData.setLicensePlateServiceCharge(licensePlateInfoModel.getPrice());
		quotationData.setLicensePlatePurchaseMethod(licensePlateInfoModel.getPurchaseMethod());
		quotationData.setLicensePlateTax(licensePlateInfoModel.getPurchaseTax());
		quotationData.setVehicleTypeForLicensePlate(licensePlateInfoModel.getVehicleType());
	}
}
