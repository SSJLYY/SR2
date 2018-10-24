<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="incrementorder" tagdir="/WEB-INF/tags/responsive/incrementorder"%>

<template:defaultPage pageTitle="${pageTitle}">
	<c:url value="/increment-order/createOrUpdate" var="action" />
	<div class="col-md-12 col-sm-12 ls_nav parent-page-block">
		<div class="reseration_inf">
			<p><spring:theme code="create.new.increment.order" /></p>
		</div>
		<form:form id="service-order-form" method="post" commandName="incrementOrderData" action="${action}">
			<div class="base-info">
				<incrementorder:baseForm />
			</div>
			<div class="product-info hide">
				<incrementorder:productForm />
			</div>
		</form:form>
	</div>
	<incrementorder:choseMultipleProductBlock/>
    <customer:choseCustomerPopup/>
	<vehicle:choseVehiclePopup/>
</template:defaultPage>

