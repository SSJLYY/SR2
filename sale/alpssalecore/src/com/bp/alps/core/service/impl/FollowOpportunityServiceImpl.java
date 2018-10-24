package com.bp.alps.core.service.impl;

import com.bp.alps.core.dao.FollowOpportunityDao;
import com.bp.alps.core.dao.MarketActivityDao;
import com.bp.alps.core.model.MarketActivityModel;
import com.bp.alps.core.model.type.FollowContentModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.core.model.type.OpportunityModel;
import com.bp.alps.core.service.FollowOpportunityService;
import com.bp.alps.core.service.OpportunityServices;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class FollowOpportunityServiceImpl implements FollowOpportunityService
{
	@Resource
	private MarketActivityDao marketActivityDao;

	@Resource
	private FollowOpportunityDao followOpportunityDao;

	@Resource
	private KeyGenerator followCodeGenerator;

	@Resource
	private ModelService modelService;

	@Resource
	private UserService userService;

	@Resource
	private OpportunityServices opportunityServices;

	@Override
	public List<MarketActivityModel> getAllMarketActivity()
	{
		return marketActivityDao.getAllMarketActivity();
	}

	public List<MarketActivityModel> searchMarketActivity(final String name)
	{
		return marketActivityDao.searchMarketActivity(name);
	}

	public List<FollowOpportunityModel> getFollowOpportunityList(OpportunityModel opportunityModel){
		return followOpportunityDao.getFollowOpportunityList(opportunityModel);
	}

	public FollowOpportunityModel saveFollowOpportunity(FollowOpportunityModel followOpportunityModel){
		modelService.save(followOpportunityModel);
		return followOpportunityModel;
	}

	public FollowOpportunityModel saveFollowOpportunity(OpportunityModel opportunityModel, FollowOpportunityModel followOpportunityModel){
		if(StringUtils.isBlank(followOpportunityModel.getCode())) followOpportunityModel.setCode(followCodeGenerator.generate().toString());
		followOpportunityModel.setOpportunity(opportunityModel);
		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel){
			followOpportunityModel.setSalesconsultant((CustomerModel)userModel);
		}
		modelService.save(followOpportunityModel);

		opportunityServices.additionaFolowlines(opportunityModel, followOpportunityModel);

		return followOpportunityModel;
	}

	public FollowContentModel saveFollowContent(FollowContentModel followContentModel, FollowOpportunityModel followOpportunityModel){
		followContentModel.setFollowOpportunity(followOpportunityModel);
		modelService.save(followContentModel);

		List<FollowContentModel> followContentModels = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(followOpportunityModel.getContentList())){
			followContentModels = new ArrayList<>(followOpportunityModel.getContentList());
		}
		followContentModels.add(followContentModel);
		followOpportunityModel.setContentList(this.modelService.toModelLayer(followContentModels));
		modelService.save(followOpportunityModel);
		return followContentModel;
	}

	public FollowOpportunityModel getFollowOpportunityByCode(String code){
		return followOpportunityDao.getFollowOpportunityByCode(code);
	}
}
