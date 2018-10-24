package com.bp.alps.vehiclecommercefacade.populators.product;

import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlpsSearchResultVariantProductPopulator extends SearchResultProductPopulator implements Populator<SearchResultValueData, ProductData>
{
    @Override
    public void populate(final SearchResultValueData source, final ProductData target)
    {
        super.populate(source, target);
        if (source.getValues() != null)
        {
            target.setMaterialGroup(this.getValue(source,"materialGroup"));
            target.setManualDirectoryIdentifier(this.getValue(source,"manualDirectoryIdentifier"));
            target.setPricingReferenceMaterial(this.getValue(source,"pricingReferenceMaterial"));
            target.setUnitHourSalesPrice(this.getValue(source,"unitHourSalesPrice"));
            target.setRecentlyModifiedPerson(this.getValue(source,"recentlyModifiedPerson"));

            /*target.setMaterialType(this.getValue(source,"materialType"));
            target.setSpecificationModel(this.getValue(source,"specificationModel"));
            target.setMaintenanceItemCode(this.getValue(source,"maintenanceItemCode"));
            target.setArtificialMainType(this.getValue(source,"artificialMainType"));
            target.setServicePackStatus(this.getValue(source,"servicePackStatus"));*/


            List<String> materialTypeList = this.getValue(source,"materialType");
            if(materialTypeList != null && materialTypeList.size() >0 && materialTypeList.get(0)!=null) {
                target.setMaterialType(materialTypeList.get(0));
            }

            List<String> specificationModelList = this.getValue(source,"specificationModel");
            if(specificationModelList != null && specificationModelList.size() >0 && specificationModelList.get(0)!=null) {
                target.setSpecificationModel(specificationModelList.get(0));
            }

            List<String> maintenanceItemCodeList = this.getValue(source,"maintenanceItemCode");
            if(maintenanceItemCodeList != null && maintenanceItemCodeList.size() >0 && maintenanceItemCodeList.get(0)!=null) {
                target.setMaintenanceItemCode(maintenanceItemCodeList.get(0));
            }

            List<String> artificialMainTypeList = this.getValue(source,"artificialMainType");
            if(artificialMainTypeList != null && artificialMainTypeList.size() >0 && artificialMainTypeList.get(0)!=null){
                target.setArtificialMainType(artificialMainTypeList.get(0));
            }

            List<String> servicePackStatusList = this.getValue(source,"servicePackStatus");
            if(servicePackStatusList != null && servicePackStatusList.size() >0 && servicePackStatusList.get(0)!=null) {
                target.setServicePackStatus(servicePackStatusList.get(0));
            }



            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);

            if(null!=this.getValue(source,"onlineDate")){
                String startTime = this.getValue(source,"onlineDate");
                Date date = null;
                try {
                    date = sf1.parse(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String startTimeString = formatter.format(date);
                target.setOnlineDate(startTimeString);
            }

            if(null!=this.getValue(source,"offlineDate")){
                String endTime = this.getValue(source,"offlineDate");
                Date date = null;
                try {
                     date = sf1.parse(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String endTimeString = formatter.format(date);
                target.setOfflineDate(endTimeString);
            }


            populateCurrency(source, target);
        }
    }

    private void populateCurrency(SearchResultValueData source, ProductData target) {
        PriceData priceData = target.getPrice();
        Double value = this.getValue(source,"suggestedRetailPrice_cny_double");
        if(value!=null)
        {
            priceData.setSuggestedRetailPrice(value.toString());
            target.setPrice(priceData);
        }
    }


}
