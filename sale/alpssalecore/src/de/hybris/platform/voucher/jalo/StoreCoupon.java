/*
 *  
 * [y] hybris Platform
 *  
 * Copyright (c) 2000-2016 SAP SE
 * All rights reserved.
 *  
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 *  
 */
package de.hybris.platform.voucher.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.user.User;
import org.apache.log4j.Logger;

public class StoreCoupon extends GeneratedStoreCoupon
{
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger( StoreCoupon.class.getName() );
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem( ctx, type, allAttributes );
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	public boolean isReservable(String aVoucherCode, User user) {
		return this.getInvalidations(aVoucherCode, user).size() < this.getRedemptionQuantityLimitPerUserAsPrimitive() && this.getInvalidations(aVoucherCode).size() < this.getRedemptionQuantityLimitAsPrimitive();
	}

	public boolean checkVoucherCode(String aVoucherCode) {
		return aVoucherCode.equals(this.getVoucherCode());
	}

	protected int getNextVoucherNumber(SessionContext ctx) {
		return 1;
	}
}
