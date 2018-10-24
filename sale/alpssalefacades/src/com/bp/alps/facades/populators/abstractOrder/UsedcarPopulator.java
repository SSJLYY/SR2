package com.bp.alps.facades.populators.abstractOrder;

import com.bp.alps.core.model.UsedcarInfoModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.UUID;


public class UsedcarPopulator implements Populator<QuotationData, UsedcarInfoModel>
{
	@Resource
	private ModelService modelService;

	@Resource
	private QuotationService quotationService;

	@Override
	public void populate(QuotationData quotationData, UsedcarInfoModel usedcarInfoModel) throws ConversionException
	{
		if(StringUtils.isNotBlank(quotationData.getSecondHandCarEvaluationOfPrice()))
		{
			usedcarInfoModel.setCode(UUID.randomUUID().toString());
			usedcarInfoModel.setBrand(quotationData.getSecondHandCarBrand());
			usedcarInfoModel.setVehicleCategory(quotationData.getSecondHandCarVhicle());
			usedcarInfoModel.setMortgage(quotationData.getSecondHandCarMortgage());
			usedcarInfoModel.setKmNumber(quotationData.getSecondHandCarMileage());
			if (quotationData.getSecondHandCarEvaluationOfPrice() != null)
				usedcarInfoModel.setPrice(Double.valueOf(quotationData.getSecondHandCarEvaluationOfPrice()));
			usedcarInfoModel.setRecycleType(quotationData.getSecondHandCarRecycleType());
			usedcarInfoModel.setDescription(quotationData.getSecondHandCarRemark());
			usedcarInfoModel.setPurchaseYear(quotationData.getSecondHandCarPurchasedDated());
		}
	}
}
