<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="serviceorder" tagdir="/WEB-INF/tags/responsive/serviceorder"%>
<%@ taglib prefix="Spring" uri="http://www.springframework.org/tags" %>

<table class="table  pick_carTable2 pick_carTable_even table_open2">
	<tbody>
		<tr class="trOpen">
			<td></td>
			<td><spring:theme code="workhours.typesOfWork.label"/></td>
			<c:forEach var="item" items="${workhoursData}">
				<td>${item.typesOfWorkName!=null?item.typesOfWorkName : item.typesOfWork}</td>
			</c:forEach>
		</tr>
		<tr class="trOpen">
			<td></td>
			<td><spring:theme code="workhours.hours.label"/></td>
			<c:forEach var="item" items="${workhoursData}">
				<td>${item.hours}</td>
			</c:forEach>
		</tr>
	</tbody>
</table>
