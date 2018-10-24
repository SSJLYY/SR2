package com.bp.alps.vehiclecommerceservices.service.impl;

import com.bp.alps.vehiclecommerceservices.dao.VehicleDao;
import com.bp.alps.vehiclecommerceservices.dao.vehicle.Vehicle2UserDao;
import com.bp.alps.vehiclecommerceservices.dao.vehicle.VehicleIncreaseDao;
import com.bp.alps.vehiclecommerceservices.dao.vehicle.VehicleInsuranceDao;
import com.bp.alps.vehiclecommerceservices.dao.vehicle.VehicleMaintainDao;
import com.bp.alps.vehiclecommerceservices.enums.Vehicle2UserType;
import com.bp.alps.vehiclecommerceservices.enums.VehicleStatus;
import com.bp.alps.vehiclecommerceservices.model.*;
import com.bp.alps.vehiclecommerceservices.service.VehicleService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.PK;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class VehicleServiceImpl implements VehicleService
{
	@Resource
	private ModelService modelService;

	@Resource
	private VehicleDao vehicleDao;

	@Resource
	private KeyGenerator vehicleCodeGenerator;

	@Resource
	private VehicleInsuranceDao vehicleInsuranceDao;

	@Resource
	private VehicleIncreaseDao vehicleIncreaseDao;

	@Resource
	private VehicleMaintainDao vehicleMaintainDao;

	@Resource
	private Vehicle2UserDao vehicle2UserDao;

	private String keyPrefix;

	@Override
	public VehicleInfoModel getVehicleByCode(String code)
	{
		return vehicleDao.getVehicleByCode(code);
	}

	public Boolean deleteVehicle2UserByPks(List<String> pks){
		List<Vehicle2UserModel> vehicle2UserList = getVehicle2UserByPks(pks);
		modelService.removeAll(vehicle2UserList);
		return true;
	}

	public Boolean deleteVehicleInsurancePks(List<String> pks){
		List<VehicleInsuranceInfoModel> vehicleInsuranceList = getVehicleInsurancePks(pks);
		modelService.removeAll(vehicleInsuranceList);
		return true;
	}

	public List<Vehicle2UserModel> getVehicle2UserByPks(List<String> pks){
		List<Vehicle2UserModel> vehicle2UserList = getModelByPks(pks, Vehicle2UserModel.class);
		return vehicle2UserList;
	}

	public List<VehicleInsuranceInfoModel> getVehicleInsurancePks(List<String> pks){
		List<VehicleInsuranceInfoModel> vehicleInsuranceList = getModelByPks(pks, VehicleInsuranceInfoModel.class);
		return vehicleInsuranceList;
	}

	public <T> List<T> getModelByPks(List<String> pks, Class className){
		Collection<PK> pkList = PK.parse(pks);
		List<Object> objectList = pkList.stream().map(pk -> modelService.get(pk)).collect(Collectors.toList());
		List instanceList = objectList.stream().filter(object-> className.isInstance(object)).map(o -> className.cast(o)).collect(Collectors.toList());
		return instanceList;
	}


	public SearchPageData<VehicleInfoModel> getRelatedVehiclesByCustomerUid(String code, PageableData pageableData){
		return vehicleDao.getRelatedVehiclesByCustomerUid(code, pageableData);
	}

	public Vehicle2UserModel createVehicle2User(){
		Vehicle2UserModel vehicle2UserModel = modelService.create(Vehicle2UserModel.class);
		vehicle2UserModel.setUserType(Vehicle2UserType.OWNER);
		return vehicle2UserModel;
	}

	public VehicleInsuranceInfoModel createVehicleInsurance(){
		VehicleInsuranceInfoModel vehicleInsuranceInfoModel = modelService.create(VehicleInsuranceInfoModel.class);
		return vehicleInsuranceInfoModel;
	}

	public void saveVehicleInsurance(VehicleInsuranceInfoModel vehicleInsuranceInfoModel)
	{
		modelService.save(vehicleInsuranceInfoModel);
	}

	public void saveVehicle2UserModel(Vehicle2UserModel vehicle2UserModel)
	{
		modelService.save(vehicle2UserModel);
	}

	public List<VehicleIncreaseInfoModel> getIncreaseByVehicle(VehicleInfoModel vehicleInfoModel){
		return vehicleIncreaseDao.getIncreaseByVehicle(vehicleInfoModel);
	}

	public List<VehicleInsuranceInfoModel> getInsuranceByVehicle(VehicleInfoModel vehicleInfoModel){
		return vehicleInsuranceDao.getInsuranceByVehicle(vehicleInfoModel);
	}

	public List<VehicleMaintainInfoModel> getMaintainByVehicle(VehicleInfoModel vehicleInfoModel){
		return vehicleMaintainDao.getMaintainByVehicle(vehicleInfoModel);
	}

	public List<Vehicle2UserModel> getRelatedUserByVehice(VehicleInfoModel vehicleInfoModel){
		return vehicle2UserDao.getUserByVehice(vehicleInfoModel);
	}

	public VehicleInfoModel createVehicle()
	{
		VehicleInfoModel vehicleInfoModel = modelService.create(VehicleInfoModel.class);
		vehicleInfoModel.setCode(keyPrefix + vehicleCodeGenerator.generate().toString());
		vehicleInfoModel.setStatus(VehicleStatus.VALID);
		return vehicleInfoModel;
	}

	@Override
	public void saveVehicleModel(VehicleInfoModel vehicleInfoModel)
	{
		Boolean newModel = false;
		if(vehicleInfoModel.getPk()==null) newModel=true;
		modelService.save(vehicleInfoModel);
		if(newModel){
			CreateAffiliateUserInfo(vehicleInfoModel);
		}
	}

	protected void CreateAffiliateUserInfo(VehicleInfoModel vehicleInfoModel){
		Vehicle2UserModel vehicle2UserModel = modelService.create(Vehicle2UserModel.class);
		vehicle2UserModel.setVehicleInfo(vehicleInfoModel);
		vehicle2UserModel.setUserType(Vehicle2UserType.OWNER);
		vehicle2UserModel.setUser(vehicleInfoModel.getCustomer());
		modelService.save(vehicle2UserModel);
	}

	@Override
	public SearchPageData<VehicleInfoModel> searchVehicle(String customerName, String mobileNumber, String vehicleCode, String vinCode, String licensePlateNumber, String beforetimeKey, PageableData pageableData)
	{
		return vehicleDao.searchVehicle(customerName, mobileNumber, vehicleCode, vinCode, licensePlateNumber, beforetimeKey, pageableData);
	}

	public String getKeyPrefix()
	{
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix){
		this.keyPrefix = keyPrefix;
	}
}
