package com.bp.alps.vehiclecommercefacade.vehicle;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.vehicle.InsuranceData;
import com.bp.alps.facades.data.vehicle.Vehicle2User;

import java.util.List;


public interface VehicleInsuranceFacade
{
	DefaultResponse create(InsuranceData insuranceData);

	DefaultResponse delete(final List<String> pks);
}
