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
package com.bp.after.sale.facades.setup;

import static com.bp.after.sale.facades.constants.AlpsaftersalefacadesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.bp.after.sale.facades.constants.AlpsaftersalefacadesConstants;
import com.bp.after.sale.facades.service.AlpsaftersalefacadesService;


@SystemSetup(extension = AlpsaftersalefacadesConstants.EXTENSIONNAME)
public class AlpsaftersalefacadesSystemSetup
{
	private final AlpsaftersalefacadesService alpsaftersalefacadesService;

	public AlpsaftersalefacadesSystemSetup(final AlpsaftersalefacadesService alpsaftersalefacadesService)
	{
		this.alpsaftersalefacadesService = alpsaftersalefacadesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		alpsaftersalefacadesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return AlpsaftersalefacadesSystemSetup.class.getResourceAsStream("/alpsaftersalefacades/sap-hybris-platform.png");
	}
}
