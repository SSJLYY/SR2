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
package com.dp.dubbo.connect.setup;

import static com.dp.dubbo.connect.constants.DubboconnectserverConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.dp.dubbo.connect.constants.DubboconnectserverConstants;
import com.dp.dubbo.connect.service.DubboconnectserverService;


@SystemSetup(extension = DubboconnectserverConstants.EXTENSIONNAME)
public class DubboconnectserverSystemSetup
{
	private final DubboconnectserverService dubboconnectserverService;

	public DubboconnectserverSystemSetup(final DubboconnectserverService dubboconnectserverService)
	{
		this.dubboconnectserverService = dubboconnectserverService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		dubboconnectserverService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return DubboconnectserverSystemSetup.class.getResourceAsStream("/dubboconnectserver/sap-hybris-platform.png");
	}
}
