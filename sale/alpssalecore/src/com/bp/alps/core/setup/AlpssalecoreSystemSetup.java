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
package com.bp.alps.core.setup;

import static com.bp.alps.core.constants.AlpssalecoreConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.bp.alps.core.constants.AlpssalecoreConstants;
import com.bp.alps.core.service.AlpssalecoreService;


@SystemSetup(extension = AlpssalecoreConstants.EXTENSIONNAME)
public class AlpssalecoreSystemSetup
{
	private final AlpssalecoreService alpssalecoreService;

	public AlpssalecoreSystemSetup(final AlpssalecoreService alpssalecoreService)
	{
		this.alpssalecoreService = alpssalecoreService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		alpssalecoreService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return AlpssalecoreSystemSetup.class.getResourceAsStream("/alpssalecore/sap-hybris-platform.png");
	}
}
