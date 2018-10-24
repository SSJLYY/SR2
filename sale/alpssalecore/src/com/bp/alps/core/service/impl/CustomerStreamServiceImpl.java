package com.bp.alps.core.service.impl;

import com.bp.alps.core.dao.CustomerStreamDao;
import com.bp.alps.core.model.type.CustomerStreamModel;
import com.bp.alps.core.service.CustomerSteamService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;

import java.util.Date;
import java.util.List;


public class CustomerStreamServiceImpl implements CustomerSteamService
{
	@Resource
	private ModelService modelService;

	@Resource
	private KeyGenerator customerFlowCodeGenerator;

	@Resource
	private CustomerStreamDao customerStreamDao;

	private String keyPrefix;

	@Override
	public CustomerStreamModel createCustomerSteam(CustomerStreamModel customerStreamModel)
	{
		generateCode(customerStreamModel);
		return updateCustomerSteam(customerStreamModel);
	}

	public CustomerStreamModel updateCustomerSteam(CustomerStreamModel customerStreamModel)
	{
		modelService.save(customerStreamModel);
		return customerStreamModel;
	}

	public SearchPageData<CustomerStreamModel> getCustomerStreamByUserBeforDate(final CustomerModel customerModel, final Date date, final PageableData pageableData){
		return customerStreamDao.getCustomerStreamByUserBeforDate(customerModel, date, pageableData);
	}

	public List<CustomerStreamModel> getCustomerStreamByCode(String code){
		return customerStreamDao.getCustomerStreamByCode(code);
	}

	protected CustomerStreamModel generateCode(CustomerStreamModel customerStreamModel){
		String keycode = String.valueOf(customerFlowCodeGenerator.generate());
		customerStreamModel.setCode(keyPrefix+keycode);
		return customerStreamModel;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix){
		this.keyPrefix=keyPrefix;
	}
}
