package com.bp.alps.facades.populators;

import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.vehiclecommerceservices.service.RegionService;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.opp.OpportunityData;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.services.BaseStoreService;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


public class OpportunityOutputPoplator implements Populator<OpportunityModel,OpportunityData>
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

	@Override
	public void populate(OpportunityModel source, OpportunityData target) throws ConversionException{
		target.setCode(source.getCode());

		target.setSalesconsultant(source.getSalesconsultant()!=null?source.getSalesconsultant().getUid():null);
		target.setCreatedPerson(source.getCreatedPerson()!=null?source.getCreatedPerson().getUid():null);
		target.setName(source.getName());
		target.setGender(source.getGender());
		target.setMobile(source.getMobile());
		target.setOtherContact(source.getOtherContact());
		target.setBuyerTypeCode(source.getBuyerType()!=null?source.getBuyerType().getCode():null);
		target.setProvinceCode(source.getProvince()!=null?source.getProvince().getCode():null);
		target.setCityCode(source.getCity()!=null?source.getCity().getCode():"");
		target.setDistrictCode(source.getDistrict()!=null?source.getDistrict().getCode():null);
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
		target.setVehicleTypeCode(source.getVehicleType()!=null?source.getVehicleType().getCode():"");
		target.setVehicleCategoryCode(source.getVehicleCategory()!=null?source.getVehicleCategory().getCode():"");
		target.setVehicleCode(source.getVehicle()!=null?source.getVehicle().getCode():null);
		target.setColor(source.getColor());
		target.setCompetitiveVehicle(source.getCompetitiveVehicle());
		target.setReplacement(source.getReplacement());
		target.setReplacementVehicle(source.getReplacementVehicle());
		target.setPurchaseTypeCode(source.getPurchaseType()!=null?source.getPurchaseType().getCode():null);
		target.setPurchaseTimeTypeCode(source.getPurchaseTimeType()!=null?source.getPurchaseTimeType().getCode():null);
		target.setLevelCode(source.getLevel()!=null?source.getLevel().getCode():null);
		target.setBudget(source.getBudget());
		target.setPaymentMethod(source.getPaymentMethod());
		target.setPurpose(source.getPurpose());
		target.setLastFollowTime(DateFormatUtils.getDateString("datetime",source.getLastFollowTime()));
		target.setCreationtime(DateFormatUtils.getDateString("datetime",source.getCreationtime()));
		target.setLeftFollowTime(source.getLeftFollowTime());
		target.setStatusCode(source.getStatus()!=null?source.getStatus().getCode():null);
		target.setStatusName(source.getStatus()!=null?source.getStatus().getName():null);
		target.setPlatform(source.getPlatform()!=null?source.getPlatform().getName():null);
		target.setFollowCount(source.getFollowCount());

		if(CollectionUtils.isNotEmpty(source.getBuyerPoint()))
		{
			List<String> buyerPointList = source.getBuyerPoint().stream().map(s -> s.getCode()).collect(Collectors.toList());
			target.setBuyerPoint(buyerPointList);
		}

		if(CollectionUtils.isNotEmpty(source.getServicesTypes()))
		{
			List<String> servicesTypeInfos = source.getServicesTypes().stream().map(s -> s.getCode()).collect(Collectors.toList());
			target.setServicestypes(servicesTypeInfos);
		}
		target.setFamilylncome(source.getFamilylncome());
	}
}
