<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<template:defaultPage pageTitle="${pageTitle}">
	<div class="col-md-12 col-sm-12 ls_nav">
		<customer:searchForm/>
	</div>
	<c:url value="/customer/detail?code=" var="detailUrl" />
	<div class="col-md-12 col-sm-12 pick_car">
		<table class="table  pick_carTable2 pick_carTable_even">
			<thead>
			<tr>
				<th><spring:theme code="customer.list.sequence" /></th>
				<th><spring:theme code="customer.list.name" /></th>
				<th><spring:theme code="customer.list.phone" /></th>
				<th><spring:theme code="customer.list.role" /></th>
				<th><spring:theme code="customer.list.type" /></th>
				<th><spring:theme code="customer.list.status" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${customerList}" var="customer" varStatus="status">
				<tr>
					<td><a href="${detailUrl}${customer.uid}">${status.index+1}</a></td>
					<td><a href="${detailUrl}${customer.uid}">${customer.name}</a></td>
					<td><a href="${detailUrl}${customer.uid}">${customer.mobileNumber}</a></td>
					<td><a href="${detailUrl}${customer.uid}">${customer.role}</a></td>
					<td><a href="${detailUrl}${customer.uid}">${customer.customerType}</a></td>
					<td><a href="${detailUrl}${customer.uid}">${customer.status}</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</template:defaultPage>
