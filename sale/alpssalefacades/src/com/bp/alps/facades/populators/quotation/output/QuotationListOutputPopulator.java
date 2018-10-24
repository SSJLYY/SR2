package com.bp.alps.facades.populators.quotation.output;

import com.bp.alps.core.model.UsedcarInfoModel;
import com.bp.alps.core.quotation.QuotationService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.quotation.QuotationListData;
import de.hybris.platform.converters.Populator;
import com.bp.alps.core.model.QuotationInfoModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;


public class QuotationListOutputPopulator implements Populator<QuotationInfoModel, QuotationListData>
{
	@Resource
	private ModelService modelService;

	@Resource
	private QuotationService quotationService;

	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(QuotationInfoModel quotationInfoModel, QuotationListData target) throws ConversionException
	{
		target.setCode(quotationInfoModel.getCode());
		target.setCreationtime(DateFormatUtils.getDateString("datetime",quotationInfoModel.getCreationtime()));

		if(quotationInfoModel.getStatus()!=null)
		{
			target.setStatus(enumerationService.getEnumerationName(quotationInfoModel.getStatus()));
			target.setStatusCode(quotationInfoModel.getStatus().getCode());
		}
		target.setTotalPrice(quotationInfoModel.getTotalPrice());
		target.setLineItemName(quotationInfoModel.getDescription());
	}
}
