<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="hideDescription" value="checkout.login.loginAndCheckout" />

<p class="sign_p">
	<spring:theme code="login.login" />
</p>
<c:if test="${not empty message}">
	<span class="has-error"> <spring:theme code="${message}" />
	</span>
</c:if>
<form:form action="${action}" method="post" commandName="loginForm">
	<input id="j_username" name="j_username" type="text" class="form-control sign_number" value="" placeholder="<spring:theme code="login.username.placeholder" />"/>
	<input id="j_password" name="j_password" class="form-control sign_password" type="password" value="" placeholder="<spring:theme code="login.password.placeholder" />" autocomplete="off"/>
	<button type="submit" class="btn btn-primary btn-block">
		<spring:theme code="${actionNameKey}" />
	</button>
	<div class="sign_retrieve">
		<p class="pull-left"><spring:theme code="login.not.account" /><span><a href="<c:url value="/register"/>" style="margin-left:5px;"><spring:theme code="login.register.btn" /></a></span></p>
		<p class="pull-right"><a href="<c:url value='/login/pw/request'/>" style="color:#999999"><spring:theme code="login.register.forgottenPwd" /></a></p>
	</div>

</form:form>

