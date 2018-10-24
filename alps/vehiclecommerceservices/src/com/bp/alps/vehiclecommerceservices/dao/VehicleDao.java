package com.bp.alps.vehiclecommerceservices.dao;

import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;


public interface VehicleDao
{
	VehicleInfoModel getVehicleByCode(String code);

	SearchPageData<VehicleInfoModel> searchVehicle(String customerName, String mobileNumber, String vehicleCode, String vinCode, String licensePlateNumber, String beforetimeKey, PageableData pageableData);

	SearchPageData<VehicleInfoModel> getRelatedVehiclesByCustomerUid(String code, PageableData pageableData);

	VehicleInfoModel getVehicleByLicensePlateNumber(String number);
}
