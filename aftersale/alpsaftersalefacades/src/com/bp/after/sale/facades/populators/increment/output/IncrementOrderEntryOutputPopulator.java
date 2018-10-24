package com.bp.after.sale.facades.populators.increment.output;

import com.bp.after.sale.facades.data.IncrementOrderEntryData;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class IncrementOrderEntryOutputPopulator implements Populator<AbstractOrderEntryModel, IncrementOrderEntryData>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private CategoryService categoryService;

	@Override
	public void populate(AbstractOrderEntryModel entryModel, IncrementOrderEntryData incrementOrderEntryData)
			throws ConversionException
	{
		if(entryModel.getProductType()!=null)
		{
			incrementOrderEntryData.setCategoryCode(entryModel.getProductType());
			CategoryModel categoryModel = categoryService.getCategoryForCode(entryModel.getProductType());
			if(categoryModel!=null)
			{
				incrementOrderEntryData.setCategoryName(categoryModel.getName());
			}
		}
		incrementOrderEntryData.setName(entryModel.getProduct().getName());
		incrementOrderEntryData.setCode(entryModel.getProduct().getCode());
		incrementOrderEntryData.setQuantity(entryModel.getQuantity().intValue());
		incrementOrderEntryData.setActualPrice(entryModel.getActualPrice());
		incrementOrderEntryData.setPrice(entryModel.getBasePrice());
		incrementOrderEntryData.setRate(entryModel.getDiscountRate());
	}
}
