package com.bp.alps.vehiclecommercefacade.vehicle;

import com.bp.alps.facades.data.DefaultResponse;
import com.bp.alps.facades.data.vehicle.Vehicle2User;
import com.bp.alps.facades.data.vehicle.VehicleInfoData;

import java.util.List;


public interface Vehicle2UserFacade
{
	DefaultResponse create(Vehicle2User vehicle2User);

	DefaultResponse delete(List<String> pks);
}
