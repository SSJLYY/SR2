<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="serviceorder" tagdir="/WEB-INF/tags/responsive/serviceorder"%>

<template:defaultPage pageTitle="${pageTitle}">
	<c:url value="/service-order/createOrUpdate" var="action" />
	<div class="col-md-12 col-sm-12 ls_nav parent-page-block">
		<div class="reseration_inf">
			<p><spring:theme code="create.new.service.order" /></p>
		</div>
		<form:form id="service-order-form" method="post" commandName="serviceOrderData" action="${action}">
			<div class="base-info">
				<serviceorder:baseForm />
			</div>
			<div class="product-info hide">
				<serviceorder:productForm />
			</div>
		</form:form>
	</div>
	<serviceorder:choseMultipleProductBlock/>
    <customer:choseCustomerPopup/>
	<vehicle:choseVehiclePopup/>
</template:defaultPage>

