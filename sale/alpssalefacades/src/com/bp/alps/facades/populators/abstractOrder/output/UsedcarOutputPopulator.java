package com.bp.alps.facades.populators.abstractOrder.output;

import com.bp.alps.core.model.UsedcarInfoModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


public class UsedcarOutputPopulator implements Populator<UsedcarInfoModel, QuotationData>
{
	@Resource
	private ModelService modelService;

	@Resource
	private QuotationService quotationService;

	@Override
	public void populate(UsedcarInfoModel usedcarInfoModel, QuotationData quotationData) throws ConversionException
	{
		quotationData.setSecondHandCarBrand(usedcarInfoModel.getBrand());
		quotationData.setSecondHandCarVhicle(usedcarInfoModel.getVehicleCategory());
		quotationData.setSecondHandCarMortgage(usedcarInfoModel.getMortgage());
		quotationData.setSecondHandCarMileage(usedcarInfoModel.getKmNumber());
		if(usedcarInfoModel.getPrice()!=null) quotationData.setSecondHandCarEvaluationOfPrice(usedcarInfoModel.getPrice().toString());
		if (usedcarInfoModel.getPurchaseYear() != null)
			quotationData.setSecondHandCarPurchasedDated(usedcarInfoModel.getPurchaseYear());
		quotationData.setSecondHandCarRecycleType(usedcarInfoModel.getRecycleType());
		quotationData.setSecondHandCarRemark(usedcarInfoModel.getDescription());
	}
}
