/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.bp.alps.after.sale.core.setup;

import static com.bp.alps.after.sale.core.constants.AlpsaftersalecoreConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.bp.alps.after.sale.core.constants.AlpsaftersalecoreConstants;
import com.bp.alps.after.sale.core.service.AlpsaftersalecoreService;


@SystemSetup(extension = AlpsaftersalecoreConstants.EXTENSIONNAME)
public class AlpsaftersalecoreSystemSetup
{
	private final AlpsaftersalecoreService alpsaftersalecoreService;

	public AlpsaftersalecoreSystemSetup(final AlpsaftersalecoreService alpsaftersalecoreService)
	{
		this.alpsaftersalecoreService = alpsaftersalecoreService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		alpsaftersalecoreService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return AlpsaftersalecoreSystemSetup.class.getResourceAsStream("/alpsaftersalecore/sap-hybris-platform.png");
	}
}
