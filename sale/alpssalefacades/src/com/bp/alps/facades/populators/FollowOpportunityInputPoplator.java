package com.bp.alps.facades.populators;

import com.bp.alps.core.dao.MarketActivityDao;
import com.bp.alps.core.enums.FollowType;
import com.bp.alps.core.model.type.FollowOpportunityModel;
import com.bp.alps.vehiclecommerceservices.utils.DateFormatUtils;
import com.bp.alps.facades.data.opp.FollowOpportunityData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;


public class FollowOpportunityInputPoplator implements Populator<FollowOpportunityData,FollowOpportunityModel>
{
	@Resource
	private MarketActivityDao marketActivityDao;

	@Resource
	private ProductService productService;

	@Override
	public void populate(final FollowOpportunityData source, FollowOpportunityModel target) throws ConversionException
	{
		target.setConnectCustomer(source.getConnectCustomer());
		if(StringUtils.isNotBlank(source.getMarketActivityCode())) target.setMarketActivity(marketActivityDao.getMarketActivityByCode(source.getMarketActivityCode()));
		if(source.getTypeCode()!=null) target.setType(FollowType.valueOf(source.getTypeCode()));
		if(source.getVehicleCode()!=null) target.setVehicle(productService.getProductForCode(source.getVehicleCode()));
		if(source.getBookingCartSTime()!=null) target.setBookingCartSTime(DateFormatUtils.getDate("datetime", source.getBookingCartSTime()));
		if(source.getBookingCartNTime()!=null) target.setBookingCartNTime(DateFormatUtils.getDate("datetime", source.getBookingCartNTime()));
	}
}
