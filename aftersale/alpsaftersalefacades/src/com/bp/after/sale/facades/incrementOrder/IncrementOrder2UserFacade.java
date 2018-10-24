package com.bp.after.sale.facades.incrementOrder;

import com.bp.after.sale.facades.data.RelatedRoleData;
import com.bp.alps.facades.data.DefaultResponse;
import de.hybris.platform.cmsfacades.data.OptionData;

import java.util.List;


public interface IncrementOrder2UserFacade
{
	DefaultResponse create(RelatedRoleData relatedRoleData);

	DefaultResponse delete(List<String> pks);

	List<OptionData> getService2RoleType();
}
