package com.bp.alps.vehiclecommercefacade.vehicle.impl;

import com.bp.alps.facades.data.DefaultPageableRequest;
import com.bp.alps.vehiclecommerceservices.enums.Vehicle2UserType;
import com.bp.alps.vehiclecommerceservices.enums.VehicleStatus;
import com.bp.alps.vehiclecommerceservices.enums.VehicleType;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import com.bp.alps.vehiclecommercefacade.facade.DefaultAlpsCommerceFacade;
import com.bp.alps.vehiclecommercefacade.vehicle.VehicleFacade;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.vehicle.SearchVehicleRequest;
import com.bp.alps.facades.data.vehicle.SearchVehicleResponse;
import com.bp.alps.facades.data.vehicle.VehicleInfoBaseData;
import com.bp.alps.facades.data.vehicle.VehicleInfoData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;


public class VehicleFacadeImpl extends DefaultAlpsCommerceFacade implements VehicleFacade
{
	@Resource
	private VehicleService vehicleService;

	@Resource
	private Converter<VehicleInfoData, VehicleInfoModel> vehicleConverter;

	@Resource
	private Converter<VehicleInfoModel, VehicleInfoData> vehicleOutputConverter;

	@Resource
	private Converter<VehicleInfoModel, VehicleInfoBaseData> vehicleBaseOutputConverter;

	@Resource
	private EnumerationService enumerationService;

	@Resource(name="enumConverter")
	private Converter<HybrisEnumValue,OptionData> enumerationValueModelConverter;

	@Override
	public DefaultResponse createOrUpdateRequest(VehicleInfoData vehicleInfoData)
	{
		VehicleInfoModel vehicleInfoModel = null;
		if(StringUtils.isNotBlank(vehicleInfoData.getCode()))
		{
			vehicleInfoModel = vehicleService.getVehicleByCode(vehicleInfoData.getCode());
		}
		if(vehicleInfoModel == null){
			vehicleInfoModel = vehicleService.createVehicle();
		}
		vehicleConverter.convert(vehicleInfoData, vehicleInfoModel);
		vehicleService.saveVehicleModel(vehicleInfoModel);
		DefaultResponse defaultResponse = new DefaultResponse();
		defaultResponse.setSuccess(true);
		return defaultResponse;
	}

	public List<OptionData> getStatus(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(VehicleStatus.class));
	}

	public List<OptionData> getVehicleType(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(VehicleType.class));
	}

	public List<OptionData> getVehicle2userType(){
		return enumerationValueModelConverter.convertAll(enumerationService.getEnumerationValues(Vehicle2UserType.class));
	}

	public SearchVehicleResponse getRelatedVehiclesByCustomerUid(String code, DefaultPageableRequest defaultPageableRequest){
		PageableData pageableData = populatorPageable(defaultPageableRequest);
		SearchPageData<VehicleInfoModel> vehicleInfoList = vehicleService.getRelatedVehiclesByCustomerUid(code, pageableData);
		SearchVehicleResponse searchVehicleResponse = convertSearchPageData(vehicleInfoList, pageableData);
		return searchVehicleResponse;
	}

	@Override
	public SearchVehicleResponse searchVehicle(SearchVehicleRequest searchVehicleRequest)
	{
		PageableData pageableData = populatorPageable(searchVehicleRequest);
		SearchPageData<VehicleInfoModel> searchPageData = vehicleService.searchVehicle(searchVehicleRequest.getCustomerName(), searchVehicleRequest.getMobileNumber(), searchVehicleRequest.getCode(), searchVehicleRequest.getVinCode(), searchVehicleRequest.getLicensePlateNumber(), searchVehicleRequest.getBeforetimeKey(), pageableData);
		SearchVehicleResponse searchVehicleResponse = convertSearchPageData(searchPageData, pageableData);
		return searchVehicleResponse;
	}

	private SearchVehicleResponse convertSearchPageData(SearchPageData<VehicleInfoModel> searchPageData, PageableData pageableData){
		SearchVehicleResponse searchVehicleResponse = new SearchVehicleResponse();
		searchVehicleResponse.setTotalPage(1);
		if(CollectionUtils.isNotEmpty(searchPageData.getResults())){
			List<VehicleInfoBaseData> vehicleInfoBaseDataList = vehicleBaseOutputConverter.convertAll(searchPageData.getResults());
			poplatorSequenceNumber(vehicleInfoBaseDataList, pageableData);
			searchVehicleResponse.setVehicleList(vehicleInfoBaseDataList);
			searchVehicleResponse.setTotalPage(searchPageData.getPagination().getNumberOfPages());
		}
		searchVehicleResponse.setSuccess(true);
		return searchVehicleResponse;
	}

	public VehicleInfoData getVehicleDetail(String code){
		VehicleInfoModel vehicleInfoModel = vehicleService.getVehicleByCode(code);
		if(vehicleInfoModel==null) return null;
		VehicleInfoData vehicleInfoData = vehicleOutputConverter.convert(vehicleInfoModel);
		return vehicleInfoData;
	}

	protected void poplatorSequenceNumber(List<VehicleInfoBaseData> vehicleInfoBaseDataList, PageableData pageableData){
		Integer startSequence = pageableData.getPageSize()*pageableData.getCurrentPage()+1;
		for(VehicleInfoBaseData vehicleInfoBaseData : vehicleInfoBaseDataList){
			vehicleInfoBaseData.setSequenceNumber(startSequence++);
		}
	}
}
