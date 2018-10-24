package com.bp.after.sale.facades.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ProductSolrItemValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider {

    @Resource(name="solrFieldNameProvider")
    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object model) throws FieldValueProviderException {

        final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();

        if (model instanceof ProductModel){

            String value = null;
            ProductModel productModel = (ProductModel)model;

            String name = productModel.getName();
            String code = productModel.getCode();
            String materialType = productModel.getMaterialType();
            String specificationModel = productModel.getSpecificationModel();
            String maintenanceItemCode = productModel.getMaintenanceItemCode();
            String artificialMainType = productModel.getArtificialMainType();
            String servicePackStatus = productModel.getServicePackStatus();


            String name1 = indexedProperty.getName();

            switch(name1){
                case "name":
                    value=name;
                    break;
                case "code":
                    value=code;
                    break;
                case "materialType":
                    value=materialType;
                    break;
                case "specificationModel":
                    value=specificationModel;
                    break;
                case "maintenanceItemCode":
                    value=maintenanceItemCode;
                    break;
                case "artificialMainType":
                    value=artificialMainType;
                    break;
                case "servicePackStatus":
                    value=servicePackStatus;
                    break;
            }


            final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
            //fieldValues.add(new FieldValue(indexedProperty.getName(), value));
            for (final String fieldName : fieldNames)
            {
                    fieldValues.add(new FieldValue(fieldName, value));

            }


            return fieldValues;
        }
            return Collections.emptyList();

    }







   protected FieldNameProvider getFieldNameProvider()
   {
       return fieldNameProvider;
   }

    public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
    {
        this.fieldNameProvider = fieldNameProvider;
    }


}
