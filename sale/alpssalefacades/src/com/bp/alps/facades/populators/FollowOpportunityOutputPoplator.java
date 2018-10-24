package com.bp.alps.facades.populators;

import com.bp.alps.core.model.type.FollowContentModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.opp.FollowContentData;
import com.bp.alps.facades.data.opp.FollowOpportunityData;
import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;


public class FollowOpportunityOutputPoplator implements Populator<FollowOpportunityModel,FollowOpportunityData>
{
	@Resource
	private Converter<FollowContentModel,FollowContentData> followContentOutputConverter;

	@Resource
	private EnumerationService enumerationService;

	@Resource
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;

	@Resource
	private BaseSiteService baseSiteService;

	@Override
	public void populate(final FollowOpportunityModel source, FollowOpportunityData target) throws ConversionException
	{
		target.setCode(source.getCode());
		if(source.getSalesconsultant()!=null){
			target.setSalesConsultantId(source.getSalesconsultant().getUid());
			target.setSalesConsultantName(source.getSalesconsultant().getName());
			BaseStoreModel baseStoreModel = source.getSalesconsultant().getStore();
			if(baseStoreModel!=null){
				target.setStoreName(baseStoreModel.getName());
			}
		}
		target.setConnectCustomer(source.getConnectCustomer());
		if(source.getMarketActivity()!=null)
		{
			target.setMarketActivityCode(source.getMarketActivity().getCode());
			target.setMarketActivityName(source.getMarketActivity().getName());
		}
		if(source.getContentList()!=null) target.setContentList(followContentOutputConverter.convertAll(source.getContentList()));
		target.setCreationtime(DateFormatUtils.getDateString("datetime", source.getCreationtime()));
		if(source.getType()!=null){
			target.setTypeCode(source.getType().getCode());
			target.setType(enumerationService.getEnumerationName(source.getType()));
		}
		if(source.getVehicle()!=null){
			target.setVehicle(source.getVehicle().getName());
			target.setVehicleCode(source.getVehicle().getCode());
			Collection<CategoryModel> categoryModelList = source.getVehicle().getSupercategories();
			if(CollectionUtils.isNotEmpty(categoryModelList)){
				Optional<CategoryModel> optional = categoryModelList.stream().findAny();
				CategoryModel categoryModel = optional.get();
				target.setVehicleCategoryCode(categoryModel.getName());
				target.setVehicleCode(categoryModel.getCode());
			}
		}
		if(source.getBookingCartSTime()!=null) target.setBookingCartSTime(DateFormatUtils.getDateString("datetime", source.getBookingCartSTime()));
		if(source.getBookingCartNTime()!=null) target.setBookingCartNTime(DateFormatUtils.getDateString("datetime", source.getBookingCartNTime()));
		target.setCustomerName(source.getCustomerName());
		target.setLicenseNumber(source.getLicenseNumber());
		target.setCustomerConfirm(source.getCustomerConfirm());
		if(source.getLicense()!=null){
			final String licenseUrl = siteBaseUrlResolutionService.getMediaUrlForSite(baseSiteService.getCurrentBaseSite(),true, source.getLicense().getURL());
			target.setLicenseUrl(licenseUrl);
		}
		target.setShareUrl(siteBaseUrlResolutionService.getWebsiteUrlForSite(baseSiteService.getCurrentBaseSite(), true, "/follow/confirmation", "followCode="+source.getCode()));
		if(source.getOpportunity()!=null) target.setOpportunityCode(source.getOpportunity().getCode());
	}
}
