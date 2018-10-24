<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<template:singleColumnPage pageTitle="${pageTitle}" bodyClass="signin">
	<div class="signinpanel sign_form">
		<div class="row">
			<div class="col-md-8 col-sm-8 col-md-offset-2 col-sm-offset-2">
				<div class="col-md-10 col-sm-10 col-md-offset-1 col-sm-offset-1">
					<div class="imgg col-md-6 col-sm-6" style="margin-top:46px;" >
						<cms:pageSlot position="LeftContentSlot" var="feature" element="div" class="login-left-content-slot">
							<cms:component component="${feature}"  element="div" class="login-left-content-component"/>
						</cms:pageSlot>
					</div>
					<div class="col-md-6 col-sm-6 ry_sign" style="">
						<common:globalMessages />
						<cms:pageSlot position="RightContentSlot" var="feature" element="div" class="logiregister__sectionn-right-content-slot">
							<cms:component component="${feature}"  element="div" class="login-right-content-component"/>
						</cms:pageSlot>
					</div>
				</div>
			</div>
		</div>
	</div>
</template:singleColumnPage>
