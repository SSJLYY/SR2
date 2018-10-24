package com.bp.alps.vehiclecommerceservices.order.impl;

import com.bp.alps.vehiclecommerceservices.model.ReturnEntryRecordModel;
import com.bp.alps.vehiclecommerceservices.order.ReturnRequestFactory;
import de.hybris.platform.basecommerce.enums.ReturnAction;
import de.hybris.platform.basecommerce.enums.ReturnStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.returns.model.ReturnRequestModel;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;


public class ReturnRequestFactoryImpl implements ReturnRequestFactory
{
	private KeyGenerator keyGenerator;
	@Resource
	private ModelService modelService;
	@Resource
	private UserService userService;

	public ReturnRequestModel initializeReturnRequest(AbstractOrderModel orderModel)
	{
		final UserModel user = userService.getCurrentUser();
		final ReturnRequestModel returnRequest = modelService.create(ReturnRequestModel._TYPECODE);
		returnRequest.setCode(String.valueOf(keyGenerator.generate()));
		if(user instanceof CustomerModel)
		{
			returnRequest.setConsultant((CustomerModel) user);
		}
		returnRequest.setUser((CustomerModel)orderModel.getUser());
		returnRequest.setVehicle(orderModel.getVehicle());
		returnRequest.setStatus(ReturnStatus.RECEIVING);
		return returnRequest;
	}

	public ReturnEntryRecordModel initializeReturnEntry(AbstractOrderEntryModel entryModel){
		final ReturnEntryRecordModel returnEntry = modelService.create(ReturnEntryRecordModel._TYPECODE);
		returnEntry.setExpectedQuantity(entryModel.getQuantity());
		returnEntry.setReceivedQuantity(entryModel.getQuantity());
		returnEntry.setOrderEntry(entryModel);
		returnEntry.setStatus(ReturnStatus.RECEIVED);
		returnEntry.setAction(ReturnAction.IMMEDIATE);
		return returnEntry;
	}

	@Required
	public void setKeyGenerator(final KeyGenerator keyGenerator)
	{
		this.keyGenerator = keyGenerator;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}
}
