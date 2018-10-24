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
package com.bp.alps.after.sale.core.constants;

/**
 * Global class for all Alpsaftersalecore constants. You can add global constants for your extension into this class.
 */
public final class AlpsaftersalecoreConstants extends GeneratedAlpsaftersalecoreConstants
{
	public static final String EXTENSIONNAME = "alpsaftersalecore";

	private AlpsaftersalecoreConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

    public static final String PLATFORM_LOGO_CODE = "alpsaftersalecorePlatformLogo";

	public class Product{
		public static final String ALPS_PARTS_CATEGORIES_KEY = "alps.after.sale.parts.product.categories";
		public static final String ALPS_MANHOURS_CATEGORIES_KEY = "alps.after.sale.manhouse.product.categories";
	}

	public class Category{
		public static final String ROOT_CATEGORY_CODE_FOR_AFTER_SALE="alps.after.sale.root.category.code";
	}
}
