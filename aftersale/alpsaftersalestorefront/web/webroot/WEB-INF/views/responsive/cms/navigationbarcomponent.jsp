<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<c:set value="${component.styleClass} ${dropDownLayout}" var="bannerClasses"/>

<li class="${fn:escapeXml(bannerClasses)} nav__links--primary <c:if test="${not empty component.navigationNode.children}">nav__links--primary-has__sub js-enquire-has-sub</c:if>">
    <c:if test="${not empty component.navigationNode.children}"><a href="#"></c:if>
	<c:if test="${empty component.navigationNode.children}">
	<c:url var="url" value="${component.link.url}"/><a href="${component.link.external ? component.link.url : url}">
	</c:if>
		<i class="fa ${component.link.styleAttributes}"></i>
		<span class="nav-label">${component.link.linkName}</span>
		<c:if test="${not empty component.navigationNode.children}">
			<span class="fa arrow"></span>
		</c:if>
	</a>

	<c:if test="${not empty component.navigationNode.children}">
		<ul class="nav nav-second-level collapse ${hasTitleClass}">
		<c:forEach items="${component.navigationNode.children}" var="child">
		<c:if test="${child.visible}">
			<c:forEach items="${child.links}" step="${component.wrapAfter}" var="childlink" varStatus="i">
				<c:forEach items="${child.links}" var="childlink" begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
					<li>
						<c:url var="url" value="${childlink.url}"/>
						<a href="${childlink.external ? childlink.url : url}">
							${childlink.linkName}
						</a>
					</li>
				</c:forEach>
			</c:forEach>
		</c:if>
		</c:forEach>
		</ul>
	</c:if>
</li>
