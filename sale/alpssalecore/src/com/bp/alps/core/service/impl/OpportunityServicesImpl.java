package com.bp.alps.core.service.impl;

import com.bp.alps.core.dao.OppoStatusDao;
import com.bp.alps.core.dao.OpportunityDao;
import com.bp.alps.core.dao.OpportunityLevelDao;
import com.bp.alps.core.model.oppo.level.OpportunityLevelModel;
import com.bp.alps.core.model.oppo.type.OppoStatusModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.service.OpportunityServices;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.*;


public class OpportunityServicesImpl extends DefaultAlpssalecoreService implements OpportunityServices
{
	private String keyPrefix;

	@Resource
	private KeyGenerator opportunityCodeGenerator;

	@Resource
	private OpportunityDao opportunityDao;

	@Resource
	private ModelService modelService;

	@Resource
	private UserService userService;

	@Resource
	private OpportunityLevelDao opportunityLevelDao;

	@Resource
	private OppoStatusDao oppoStatusDao;

	@Override
	public OpportunityModel getOpportunityByKey(final String code, final String mobile)
	{
		Map<String,Object> key = new HashMap<>();
		if(StringUtils.isNotBlank(code)) key.put(OpportunityModel.CODE, code);
		if(StringUtils.isNotBlank(mobile)) key.put(OpportunityModel.MOBILE, mobile);
		return opportunityDao.getOpportunityByKey(key);
	}

	public void setTestDriveConfrimStatus(OpportunityModel opportunityModel){
		OppoStatusModel oppoStatus = oppoStatusDao.getOppoStatusByCode(Config.getString("alps.oppo.test.drive.confirm.status.key","confirmTestDrive"));
		if(oppoStatus!=null)
		{
			if(opportunityModel.getStatus()!=null){
				if(oppoStatus.getOrder() > opportunityModel.getStatus().getOrder()){
					opportunityModel.setStatus(oppoStatus);
					modelService.save(opportunityModel);
				}
			}else{
				opportunityModel.setStatus(oppoStatus);
				modelService.save(opportunityModel);
			}
		}
	}

	public void getSaleAccess(OpportunityModel opportunityModel){
		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel){
			opportunityModel.setSalesconsultant((CustomerModel)userModel);
			modelService.save(opportunityModel);
		}
	}

	public OpportunityLevelModel getOpportunityLevelByCode(String code){
		return opportunityLevelDao.getOpportunityLevelByCode(code);
	}

	public OpportunityModel additionaFolowlines(OpportunityModel opportunityModel, FollowOpportunityModel followOpportunityModel)
	{
		List<FollowOpportunityModel> followOpportunityModels = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(opportunityModel.getFollowList()))
		{
			followOpportunityModels = new ArrayList<>(opportunityModel.getFollowList());
		}
		followOpportunityModels.add(followOpportunityModel);
		opportunityModel.setFollowList(this.modelService.toModelLayer(followOpportunityModels));

		opportunityModel.setFollowCount(opportunityModel.getFollowCount()+1);

		opportunityModel.setLastFollowTime(new Date());

		if(followOpportunityModel.getType()!=null){
			final OppoStatusModel oppoStatus = opportunityModel.getStatus();
			if(oppoStatus!=null){
				OppoStatusModel newOppoStatus = oppoStatusDao.getOppoStatusByFollowType(followOpportunityModel.getType());
				if(newOppoStatus!=null && newOppoStatus.getOrder()>oppoStatus.getOrder()){
					opportunityModel.setStatus(newOppoStatus);
				}
			}else{
				opportunityModel.setStatus(oppoStatusDao.getOppoStatusByFollowType(followOpportunityModel.getType()));
			}
		}
		modelService.save(opportunityModel);
		return opportunityModel;
	}

	@Override
	public OpportunityModel saveOpportunity(OpportunityModel opportunityModel)
	{
		initOpportunity(opportunityModel);
		modelService.save(opportunityModel);
		return opportunityModel;
	}

	protected void initOpportunity(OpportunityModel opportunityModel){
		if(StringUtils.isBlank(opportunityModel.getCode())){
			String keycode = String.valueOf(opportunityCodeGenerator.generate());
			opportunityModel.setCode(keyPrefix+keycode);
			opportunityModel.setLastFollowTime(new Date());
			opportunityModel.setStatus(oppoStatusDao.getDefaultOppoStatus());
			UserModel userModel = userService.getCurrentUser();
			if(userModel instanceof CustomerModel){
				opportunityModel.setSalesconsultant((CustomerModel)userModel);
				opportunityModel.setCreatedPerson((CustomerModel)userModel);
			}
		}
	}

	@Override
	public SearchPageData<OpportunityModel> getOpportunityBySales(CustomerModel customer, final String moblie, final String opportunityLevel, PageableData pageableData){
		return opportunityDao.getOpportunityBySales(customer, opportunityLevel, moblie, pageableData);
	}

	public String getKeyPrefix()
	{
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix){
		this.keyPrefix=keyPrefix;
	}
}
