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
package com.bp.alps.vehiclecommercefacade.setup;

import static com.bp.alps.vehiclecommercefacade.constants.VehiclecommercefacadeConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.bp.alps.vehiclecommercefacade.constants.VehiclecommercefacadeConstants;
import com.bp.alps.vehiclecommercefacade.service.VehiclecommercefacadeService;


@SystemSetup(extension = VehiclecommercefacadeConstants.EXTENSIONNAME)
public class VehiclecommercefacadeSystemSetup
{
	private final VehiclecommercefacadeService vehiclecommercefacadeService;

	public VehiclecommercefacadeSystemSetup(final VehiclecommercefacadeService vehiclecommercefacadeService)
	{
		this.vehiclecommercefacadeService = vehiclecommercefacadeService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		vehiclecommercefacadeService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return VehiclecommercefacadeSystemSetup.class.getResourceAsStream("/vehiclecommercefacade/sap-hybris-platform.png");
	}
}
