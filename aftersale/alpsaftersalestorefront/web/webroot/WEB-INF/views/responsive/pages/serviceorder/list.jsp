<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="serviceorder" tagdir="/WEB-INF/tags/responsive/serviceorder"%>

<template:defaultPage pageTitle="${pageTitle}">
	<div class="col-md-12 col-sm-12 ls_nav">
		<serviceorder:searchForm/>
	</div>
	<c:url value="/service-order/detail?code=" var="detailUrl" />
	<div class="col-md-12 col-sm-12 pick_car">
		<table class="table  pick_carTable2 pick_carTable_even">
			<thead>
			<tr>
				<th><spring:theme code="table.sequence" /></th>
				<th><spring:theme code="service.order.code" /></th>
				<th><spring:theme code="service.order.type" /></th>
				<th><spring:theme code="service.order.licensePlateNumber" /></th>
				<th><spring:theme code="service.order.sender" /></th>
				<th><spring:theme code="service.order.sender.phone" /></th>
				<th><spring:theme code="service.order.vinNumber" /></th>
				<th><spring:theme code="service.order.serviceConsultant" /></th>
				<th><spring:theme code="service.order.status" /></th>
				<th><spring:theme code="service.order.creationtime" /></th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${listResponse.orders}" var="order" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td><a href="${detailUrl}${order.code}">${order.code}</a></td>
						<td>${order.serviceType}</td>
						<td>${order.vehicle.licensePlateNumber}</td>
						<td>${order.sender.name}</td>
						<td>${order.sender.mobileNumber}</td>
						<td>${order.vehicle.vinNumber}</td>
						<td>${order.serviceConsultant.name}</td>
						<td>${order.status}</td>
						<td>${order.creationtime}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</template:defaultPage>
