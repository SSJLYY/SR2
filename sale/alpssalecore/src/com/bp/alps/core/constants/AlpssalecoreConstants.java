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
package com.bp.alps.core.constants;

/**
 * Global class for all Alpssalecore constants. You can add global constants for your extension into this class.
 */
public final class AlpssalecoreConstants extends GeneratedAlpssalecoreConstants
{
	public static final String EXTENSIONNAME = "alpssalecore";

	private AlpssalecoreConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

    public static final String PLATFORM_LOGO_CODE = "alpssalecorePlatformLogo";

	public final class PlatForm{
		public static final String INSTORE_CODE_KEY = "alps.platform.code.for.instore";
	}

	public static final String ContentCatalogVersion = "alpsContentCatalog";

	public class Product{
		public static final String ALPS_PARTS_CATEGORIES_KEY = "alps.parts.product.categories";
		public static final String ALPS_DECOR_CATEGORIES_KEY = "alps.decor.product.categories";
		public static final String ALPS_INSURANCE_CATEGORIES_KEY = "alps.insurance.product.categories";
		public static final String ALPS_EXTENDED_WARRANTY_CATEGORIES_KEY = "alps.extended.warranty.product.categories";
		public static final String ALPS_VOUCHER_CATEGORIES_KEY = "alps.voucher.product.categories";
	}
}
