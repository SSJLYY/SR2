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
package com.bp.after.sale.facades.constants;

/**
 * Global class for all Alpsaftersalefacades constants. You can add global constants for your extension into this class.
 */
public final class AlpsaftersalefacadesConstants extends GeneratedAlpsaftersalefacadesConstants
{
	public static final String EXTENSIONNAME = "alpsaftersalefacades";

	private AlpsaftersalefacadesConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

    public static final String PLATFORM_LOGO_CODE = "alpsaftersalefacadesPlatformLogo";
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final Integer DEFAULT_CURRENT_PAGE = 0;

	public class Customer{
		public static final String serviceConsultantGroup = "salesconsultant";
	}
}
