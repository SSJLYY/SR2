package com.bp.alps.vehiclecommercefacade.abstractOrder.impl;

import com.bp.alps.facades.data.abstractOrder.EntryData;
import com.bp.alps.vehiclecommercefacade.abstractOrder.AbstractOrderEntryOutputConverter;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class AbstractOrderEntryOutputConverterImpl<T> implements AbstractOrderEntryOutputConverter<T>
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractOrderEntryOutputConverterImpl.class);

	@Resource
	private Populator entryDataOutputPopulator;

	protected void populatorEntryProduct(final String key, final List<String> categoryCodeList, final String dataCode, final AbstractOrderModel abstractOrderModel, final T abstractOrderData)
	{
		if(CollectionUtils.isNotEmpty(abstractOrderModel.getEntries()))
		{
			List<EntryData> entryDataList = new ArrayList<>();
			abstractOrderModel.getEntries().stream().forEach(entryModel->{
				if (entryModel.getProduct() != null)
				{
					ProductModel productModel = entryModel.getProduct();
					if (productModel.getItemtype().equals(key))
					{
						Optional<CategoryModel> optional = productModel.getSupercategories().stream().filter(categoryModel->categoryCodeList.contains(categoryModel.getCode())).findAny();
						if(optional.isPresent())
						{
							EntryData entryData = new EntryData();
							entryDataOutputPopulator.populate(entryModel, entryData);
							entryDataList.add(entryData);
						}
					}
				}
			});
			if(CollectionUtils.isNotEmpty(entryDataList)) setObjectValue(dataCode, abstractOrderData, entryDataList);
		}
	}

	protected void setObjectValue(final String key, final T abstractOrderData, final Object value){
		setObjectValue(key, value, abstractOrderData, null);
	}

	protected void setObjectValue(final String key, final Object value, final Object object, Class objectClass){
		try
		{
			if(objectClass == null){
				objectClass = object.getClass();
			}
			final Field field = objectClass.getDeclaredField(key);
			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(false);
		}catch (Exception e){
			if(objectClass.getSuperclass()!=null)
			{
				setObjectValue(key, value, object, objectClass.getSuperclass());
				return;
			}
			LOG.error(e.getMessage(),e);
			return;
		}
	}
}
