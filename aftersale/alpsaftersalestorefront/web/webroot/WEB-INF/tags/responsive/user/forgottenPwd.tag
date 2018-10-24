<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>


<spring:htmlEscape defaultHtmlEscape="true" />

	<div class="signinpanel sign_form2">
		<div class="row">
			<div class="col-md-4 col-sm-4 col-md-offset-4 col-sm-offset-4">
				<div class="col-md-10 col-sm-10 col-md-offset-1 col-md-offset-1 ry_sign ry_sign2">

					<div class="sign_up">
						<p class="sign_p"><spring:theme code="text.account.changePassword" /></p>
						<c:url value="/login" var="loginurl"/>
						<label class="sign_lab"><a href="${loginurl}"><spring:theme code="register.back.login.btn" /></a></label>
					</div>
					<c:if test="${not empty message}">
						<span class="has-error"> <spring:theme code="${message}" /></span>
					</c:if>

					<div class="forgotten-password">
						<div class="description">
							<%--<spring:theme code="forgottenPwd.description"/>--%>
						</div>
						<form:form method="post" commandName="forgottenPwdForm">
							<div class="control-group">
									<%--<ycommerce:testId code="login_forgotPasswordEmail_input">
                                        <formElement:formInputBox idKey="forgottenPwd.email" labelKey="forgottenPwd.email" path="email" mandatory="false"/>
                                    </ycommerce:testId>--%>

								<label class="word_spac " for="forgottenPwd.username"></label>
								<input id="forgottenPwd.username" name="username" class="form-control sign_number2" placeholder="<spring:theme code="login.register.forgottenPwd.phone" />"  type="text" value="">

								<div class="row row_left">
								<div class="has-error">
									<div class="help-block">
										<form:errors path="username" />
									</div>
								</div>
								</div>

								<div style="margin:214px 40px 20px 40px;">
									<ycommerce:testId code="login_forgotPasswordSubmit_button">
										<button class="btn btn-primary btn-block" type="submit">
											<spring:theme code="text.account.changePassword"/>
										</button>
									</ycommerce:testId>
								</div>

							</div>
						</form:form>
					</div>

				</div>
			</div>
		</div>
	</div>



















</div>



