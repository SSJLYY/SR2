package com.dp.webservices.controllers;

import com.dp.alps.facades.data.DefaultResponse;
import com.dp.alps.facades.data.vehicle.SearchVehicleRequest;
import com.dp.alps.facades.data.vehicle.SearchVehicleResponse;
import com.dp.alps.facades.data.vehicle.VehicleInfoData;
import com.dp.alps.facades.vehicle.VehicleFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/vehicle")
public class VehicleController
{
	@Resource
	private VehicleFacade vehicleFacade;

	@RequestMapping(value = "/list", method = { RequestMethod.POST})
	@ResponseBody
	public SearchVehicleResponse searchCustomer(@RequestBody SearchVehicleRequest searchVehicleRequest){
		return vehicleFacade.searchVehicle(searchVehicleRequest);
	}

	@RequestMapping(value = "/createOrUpdate", method = { RequestMethod.POST})
	@ResponseBody
	public DefaultResponse create(@RequestBody VehicleInfoData vehicleInfoData){
		return vehicleFacade.createOrUpdateRequest(vehicleInfoData);
	}
}
