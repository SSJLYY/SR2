<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true"
	type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class="sign_up">
	<p class="sign_p"><spring:theme code="register.new.customer" /></p>
	<c:url value="/login" var="loginurl"/>
	<label class="sign_lab"><a href="${loginurl}"><spring:theme code="register.back.login.btn" /></a></label>
</div>
<c:if test="${not empty message}">
	<span class="has-error"> <spring:theme code="${message}" />
	</span>
</c:if>

<form:form method="post" commandName="registerForm" action="${action}">
	<input id="register.firstName" placeholder="<spring:theme code="register.firstName" />" name="firstName" class="form-control" type="text" value="">
	<input id="register.lastName" name="lastName" class="form-control" type="hidden" value="/">
	<input id="register.title" name="titleCode" class="form-control" type="hidden" value="rev">
	<input id="register.email" placeholder="<spring:theme code="register.email" />" name="email" class="form-control" type="text" value="">
	<input id="password" name="pwd" placeholder="<spring:theme code="register.pwd" />" class="form-control password-strength" type="password" value="" autocomplete="off">
	<input id="register.checkPwd" placeholder="<spring:theme code="register.checkPwd" />" name="checkPwd" class="form-control" type="password" value="" autocomplete="off">


	<input type="hidden" id="recaptchaChallangeAnswered"
		value="${requestScope.recaptchaChallangeAnswered}" />
	<div class="form_field-elements control-group js-recaptcha-captchaaddon"></div>
	<div class="form-actions clearfix">
		<ycommerce:testId code="register_Register_button">
			<button type="submit" class="btn btn-primary ry_register btn-block">
				<spring:theme code='${actionNameKey}' />
			</button>
		</ycommerce:testId>
	</div>
</form:form>
