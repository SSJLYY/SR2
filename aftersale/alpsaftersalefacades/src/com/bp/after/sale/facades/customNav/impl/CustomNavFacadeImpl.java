package com.bp.after.sale.facades.customNav.impl;

import com.bp.after.sale.facades.customNav.CustomNavFacade;
import com.bp.after.sale.facades.data.CustomNavData;
import com.bp.after.sale.facades.data.CustomNavList;
import com.bp.alps.after.sale.core.model.CustomNavModel;
import com.bp.alps.after.sale.core.service.CustomNavService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


public class CustomNavFacadeImpl implements CustomNavFacade
{
	@Resource
	private CustomNavService customNavService;

	@Resource
	private Converter<CustomNavModel, CustomNavData> customNavConverter;

	public CustomNavList getCustomNavList(){

		List<CustomNavModel> customNavList = customNavService.getCustomNav();
		List<CustomNavModel> defaultCustomNavList = customNavList.stream()
				.filter(customNavModel -> customNavModel.getLink()!=null)
				.filter(customNavModel -> customNavModel.getUser()==null)
				.collect(Collectors.toList());
		List<CustomNavData> defaultCustomNavData = customNavConverter.convertAll(defaultCustomNavList);

		CustomNavList customNavDataList = new CustomNavList();
		customNavDataList.setDefaultNavList(defaultCustomNavData);
		customNavDataList.setCustomerNavList(defaultCustomNavData);

		List<CustomNavModel> userCustomNavList = customNavList.stream()
				.filter(customNavModel -> customNavModel.getLink()!=null)
				.filter(customNavModel -> customNavModel.getUser()!=null)
				.collect(Collectors.toList());

		if(CollectionUtils.isNotEmpty(userCustomNavList))
		{
			List<CustomNavData> userCustomNavData = customNavConverter.convertAll(userCustomNavList);
			customNavDataList.setCustomerNavList(userCustomNavData);
		}
		return customNavDataList;
	}

	public Boolean setCustomNavByCode(String[] customNavCode){
		return customNavService.setCustomNavByCode(customNavCode);
	}
}
