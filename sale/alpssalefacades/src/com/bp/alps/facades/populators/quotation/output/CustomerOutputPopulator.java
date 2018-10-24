package com.bp.alps.facades.populators.quotation.output;

import com.bp.alps.core.model.SalesOrderAttributeModel;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.facades.data.order.CustomerInfoData;
import com.bp.alps.facades.data.quotation.QuotationData;
import de.hybris.platform.converters.Populator;
import com.bp.alps.core.model.QuotationInfoModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Resource;


public class CustomerOutputPopulator implements Populator<QuotationInfoModel, QuotationData>
{
	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(QuotationInfoModel quotationInfoModel, QuotationData quotationData)
			throws ConversionException
	{
		quotationData.setLineItemName(quotationInfoModel.getDescription());
		quotationData.setDeposit(quotationInfoModel.getDeposit());
		CustomerInfoData customerInfoData = new CustomerInfoData();
		SalesOrderAttributeModel salesOrderAttribute = quotationInfoModel.getSalesAttribute();
		if(salesOrderAttribute!=null && salesOrderAttribute.getOpportunity()!=null){
			OpportunityModel opportunityModel = salesOrderAttribute.getOpportunity();
			if(opportunityModel.getProvince()!=null) customerInfoData.setProvinceCode(opportunityModel.getProvince().getCode());
			if(opportunityModel.getCity()!=null) customerInfoData.setCityCode(opportunityModel.getCity().getCode());
			if(opportunityModel.getDistrict()!=null) customerInfoData.setDistrictCode(opportunityModel.getDistrict().getCode());
			if(opportunityModel.getBuyerType()!=null) customerInfoData.setAttribute(enumerationService.getEnumerationName(opportunityModel.getBuyerType()));
			customerInfoData.setName(opportunityModel.getName());
			customerInfoData.setMobileNumber(opportunityModel.getMobile());
			customerInfoData.setAddress(opportunityModel.getAddress());
			customerInfoData.setOtherContactNumber(opportunityModel.getOtherContact());
			if(opportunityModel.getPlatform()!=null) customerInfoData.setCustomerSource(opportunityModel.getPlatform().getName());
			quotationData.setCustomer(customerInfoData);
		}
	}
}
