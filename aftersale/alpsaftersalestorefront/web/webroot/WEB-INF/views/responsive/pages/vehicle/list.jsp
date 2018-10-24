<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<template:defaultPage pageTitle="${pageTitle}">
	<div class="col-md-12 col-sm-12 ls_nav">
		<vehicle:listSearchForm/>
	</div>
	<c:url value="/vehicle/detail?code=" var="detailUrl" />
	<div class="col-md-12 col-sm-12 pick_car">
		<table class="table  pick_carTable2 pick_carTable_even">
			<thead>
			<tr>
				<th><spring:theme code="vehicle.list.table.sequence" /></th>
				<th><spring:theme code="vehicle.list.table.licensePlateNumber" /></th>
				<th><spring:theme code="vehicle.list.table.customer" /></th>
				<th><spring:theme code="vehicle.list.table.VINNumber" /></th>
				<th><spring:theme code="vehicle.list.table.brand" /></th>
				<th><spring:theme code="vehicle.list.table.vehicleCategory" /></th>
				<th><spring:theme code="vehicle.list.table.vehicle" /></th>
				<th><spring:theme code="vehicle.list.table.status" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${vehicleList}" var="vehicle">
				<tr>
					<td>${vehicle.sequenceNumber}</td>
					<td><a href="${detailUrl}${vehicle.code}">${vehicle.licensePlateNumber}</a></td>
					<td>${vehicle.customerName}</td>
					<td>${vehicle.vinNumber}</td>
					<td>${vehicle.vehicleBrand}</td>
					<td>${vehicle.vehicleCategory}</td>
					<td>${vehicle.vehicle}</td>
					<td>${vehicle.statusName}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</template:defaultPage>
