package com.bp.after.sale.facades.facade.impl;

import com.bp.after.sale.facades.facade.AlpsAfterSalesCustomerFacade;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.util.Assert;

public class AlpsAfterSalesCustomerFacadeImpl implements AlpsAfterSalesCustomerFacade {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private CustomerAccountService customerAccountService;

    public CustomerAccountService getCustomerAccountService() {
        return customerAccountService;
    }

    public void setCustomerAccountService(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @Override
    public String forgottenPassword(final String uid)
    {
        Assert.hasText(uid, "The field [uid] cannot be empty");
        final CustomerModel customerModel = getUserService().getUserForUID(uid.toLowerCase(), CustomerModel.class);
        getCustomerAccountService().forgottenPassword(customerModel);
        String token = customerModel.getToken();
        return token;
    }



}
