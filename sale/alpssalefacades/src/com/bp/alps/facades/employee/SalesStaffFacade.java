package com.bp.alps.facades.employee;

import com.bp.alps.facades.data.SalesStaffListResponse;
import de.hybris.platform.cmsfacades.data.OptionData;
import de.hybris.platform.core.model.user.UserModel;


public interface SalesStaffFacade
{
	SalesStaffListResponse getInStoreReceptionist(final Integer currentPage, Integer pagesize);

	SalesStaffListResponse getSalesConsultant(final Integer currentPage, Integer pagesize);

	UserModel getCurrentUser();
}
