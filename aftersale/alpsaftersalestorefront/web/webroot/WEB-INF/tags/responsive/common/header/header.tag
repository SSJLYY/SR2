<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>


<div class="row border-bottom">
	<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
		<cms:pageSlot position="TopHeaderSlot" var="component" element="div">
			<cms:component component="${component}" />
		</cms:pageSlot>
		<%--<div class="navbar-header">
			<a class="navbar-minimalize minimalize-styl-2 btn btn-info " href="#"><i class="fa fa-bars"></i> </a>
		</div>--%>
		<div class="navbar-header">
			<form role="search" class="navbar-form-custom" method="post" action="search_results.html">
				<div class="form-group" style="position:relative;">
					<input type="text" placeholder="<spring:theme code="header.searchbox" />" class="form-control topp_search" name="top-search" id="top-search" style="padding-left:50px;"><img src="${themeResourcePath}/images/search.png" alt="" class="ry_search">
				</div>
			</form>
		</div>
		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown">
				<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
					<i><img src="${themeResourcePath}/images/xiaoxi.png" alt=""></i> <%--<span class="label label-primary"></span>--%>
				</a>
			</li>
			<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')" >
				<li class="dropdown">
					<a class="dropdown-toggle count-info" data-toggle="dropdown" href="<c:url value='/login'/>">
						<i><img src="${themeResourcePath}/images/logout.png" alt=""></i> <%--<span class="label label-primary"></span>--%>
					</a>
				</li>
			</sec:authorize>
			<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" >
				<li class="dropdown">
					<a class="dropdown-toggle count-info" href="<c:url value='/logout'/>">
						<i class="glyphicon glyphicon-off"></i> <span class="label label-primary"></span>
					</a>
				</li>
			</sec:authorize>
		</ul>
		<cms:pageSlot position="BottomHeaderSlot" var="component" element="div">
			<cms:component component="${component}" />
		</cms:pageSlot>
	</nav>
</div>
