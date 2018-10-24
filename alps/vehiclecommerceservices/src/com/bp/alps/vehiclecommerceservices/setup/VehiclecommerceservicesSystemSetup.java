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
package com.bp.alps.vehiclecommerceservices.setup;

import static com.bp.alps.vehiclecommerceservices.constants.VehiclecommerceservicesConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.bp.alps.vehiclecommerceservices.constants.VehiclecommerceservicesConstants;
import com.bp.alps.vehiclecommerceservices.service.VehiclecommerceservicesService;


@SystemSetup(extension = VehiclecommerceservicesConstants.EXTENSIONNAME)
public class VehiclecommerceservicesSystemSetup
{
	private final VehiclecommerceservicesService vehiclecommerceservicesService;

	public VehiclecommerceservicesSystemSetup(final VehiclecommerceservicesService vehiclecommerceservicesService)
	{
		this.vehiclecommerceservicesService = vehiclecommerceservicesService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		vehiclecommerceservicesService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return VehiclecommerceservicesSystemSetup.class.getResourceAsStream("/vehiclecommerceservices/sap-hybris-platform.png");
	}
}
