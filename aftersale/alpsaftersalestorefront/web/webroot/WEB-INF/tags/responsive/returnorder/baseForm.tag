<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>

<div class="row row_left">
	<div class="col-md-4 col-sm-5 Ry_flex com_first form-group col_p call-search-vehicle-popup-box" data-source-name-box="#increment-order-vehicle" data-source-id-box="#increment-order-vehicle-code">
		<formElement:formInputBoxAttachIcon labelCSS="row_label" idKey="increment-order-vehicle" labelKey="service.order.vehicle.code" path="vehicle.licensePlateNumber" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
		<c:url value="/vehicle/form" var="vehicleFormUrl"/>
		<input type="hidden" value="" id="increment-order-vehicle-code" name="vehicle.code"/>
	</div>
	<p class="com_first">&nbsp;<spring:theme code='not.have.vehicle.data' /><a href="${vehicleFormUrl}"><spring:theme code='create.new.vheicle.link' /></a></p>
</div>

<div class="row row_left">
	<div class="col-md-4 col-sm-5 Ry_flex form-group col_p call-search-customer-popup-box" data-source-name-box="#increment-order-customer" data-source-id-box="#increment-order-customer-id">
		<formElement:formInputBoxAttachIcon labelCSS="row_label" idKey="increment-order-customer" labelKey="increment.order.customer.uid" path="customer.name" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
		<input type="hidden" value="" id="increment-order-customer-id" name="customer.uid"/>
	</div>
</div>

<div class="row form-group">
	<div class="submityd">
		<div class="text-center yd_formTop yd_form">
			<button class="btn btn-default btn_save" type="button"><spring:theme code="cancel.btn" />
			</button>
			<button class="btn btn-primary btn_save next-step" type="button"><spring:theme code="next.btn" />
			</button>
		</div>
	</div>
</div>

