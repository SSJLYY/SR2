package com.bp.after.sale.facades.populators.returnorder.output;

import com.bp.after.sale.facades.data.IncrementOrderEntryData;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.returns.model.ReturnEntryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class ReturnOrderEntryOutputPopulator implements Populator<ReturnEntryModel, IncrementOrderEntryData>
{
	@Resource
	private EnumerationService enumerationService;

	@Resource
	private CategoryService categoryService;

	@Override
	public void populate(ReturnEntryModel entryModel, IncrementOrderEntryData incrementOrderEntryData)
			throws ConversionException
	{
		AbstractOrderEntryModel orderEntryModel = entryModel.getOrderEntry();
		if(orderEntryModel.getProductType()!=null)
		{
			incrementOrderEntryData.setCategoryCode(orderEntryModel.getProductType());
			CategoryModel categoryModel = categoryService.getCategoryForCode(orderEntryModel.getProductType());
			if(categoryModel!=null)
			{
				incrementOrderEntryData.setCategoryName(categoryModel.getName());
			}
		}
		incrementOrderEntryData.setName(orderEntryModel.getProduct().getName());
		incrementOrderEntryData.setCode(orderEntryModel.getProduct().getCode());
		incrementOrderEntryData.setQuantity(entryModel.getReceivedQuantity().intValue());
		incrementOrderEntryData.setActualPrice(orderEntryModel.getActualPrice());
		incrementOrderEntryData.setPrice(orderEntryModel.getBasePrice());
		incrementOrderEntryData.setRate(orderEntryModel.getDiscountRate());
	}
}
