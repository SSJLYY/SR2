package com.bp.alps.after.sale.storefront.form.validation;

import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.RegistrationValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;


public class ConsultantRegistrationValidator extends RegistrationValidator
{
	@Override
	public void validate(final Object object, final Errors errors)
	{
		final RegisterForm registerForm = (RegisterForm) object;
		final String firstName = registerForm.getFirstName();
		final String email = registerForm.getEmail();
		final String pwd = registerForm.getPwd();
		final String checkPwd = registerForm.getCheckPwd();

		validateName(errors, firstName, "firstName", "register.firstName.invalid");

		if (StringUtils.length(firstName) > 255)
		{
			errors.rejectValue("firstName", "register.name.invalid");
		}

		validatePassword(errors, pwd);
		comparePasswords(errors, pwd, checkPwd);
	}
}
