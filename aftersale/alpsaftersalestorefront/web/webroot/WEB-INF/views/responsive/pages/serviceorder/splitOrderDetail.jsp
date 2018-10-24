<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="splitOrder" tagdir="/WEB-INF/tags/responsive/serviceorder/splitOrder"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>

<template:defaultPage pageTitle="${pageTitle}">
	<c:forEach items="${subOrderDataList}" var="subOrder">
		<div class="col-md-12 col-sm-12 ls_nav" style="margin-bottom:15px;">
			<div class=" ls_nav_nav ls_nav_bottom dismanting_click">
				<p><spring:theme code="split.tab.title" /></p>
			</div>
			<div class="row">
				<div class="" style="border-bottom:1px solid #ddd;padding-bottom:20px;color:#C71B1E;">
					<p style="padding-left:15px;">
						<span><spring:theme code="split.order.code"/>: </span><span>${subOrder.code}</span>
						<span style="padding-left:30px;"><spring:theme code="service.order.sub.type"/>: </span>
						<span>${subOrder.serviceSubType}</span>
						<span style="padding-left:30px;"><spring:theme code="service.order.buyer.label"/>: </span>
						<span>${subOrder.buyerName}</span>
					</p>
				</div>
				<div class="col-md-12 col-sm-12 pick_car dismanting_none" style="display: none;">
					<div class="table-responsive pick_car_list">
						<table class="table  pick_carTable2 pick_carTable_even">
							<thead>
							<tr>
								<th><spring:theme code="table.sequence" /></th>
								<th><spring:theme code="service.entry.type" /></th>
								<th><spring:theme code="entry.itemCategory" /></th>
								<th><spring:theme code="entry.product.name" /></th>
								<th><spring:theme code="entry.row.splitAmount" /></th>
							</tr>
							</thead>
							<tbody>
								<c:forEach var="entry" items="${subOrder.entries}" varStatus="status">
									<tr>
										<td>
											${status.index+1}
										</td>
										<td>
											${entry.serviceType}
										</td>
										<td>
											${entry.categoryName}
										</td>
										<td>
											${entry.name}
										</td>
										<td>
											${entry.actualPrice}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
</template:defaultPage>
