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
package com.bp.alps.facades.setup;

import static com.bp.alps.facades.constants.AlpssalefacadesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.bp.alps.facades.constants.AlpssalefacadesConstants;
import com.bp.alps.facades.service.AlpssalefacadesService;


@SystemSetup(extension = AlpssalefacadesConstants.EXTENSIONNAME)
public class AlpssalefacadesSystemSetup
{
	private final AlpssalefacadesService alpssalefacadesService;

	public AlpssalefacadesSystemSetup(final AlpssalefacadesService alpssalefacadesService)
	{
		this.alpssalefacadesService = alpssalefacadesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		alpssalefacadesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return AlpssalefacadesSystemSetup.class.getResourceAsStream("/alpssalefacades/sap-hybris-platform.png");
	}
}
