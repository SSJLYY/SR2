<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ attribute name="pageCss" required="false" fragment="true"%>
<%@ attribute name="pageScripts" required="false" fragment="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<template:page pageTitle="${pageTitle}">
	<div class="row">
		<div class="col-sm-12 col-md-12">
			<cms:pageSlot position="Section1" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</div>
		<div class="col-sm-12 col-md-12">
			<jsp:doBody />
		</div>
		<div class="col-sm-12 col-md-12">
			<div class="col-md-6">
				<cms:pageSlot position="LeftContentSlot" var="feature" element="div" class="login-left-content-slot">
					<cms:component component="${feature}"  element="div" class="login-left-content-component"/>
				</cms:pageSlot>
			</div>
			<div class="col-md-6">
				<cms:pageSlot position="RightContentSlot" var="feature" element="div" class="logiregister__sectionn-right-content-slot">
					<cms:component component="${feature}"  element="div" class="login-right-content-component"/>
				</cms:pageSlot>
			</div>
		</div>
	</div>
</template:page>
