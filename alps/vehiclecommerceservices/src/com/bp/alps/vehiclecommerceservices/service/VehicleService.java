package com.bp.alps.vehiclecommerceservices.service;

import com.bp.alps.vehiclecommerceservices.model.*;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;


public interface VehicleService
{
	VehicleInfoModel createVehicle();

	Vehicle2UserModel createVehicle2User();

	VehicleInsuranceInfoModel createVehicleInsurance();

	void saveVehicleInsurance(VehicleInsuranceInfoModel vehicleInsuranceInfoModel);

	VehicleInfoModel getVehicleByCode(String code);

	void saveVehicleModel(VehicleInfoModel vehicleInfoModel);

	void saveVehicle2UserModel(Vehicle2UserModel vehicle2UserModel);

	SearchPageData<VehicleInfoModel> searchVehicle(String customerName, String mobileNumber, String vehicleCode, String vinCode, String licensePlateNumber, String beforetimeKey, PageableData pageableData);

	List<VehicleIncreaseInfoModel> getIncreaseByVehicle(VehicleInfoModel vehicleInfoModel);

	List<VehicleInsuranceInfoModel> getInsuranceByVehicle(VehicleInfoModel vehicleInfoModel);

	List<VehicleMaintainInfoModel> getMaintainByVehicle(VehicleInfoModel vehicleInfoModel);

	List<Vehicle2UserModel> getRelatedUserByVehice(VehicleInfoModel vehicleInfoModel);

	List<Vehicle2UserModel> getVehicle2UserByPks(List<String> pks);

	List<VehicleInsuranceInfoModel> getVehicleInsurancePks(List<String> pks);

	Boolean deleteVehicle2UserByPks(List<String> pks);

	Boolean deleteVehicleInsurancePks(List<String> pks);

	SearchPageData<VehicleInfoModel> getRelatedVehiclesByCustomerUid(String code, PageableData pageableData);
}
