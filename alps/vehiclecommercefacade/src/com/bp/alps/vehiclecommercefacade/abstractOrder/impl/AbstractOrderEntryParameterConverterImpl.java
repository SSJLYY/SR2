package com.bp.alps.vehiclecommercefacade.abstractOrder.impl;

import com.bp.alps.core.data.order.AlpsCommerceOrderEntryParameter;
import com.bp.alps.vehiclecommercefacade.abstractOrder.AbstractOrderEntryParameterConverter;
import com.bp.alps.facades.data.abstractOrder.EntryData;
import com.bp.alps.vehiclecommercefacade.populators.abstractOrder.EntryDataPopulator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractOrderEntryParameterConverterImpl<T> implements AbstractOrderEntryParameterConverter<T>
{
	private static final Logger LOG = LoggerFactory.getLogger(
			AbstractOrderEntryParameterConverterImpl.class);

	@Resource
	private EntryDataPopulator orderEntryDataPopulator;

	protected String[] entryListKeyInData = new String[]{"optionalProduct"};

	protected Class entryClass;

	public AbstractOrderEntryParameterConverterImpl(){
		setEntryClass(AbstractOrderEntryModel.class);
	}

	public List<AlpsCommerceOrderEntryParameter> converter(T abstractOrderData, AbstractOrderModel order)
	{
		List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters = new ArrayList<>();
		populatorCustomData(abstractOrderData, commerceOrderEntryParameters);
		Arrays.stream(entryListKeyInData).forEach(key -> populatorEntryProduct(key, abstractOrderData, order, commerceOrderEntryParameters));

		attachmentParameters(order, commerceOrderEntryParameters);
		return commerceOrderEntryParameters;
	}

	protected void attachmentParameters(AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> commerceOrderEntryParameters){

	}

	protected void populatorCustomData(final T abstractOrderData, List<AlpsCommerceOrderEntryParameter> commerceQuotaionEntryParameters){

	}

	protected void populatorEntryProduct(final String key, final T abstractOrderData, AbstractOrderModel order, List<AlpsCommerceOrderEntryParameter> commerceQuotationEntryParameterList){
		List object = getObjectValue(key, abstractOrderData);
		try
		{
			if(CollectionUtils.isNotEmpty(object)){
				object.stream().forEach(subObject -> {
					if(subObject instanceof EntryData){
						EntryData entryData = (EntryData) subObject;
						if(StringUtils.isNotBlank(entryData.getCode()))
						{
							AlpsCommerceOrderEntryParameter commerceOrderEntryParameter = new AlpsCommerceOrderEntryParameter();
							orderEntryDataPopulator.populate(entryData, commerceOrderEntryParameter);
							if(commerceOrderEntryParameter.getProduct()!=null)
							{
								commerceOrderEntryParameter.setOrder(order);
								commerceOrderEntryParameter.setOrderEntryClass(this.entryClass);
								commerceQuotationEntryParameterList.add(commerceOrderEntryParameter);
							}
						}
					}
				});
			}

		}catch (ConversionException e){
		}
	}

	public Class getEntryClass()
	{
		return entryClass;
	}

	public void setEntryClass(Class entryClass)
	{
		this.entryClass = entryClass;
	}

	public String[] getEntryListKeyInData()
	{
		return entryListKeyInData;
	}

	public void setEntryListKeyInData(String[] entryListKeyInData)
	{
		this.entryListKeyInData = entryListKeyInData;
	}

	protected List getObjectValue(final String key, final Object object){
		return getObjectValue(key, object,null);
	}

	protected List getObjectValue(final String key, final Object object, Class objectClass){
		try
		{
			if(objectClass==null){
				objectClass = object.getClass();
			}
			final Field field = objectClass.getDeclaredField(key);
			field.setAccessible(true);
			final Object value = field.get(object);
			field.setAccessible(false);
			if(value instanceof List){
				return (List) value;
			}
		}catch (Exception e){
			if(objectClass.getSuperclass()!=null)
			{
				return getObjectValue(key, object, objectClass.getSuperclass());
			}
			LOG.error(e.getMessage(),e);
		}
		return new ArrayList();
	}
}
