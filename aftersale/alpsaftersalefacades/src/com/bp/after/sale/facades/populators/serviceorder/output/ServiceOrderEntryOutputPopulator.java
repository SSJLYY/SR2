package com.bp.after.sale.facades.populators.serviceorder.output;

import com.bp.after.sale.facades.data.ServiceOrderEntryData;
import com.bp.alps.after.sale.core.model.ServiceOrderEntryModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class ServiceOrderEntryOutputPopulator implements Populator<ServiceOrderEntryModel, ServiceOrderEntryData>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private CategoryService categoryService;

	@Override
	public void populate(ServiceOrderEntryModel serviceOrderEntryModel, ServiceOrderEntryData serviceOrderEntryData)
			throws ConversionException
	{
		if(serviceOrderEntryModel.getDiscountRate()!=null) serviceOrderEntryData.setRate(serviceOrderEntryModel.getDiscountRate());
		if(serviceOrderEntryModel.getEntryType()!=null)
		{
			serviceOrderEntryData.setServiceTypeCode(serviceOrderEntryModel.getEntryType().getCode());
			serviceOrderEntryData.setServiceType(enumerationService.getEnumerationName(serviceOrderEntryModel.getEntryType()));
		}
		if(serviceOrderEntryModel.getProductType()!=null)
		{
			serviceOrderEntryData.setCategoryCode(serviceOrderEntryModel.getProductType());
			CategoryModel categoryModel = categoryService.getCategoryForCode(serviceOrderEntryModel.getProductType());
			if(categoryModel!=null)
			{
				serviceOrderEntryData.setCategoryName(categoryModel.getName());
			}
		}
		if(serviceOrderEntryModel.getProduct()!=null)
		{
			serviceOrderEntryData.setName(serviceOrderEntryModel.getProduct().getName());
			serviceOrderEntryData.setCode(serviceOrderEntryModel.getProduct().getCode());
		}
		serviceOrderEntryData.setQuantity(serviceOrderEntryModel.getQuantity().intValue());
		serviceOrderEntryData.setActualPrice(serviceOrderEntryModel.getActualPrice());
		serviceOrderEntryData.setPrice(serviceOrderEntryModel.getBasePrice());
		serviceOrderEntryData.setRemain(serviceOrderEntryModel.getRemainSum());
	}
}
