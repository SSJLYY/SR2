<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="detail" tagdir="/WEB-INF/tags/responsive/customer/detail"%>

<template:defaultPage pageTitle="${pageTitle}">
	<div class="col-md-12 col-sm-12">
		<div class="row">
			<div class="tabs-container">
				<detail:navigation choseli="vehicle-tab" />
				<div class="col-md-12 col-sm-12">
					<div class="tab-content">
						<div class="row ls_nav">
							<div class=" ls_nav_nav ls_nav_bottom">
								<p><spring:theme code='customer.related.vehicle'/></p>
								<c:url var="createVehicleLink" value="/vehicle/form"/>
								<p><a href="${createVehicleLinkbasket.save.cart.action.cancel}"><span style="border:none;color:#C71B1E;margin-right:10px;">+</span><spring:theme code="create.vehicle.btn"/></a></p>
							</div>
							<div class="table-responsive pick_car_list">
								<table class="table  pick_carTable2 pick_carTable_even">
									<thead>
									<tr>
										<th><spring:theme code="vehicle.list.table.sequence" /></th>
										<th><spring:theme code="vehicle.list.table.customer" /></th>
										<th><spring:theme code="vehicle.list.table.licensePlateNumber" /></th>
										<th><spring:theme code="vehicle.list.table.VINNumber" /></th>
										<th><spring:theme code="vehicle.list.table.brand" /></th>
										<th><spring:theme code="vehicle.list.table.vehicleCategory" /></th>
										<th><spring:theme code="vehicle.list.table.vehicle" /></th>
										<th><spring:theme code="vehicle.list.table.status" /></th>
									</tr>
									</thead>
									<tbody>
									<c:forEach items="${vehicleListResponse.vehicleList}" var="vehicle">
										<tr>
											<td>${vehicle.sequenceNumber}</td>
											<td><a href="${detailUrl}${vehicle.code}">${vehicle.customerName}</a></td>
											<td>${vehicle.licensePlateNumber}</td>
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
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template:defaultPage>
