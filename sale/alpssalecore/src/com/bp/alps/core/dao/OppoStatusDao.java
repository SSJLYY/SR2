package com.bp.alps.core.dao;

import com.bp.alps.core.enums.FollowType;
import com.bp.alps.core.model.oppo.type.OppoStatusModel;


public interface OppoStatusDao
{
	OppoStatusModel getOppoStatusByCode(final String code);

	OppoStatusModel getOppoStatusByFollowType(final FollowType followType);

	OppoStatusModel getDefaultOppoStatus();
}
