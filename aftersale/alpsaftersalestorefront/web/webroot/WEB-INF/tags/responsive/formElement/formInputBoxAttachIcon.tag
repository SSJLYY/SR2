<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="idKey" required="true" type="java.lang.String"%>
<%@ attribute name="labelKey" required="true" type="java.lang.String"%>
<%@ attribute name="path" required="true" type="java.lang.String"%>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean"%>
<%@ attribute name="labelCSS" required="false" type="java.lang.String"%>
<%@ attribute name="inputCSS" required="false" type="java.lang.String"%>
<%@ attribute name="placeholder" required="false" type="java.lang.String"%>
<%@ attribute name="tabindex" required="false" rtexprvalue="true"%>
<%@ attribute name="autocomplete" required="false" type="java.lang.String"%>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean"%>
<%@ attribute name="maxlength" required="false" type="java.lang.Integer"%>
<%@ attribute name="notIncludeWrap" required="false" rtexprvalue="true"%>
<%@ attribute name="wrapCSS" required="false" rtexprvalue="true"%>
<%@ attribute name="showbox" required="false" rtexprvalue="true"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<template:errorSpanField path="${path}" notIncludeWrap="${notIncludeWrap}" wrapCSS="${wrapCSS}">
	<ycommerce:testId code="LoginPage_Item_${idKey}">
		<c:if test="${required}">
			<span class="ry_required">*</span>
		</c:if>
		<label class="word_spac ${labelCSS}" for="${idKey}">
			<spring:theme code="${labelKey}" />
			<c:if test="${mandatory != null && mandatory == false}">
				<span>&nbsp;<spring:theme code="login.optional" /></span>
			</c:if>
		</label>
		<spring:theme code="${placeholder}" var="placeHolderMessage" />
		<form:input cssClass="${inputCSS} attach-icon" id="${idKey}" path="${path}"
					tabindex="${tabindex}" autocomplete="off" placeholder="${placeHolderMessage}"
					disabled="${disabled}" maxlength="${maxlength}"/>
		<img src="${themeResourcePath}/images/search.png" alt="" class="ry_search3 attach-icon">
	</ycommerce:testId>
</template:errorSpanField>
