package com.bp.alps.vehiclecommerceservices.service.impl;

import com.bp.alps.facades.data.quotation.QuotationData;
import com.bp.alps.vehiclecommerceservices.utils.ValidateClassUtils;
import de.hybris.bootstrap.annotations.UnitTest;
import org.junit.Test;


@UnitTest
public class TestService
{
	@Test
	public void shouldReturnProperUrlForLogo() throws Exception
	{
		QuotationData quotationData = new QuotationData();
		quotationData.setCarColor("123");
		Boolean a = ValidateClassUtils.isAllFieldNull(quotationData);
		System.out.print(a);
	}
}
