package com.bp.alps.vehiclecommercefacade.populators.vehicle;

import com.bp.alps.vehiclecommerceservices.enums.VehicleStatus;
import com.bp.alps.vehiclecommerceservices.enums.VehicleType;
import com.bp.alps.vehiclecommerceservices.service.AlpsCustomerService;
import com.bp.alps.facades.data.vehicle.VehicleInfoData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;


public class VehiclePoplator implements Populator<VehicleInfoData, VehicleInfoModel>
{
	@Resource
	private AlpsCustomerService alpsCustomerService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private ProductService productService;

	@Override
	public void populate(VehicleInfoData vehicleInfoData, VehicleInfoModel vehicleInfoModel) throws ConversionException
	{
		vehicleInfoModel.setLicensePlateNumber(vehicleInfoData.getLicensePlateNumber());
		vehicleInfoModel.setPurchaseYear(vehicleInfoData.getPurchaseYear());
		if(StringUtils.isNotBlank(vehicleInfoData.getVehicleCode())) vehicleInfoModel.setVehicle(productService.getProductForCode(vehicleInfoData.getVehicleCode()));
		if(StringUtils.isNotBlank(vehicleInfoData.getVehicleBrandCode())) vehicleInfoModel.setVehicleBrand(categoryService.getCategoryForCode(vehicleInfoData.getVehicleBrandCode()));
		if(StringUtils.isNotBlank(vehicleInfoData.getVehicleCategoryCode())) vehicleInfoModel.setVehicleCategory(categoryService.getCategoryForCode(vehicleInfoData.getVehicleCategoryCode()));
		if(StringUtils.isNotBlank(vehicleInfoData.getColor())) vehicleInfoModel.setColor(vehicleInfoData.getColor());
		vehicleInfoModel.setVinNumber(vehicleInfoData.getVinNumber());
		if(StringUtils.isNotBlank(vehicleInfoData.getStatus()))
		{
			vehicleInfoModel.setStatus(VehicleStatus.valueOf(vehicleInfoData.getStatus()));
		}
		CustomerModel customerModel = alpsCustomerService.getCustomerForUid(vehicleInfoData.getCustomerId());
		if(customerModel != null){
			vehicleInfoModel.setCustomer(customerModel);
		}
		vehicleInfoData.setLicensePlateNumber(vehicleInfoData.getLicensePlateNumber());
		if(vehicleInfoData.getVehicleType()!=null) vehicleInfoModel.setVehicleType(VehicleType.valueOf(vehicleInfoData.getVehicleType()));
		vehicleInfoModel.setCreator(alpsCustomerService.getCurrentSalesConsultant());
		if(vehicleInfoData.getEnterFactoryDate()!=null) vehicleInfoModel.setEnterFactoryDate(DateFormatUtils.getDate("date", vehicleInfoData.getEnterFactoryDate()));
		if(vehicleInfoData.getWarrantyStartDate()!=null) vehicleInfoModel.setWarrantyStartDate(DateFormatUtils.getDate("date", vehicleInfoData.getWarrantyStartDate()));
		vehicleInfoModel.setWarrantyCycle(vehicleInfoData.getWarrantyCycle());
		vehicleInfoModel.setWarrantyMileage(vehicleInfoData.getWarrantyMileage());
		if(vehicleInfoData.getWarrantyLastDate()!=null) vehicleInfoModel.setWarrantyLastDate(DateFormatUtils.getDate("date", vehicleInfoData.getWarrantyLastDate()));
	}
}
