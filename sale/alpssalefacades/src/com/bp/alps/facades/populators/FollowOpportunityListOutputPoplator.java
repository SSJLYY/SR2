package com.bp.alps.facades.populators;

import com.bp.alps.core.model.type.FollowContentModel;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.opp.FollowContentData;
import com.bp.alps.facades.data.opp.FollowOpportunityListData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import javax.annotation.Resource;


public class FollowOpportunityListOutputPoplator implements Populator<FollowOpportunityModel,FollowOpportunityListData>
{
	@Resource
	private Converter<FollowContentModel,FollowContentData> followContentOutputConverter;

	@Resource
	private EnumerationService enumerationService;

	@Override
	public void populate(final FollowOpportunityModel source, FollowOpportunityListData target) throws ConversionException
	{
		target.setCode(source.getCode());
		if(source.getSalesconsultant()!=null){
			target.setSalesConsultantId(source.getSalesconsultant().getUid());
			target.setSalesConsultantName(source.getSalesconsultant().getName());
			if(source.getSalesconsultant().getStore()!=null){
				target.setStoreName(source.getSalesconsultant().getStore().getName());
			}
		}

		if(source.getContentList()!=null) target.setContentList(followContentOutputConverter.convertAll(source.getContentList()));
		target.setCreationtime(DateFormatUtils.getDateString("datetime", source.getCreationtime()));
		if(source.getType()!=null){
			target.setTypeCode(source.getType().getCode());
			target.setType(enumerationService.getEnumerationName(source.getType()));
		}
	}
}
