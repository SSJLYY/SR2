<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<template:singleColumnPage pageTitle="${pageTitle}" bodyClass="signin2">
	<div class="signinpanel sign_form2">
		<div class="row">
			<div class="col-md-4 col-sm-4 col-md-offset-4 col-sm-offset-4">
				<div class="col-md-10 col-sm-10 col-md-offset-1 col-md-offset-1 ry_sign ry_sign2">
					<common:globalMessages />
					<cms:pageSlot position="LeftContentSlot" var="feature" element="div" class="login-left-content-slot">
						<cms:component component="${feature}"  element="div" class="login-left-content-component"/>
					</cms:pageSlot>
					<cms:pageSlot position="RightContentSlot" var="feature" element="div" class="logiregister__sectionn-right-content-slot">
						<cms:component component="${feature}"  element="div" class="login-right-content-component"/>
					</cms:pageSlot>
				</div>
			</div>
		</div>
	</div>
</template:singleColumnPage>
