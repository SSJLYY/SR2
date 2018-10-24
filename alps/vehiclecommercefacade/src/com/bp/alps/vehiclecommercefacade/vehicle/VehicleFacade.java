package com.bp.alps.vehiclecommercefacade.vehicle;

import com.bp.alps.facades.data.DefaultPageableRequest;
import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.vehicle.SearchVehicleRequest;
import com.bp.alps.facades.data.vehicle.SearchVehicleResponse;
import com.bp.alps.facades.data.vehicle.VehicleInfoData;
import com.bp.alps.vehiclecommerceservices.model.VehicleInfoModel;
import de.hybris.platform.cmsfacades.data.OptionData;

import java.util.List;


public interface VehicleFacade
{
	SearchVehicleResponse searchVehicle(SearchVehicleRequest searchVehicleRequest);

	DefaultResponse createOrUpdateRequest(VehicleInfoData vehicleInfoData);

	List<OptionData> getStatus();

	List<OptionData> getVehicleType();

	VehicleInfoData getVehicleDetail(String code);

	List<OptionData> getVehicle2userType();

	SearchVehicleResponse getRelatedVehiclesByCustomerUid(String code, DefaultPageableRequest defaultPageableRequest);
}
