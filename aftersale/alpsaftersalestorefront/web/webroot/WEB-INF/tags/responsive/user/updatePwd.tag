<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:htmlEscape defaultHtmlEscape="true"/>

<div class="signinpanel sign_form2">
    <div class="row">
                        <form:form method="post" commandName="updatePwdForm">
                            <div class="col-md-4 col-sm-4 col-md-offset-4 col-sm-offset-4 ry_sign ry_sign2">
                                    <div class="sign_up">
                                        <p class="sign_p_change">Change Password</p>
                                        <c:url value="/login" var="loginurl"/>
                                        <label class="sign_lab"><a href="${loginurl}"><spring:theme code="register.back.login.btn" /></a></label>
                                    </div>
                                    <c:if test="${empty updatePwdForm.token}">
                                        <input id="oldPwd" name="oldPwd" placeholder="<spring:theme code="register.old.pwd"/>" class="form-control sign_number2" type="password" value="" autocomplete="off">
                                    </c:if>
                                    <label class="control-label hidden" for="password" ></label>
                                    <input id="password" name="pwd" placeholder="<spring:theme code="register.pwd"/>" class="form-control" type="password" value="" autocomplete="off">

                                    <label class="control-label hidden" for="updatePwd.checkPwd"></label>
                                    <input id="updatePwd.checkPwd" name="checkPwd" placeholder="<spring:theme code="register.checkPwd"/>" class="form-control sign_password" type="password" value="" autocomplete="off">

                                    <div style="margin:264px 40px 20px 40px;">
                                        <button type="submit" class="btn btn-primary btn-block">
                                            <spring:theme code="updatePwd.submit"/>
                                        </button>
                                    </div>
                            </div>

                        </form:form>

    </div>
</div>




