package com.bp.alps.after.sale.core.service.impl;

import com.bp.alps.after.sale.core.dao.CustomNavDao;
import com.bp.alps.after.sale.core.model.CustomNavModel;
import com.bp.alps.after.sale.core.service.CustomNavService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CustomNavServiceImpl implements CustomNavService
{
	@Resource
	private CustomNavDao customNavDao;

	@Resource
	private UserService userService;

	@Resource
	private ModelService modelService;

	@Override
	public List<CustomNavModel> getCustomNav()
	{
		UserModel userModel = userService.getCurrentUser();
		if(userModel instanceof CustomerModel){
			CustomerModel customerModel = (CustomerModel)userModel;
			SearchResult<CustomNavModel> customNavList = customNavDao.getCustomNav(customerModel);
			return customNavList.getResult();
		}
		return new ArrayList<>();
	}

	public Boolean setCustomNavByCode(final String[] customNavCode){
		final List<String> customNavCodeList = Arrays.asList(customNavCode);
		List<CustomNavModel> origCustomNavs = getCustomNav();
		List<CustomNavModel> userCustomNavList = origCustomNavs.stream()
				.filter(customNavModel -> customNavModel.getLink()!=null)
				.filter(customNavModel -> customNavModel.getUser()!=null)
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(userCustomNavList)){
			final UserModel userModel = userService.getCurrentUser();
			userCustomNavList = origCustomNavs.stream().map(customNavModel -> {
				CustomNavModel newNavModel = modelService.create(CustomNavModel._TYPECODE);
				newNavModel.setLink(customNavModel.getLink());
				newNavModel.setCode(customNavModel.getCode());
				newNavModel.setImage(customNavModel.getImage());
				newNavModel.setDisplay(false);
				if(customNavCodeList.contains(customNavModel.getCode()))
				{
					newNavModel.setDisplay(true);
				}
				newNavModel.setOrder(customNavModel.getOrder());
				if(userModel instanceof CustomerModel)
				{
					newNavModel.setUser((CustomerModel) userModel);
				}
				return newNavModel;
			}).collect(Collectors.toList());
		}else{
			userCustomNavList = origCustomNavs.stream().map(customNavModel -> {
				customNavModel.setDisplay(false);
				if(customNavCodeList.contains(customNavModel.getCode()))
				{
					customNavModel.setDisplay(true);
				}
				return customNavModel;
			}).collect(Collectors.toList());
		}
		modelService.saveAll(userCustomNavList);
		return true;
	}
}
