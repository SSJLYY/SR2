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
			<td><spring:theme code="stock.level.product.code.label"/></td>
			<c:forEach var="stock" items="${stocklevels}">
				<td>${stock.productCode}</td>
			</c:forEach>
		</tr>
		<tr class="trOpen">
			<td></td>
			<td><spring:theme code="stock.level.warehouse.label"/></td>
			<c:forEach var="stock" items="${stocklevels}">
				<td>${stock.warehouseName}</td>
			</c:forEach>
		</tr>
		<tr class="trOpen">
			<td></td>
			<td><spring:theme code="stock.level.quantity.label"/></td>
			<c:forEach var="stock" items="${stocklevels}">
				<td>${stock.stockLevel}</td>
			</c:forEach>
		</tr>
	</tbody>
</table>
