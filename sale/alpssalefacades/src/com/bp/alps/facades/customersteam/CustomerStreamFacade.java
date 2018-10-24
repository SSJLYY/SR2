package com.bp.alps.facades.customersteam;

import com.bp.alps.facades.data.CustomerFlowData;
import com.bp.alps.facades.data.CustomerStreamListResponse;
import com.bp.alps.facades.data.CustomerStreamDetail;
import com.bp.alps.facades.data.DefaultResponse;
import de.hybris.platform.cmsfacades.data.OptionData;

import java.util.List;


public interface CustomerStreamFacade
{
	DefaultResponse saveCustomerSteam(CustomerFlowData customerFlowData);

	CustomerStreamListResponse getTodayCustomerStreamByCUser(final Integer currentPage, Integer pagesize);

	CustomerStreamDetail getCustomerStreamByCode(String code);

	List<OptionData> getTypes();

	List<OptionData> getAllStatus();

	List<OptionData> getGenders();
}
