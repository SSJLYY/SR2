<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="breadcrumbs" required="true" type="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:url value="/" var="homeUrl" />
<p>
	<a href="${homeUrl}"><spring:theme code="breadcrumb.home" /></a>
	<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
		<c:url value="${breadcrumb.url}" var="breadcrumbUrl" />
		&nbsp;&gt;
		<c:choose>
			<c:when test="${status.last}">
				${fn:escapeXml(breadcrumb.name)}
			</c:when>
			<c:when test="${breadcrumb.url eq '#'}">
				<a href="#">${fn:escapeXml(breadcrumb.name)}</a>
			</c:when>
			<c:otherwise>
				<a href="${breadcrumbUrl}">${fn:escapeXml(breadcrumb.name)}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</p>
