package com.bp.alps.facades.populators;

import com.bp.alps.core.enums.*;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.service.OpportunityServices;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import com.bp.alps.facades.data.opp.OpportunityData;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.services.BaseStoreService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


public class OpportunityInputPoplator implements Populator<OpportunityData,OpportunityModel>
{
	@Resource
	private UserService userService;

	@Resource
	private BaseStoreService baseStoreService;

	@Resource
	private RegionService regionService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private ProductService productService;

	@Resource
	private OpportunityServices opportunityServices;

	@Override
	public void populate(OpportunityData source, OpportunityModel target) throws ConversionException{
		target.setCode(source.getCode());
		if(source.getSalesconsultant()!=null)
		{
			UserModel userModel = userService.getUserForUID(source.getSalesconsultant());
			if (userModel instanceof CustomerModel)
			{
				target.setSalesconsultant((CustomerModel) userModel);
			}
			if(source.getCreatedPerson().equals(source.getSalesconsultant()))
			{
				target.setCreatedPerson((CustomerModel) userModel);
			}else{
				userModel = userService.getUserForUID(source.getCreatedPerson());
				if (userModel instanceof CustomerModel)
				{
					target.setCreatedPerson((CustomerModel) userModel);
				}
			}
			//target.setStore();
		}
		target.setName(source.getName());
		target.setGender(source.getGender());
		target.setMobile(source.getMobile());
		target.setOtherContact(source.getOtherContact());
		if(source.getBuyerTypeCode()!=null) target.setBuyerType(CustomerType.valueOf(source.getBuyerTypeCode()));
		if(source.getProvinceCode()!=null) target.setProvince(regionService.getProvinceByCode(source.getProvinceCode()));
		if(source.getCityCode()!=null) target.setCity(regionService.getCityByCode(source.getCityCode()));
		if(source.getDistrictCode()!=null)  target.setDistrict(regionService.getDistrictByCode(source.getDistrictCode()));
		target.setAddress(source.getAddress());
		target.setIndustry(source.getIndustry());
		target.setCompanyName(source.getCompanyName());
		target.setEducation(source.getEducation());
		target.setWorkType(source.getWorkType());
		target.setNumberOfChildren(source.getNumberOfChildren());
		target.setMaritalStatus(source.getMaritalStatus());
		target.setNumberOfFamily(source.getNumberOfFamily());
		target.setPersonalncome(source.getPersonalncome());
		target.setLicenseYear(source.getLicenseYear());
		if(StringUtils.isNotEmpty(source.getVehicleTypeCode())) target.setVehicleType(categoryService.getCategoryForCode(source.getVehicleTypeCode()));
		if(StringUtils.isNotEmpty(source.getVehicleCategoryCode())) target.setVehicleCategory(categoryService.getCategoryForCode(source.getVehicleCategoryCode()));
		if(StringUtils.isNotEmpty(source.getVehicleCode())) target.setVehicle(productService.getProductForCode(source.getVehicleCode()));
		target.setColor(source.getColor());
		target.setCompetitiveVehicle(source.getCompetitiveVehicle());
		target.setReplacement(source.getReplacement());
		target.setReplacementVehicle(source.getReplacementVehicle());
		target.setPurchaseType(PurchaseType.valueOf(source.getPurchaseTypeCode()));
		target.setPurchaseTimeType(PurchaseTimeType.valueOf(source.getPurchaseTimeTypeCode()));
		target.setLevel(opportunityServices.getOpportunityLevelByCode(source.getLevelCode()));
		target.setBudget(source.getBudget());
		target.setPaymentMethod(source.getPaymentMethod());
		target.setPurpose(source.getPurpose());

		if(CollectionUtils.isNotEmpty(source.getBuyerPoint()))
		{
			List<BuyerPoint> buyerPointList = source.getBuyerPoint().stream().map(s -> BuyerPoint.valueOf(s)).collect(Collectors.toList());
			target.setBuyerPoint(buyerPointList);
		}

		if(CollectionUtils.isNotEmpty(source.getServicestypes()))
		{
			List<ServicesTypeInfo> servicesTypeInfos = source.getServicestypes().stream().map(s -> ServicesTypeInfo.valueOf(s)).collect(Collectors.toList());
			target.setServicesTypes(servicesTypeInfos);
		}
		target.setFamilylncome(source.getFamilylncome());
	}
}
