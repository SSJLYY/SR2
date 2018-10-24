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
package com.bp.alps.facades.constants;

/**
 * Global class for all Alpssalefacades constants. You can add global constants for your extension into this class.
 */
public final class AlpssalefacadesConstants extends GeneratedAlpssalefacadesConstants
{
	public static final String EXTENSIONNAME = "alpssalefacades";

	private AlpssalefacadesConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

	public static final String PLATFORM_LOGO_CODE = "alpssalefacadesPlatformLogo";
	public static final Integer DEFAULT_PAGE_SIZE = 10;

	public class ChangePwd{
		public static final String CLIENTID = "change.password.code.clientId";
	}


	public class Customer{
		public static final String inStoreReceptionistGroup = "instorereceptionist";
		public static final String salesConsultantGroup     = "salesconsultant";
	}

	public class Store{
		public static final String ALPS_BASESITE_CODE = "alps.sales.basesite.code";
	}

	public class Product{
		public static final String ALPS_PARTS_CATEGORIES_KEY = "alps.parts.product.categories";
		public static final String ALPS_DECOR_CATEGORIES_KEY = "alps.decor.product.categories";
		public static final String ALPS_INSURANCE_CATEGORIES_KEY = "alps.insurance.product.categories";
		public static final String ALPS_EXTENDED_WARRANTY_CATEGORIES_KEY = "alps.extended.warranty.product.categories";
		public static final String ALPS_VOUCHER_CATEGORIES_KEY = "alps.voucher.product.categories";
	}



}
