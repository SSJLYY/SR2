package com.bp.alps.facades.populators.abstractOrder;

import com.bp.alps.core.model.LicensePlateInfoModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


public class LicensePlateInfoPopulator implements Populator<QuotationData, LicensePlateInfoModel>
{
	@Resource
	private ModelService modelService;

	@Resource
	private QuotationService quotationService;

	@Resource
	private RegionService regionService;

	@Override
	public void populate(QuotationData quotationData, LicensePlateInfoModel licensePlateInfoModel) throws ConversionException
	{
		if(quotationData.getLicensePlateServiceCharge()!=null)
		{
			if (quotationData.getProvince() != null)
				licensePlateInfoModel.setProvince(regionService.getProvinceByCode(quotationData.getProvince()));
			if (quotationData.getCity() != null)
				licensePlateInfoModel.setCity(regionService.getCityByCode(quotationData.getCity()));
			licensePlateInfoModel.setDescription(quotationData.getLicensePlateRemake());
			licensePlateInfoModel.setPrice(quotationData.getLicensePlateServiceCharge());
			licensePlateInfoModel.setPurchaseMethod(quotationData.getLicensePlatePurchaseMethod());
			licensePlateInfoModel.setPurchaseTax(quotationData.getLicensePlateTax());
			licensePlateInfoModel.setVehicleType(quotationData.getVehicleTypeForLicensePlate());
		}
	}
}
