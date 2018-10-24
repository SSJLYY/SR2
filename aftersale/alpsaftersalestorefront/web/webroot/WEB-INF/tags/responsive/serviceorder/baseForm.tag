<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>

<div class="row row_left">
	<div class=" Ry_flex form-group com_first special-selection-box">
		<label class="word_spac"><spring:theme code='service.order.type' /></label>
		<c:forEach var="ordertype" items="${serviceOrderTypeList}">
			<div class="but_objective option" data-value="${ordertype.id}">${ordertype.label}</div>
		</c:forEach>
		<input type="hidden" value="" id="service-order-type" name="serviceTypeCode"/>
	</div>
	<input type="hidden" value="${pickupInStoreCode}" name="pickupInStoreCode">
</div>

<div class="row row_left">
	<div class="col-md-4 col-sm-5 Ry_flex form-group col_p call-search-vehicle-popup-box" data-source-name-box="#service-order-vehicle" data-source-id-box="#service-order-vehicle-code">
		<formElement:formInputBoxAttachIcon labelCSS="row_label" idKey="service-order-vehicle" labelKey="service.order.vehicle.code" path="vehicle.licensePlateNumber" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
		<c:url value="/vehicle/form" var="vehicleFormUrl"/>
		<input type="hidden" value="" id="service-order-vehicle-code" name="vehicle.code"/>
	</div>
	<p>&nbsp;<spring:theme code='not.have.vehicle.data' /><a href="${vehicleFormUrl}"><spring:theme code='create.new.vheicle.link' /></a></p>
</div>

<div class="row row_left">
	<div class="col-md-4 col-sm-5 Ry_flex form-group col_p call-search-customer-popup-box" data-source-name-box="#service-order-sender" data-source-id-box="#service-order-sender-id">
		<formElement:formInputBoxAttachIcon labelCSS="row_label" idKey="service-order-sender" labelKey="service.order.sender.uid" path="sender.name" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
		<input type="hidden" value="" id="service-order-sender-id" name="sender.uid"/>
	</div>
</div>

<formElement:formInputBox labelCSS="row_label" idKey="service-order-mileage" labelKey="service.order.mileage.infactory" path="mileageInFactory" inputCSS="form-control" mandatory="true" placeholder="0"/>

<formElement:formInputBox labelCSS="row_label" idKey="service-es-delivery-time" labelKey="service.order.es.delivery.time" path="estimatedDeliveryTime" inputCSS="form-control" mandatory="true" placeholder="2018-09-20"/>

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

