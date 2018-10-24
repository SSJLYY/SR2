<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/responsive/template/cms" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>

<c:url value="/" var="siteRootUrl"/>

<template:javaScriptVariables/>

<c:choose>
	<c:when test="${wro4jEnabled}">
	  	<script type="text/javascript" src="${contextPath}/wro/all_responsive.js"></script>
	  	<script type="text/javascript" src="${contextPath}/wro/addons_responsive.js"></script>
	</c:when>
	<c:otherwise>
		<%-- jquery --%>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery-2.1.1.min.js"></script>

		<%-- bootstrap --%>
		<script type="text/javascript" src="${commonResourcePath}/bootstrap/dist/js/bootstrap.min.js"></script>

		<%-- plugins --%>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.metisMenu.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/layer.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/hAdmin.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/index.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/addproduct.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/customer.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/vehicle.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/task.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/serviceorder.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/pace.min.js"></script>

		<%-- Custom ACC JS --%>

		<!--<script type="text/javascript" src="${commonResourcePath}/js/acc.global.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/_autoload.js"></script>-->

		<%-- Cms Action JavaScript files --%>
		<c:forEach items="${cmsActionsJsFiles}" var="actionJsFile">
		    <script type="text/javascript" src="${commonResourcePath}/js/cms/${actionJsFile}"></script>
		</c:forEach>

		<%-- AddOn JavaScript files --%>
		<c:forEach items="${addOnJavaScriptPaths}" var="addOnJavaScript">
		    <script type="text/javascript" src="${addOnJavaScript}"></script>
		</c:forEach>

	</c:otherwise>
</c:choose>


<cms:previewJS cmsPageRequestContextData="${cmsPageRequestContextData}" />
