<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>

<div class="col-md-12 col-sm-12 ls_nav">
	<div class="reseration_inf">
		<p><spring:theme code="register.new.vehicle" /></p>
	</div>
	<script type="text/javascript">
        var categoryList = ${categoryList};
        var productList = ${productList};
	</script>
	<form:form id="vehicleInfo-form" method="post" commandName="vehicleInfoData" action="${action}">
		<formElement:formSelectBox idKey="register-vehicle-vehicleType"
								   labelKey="register.vehicle.vehicleType"
								   labelCSS="row_label"
								   path="vehicleType"
								   wrapCSS="com_first"
								   mandatory="true"
								   skipBlank="false"
								   skipBlankMessageKey=""
								   items="${vehicleType}"
								   itemValue="id"
								   itemLabel="label"
								   selectedValue=""/>

		<div class="row row_left">
			<div class="col-md-4 col-sm-5 Ry_flex form-group col_p call-search-customer-popup-box" data-source-name-box="#vehicle-customer-name" data-source-id-box="#vehicle-customer-id">
				<formElement:formInputBoxAttachIcon idKey="vehicle-customer-name" labelKey="register.vehicle.customerId" path="customerName" labelCSS="row_label" inputCSS="form-control" mandatory="true" notIncludeWrap="true"/>
				<input type="hidden" value="" id="vehicle-customer-id" name="customerId"/>
			</div>
		</div>

		<div class="row row_left">
			<div class="col-md-4 col-sm-5 Ry_flex form-group col_p call-search-customer-popup-box" data-source-name-box="#vehicle-agent-name" data-source-id-box="#vehicle-agent-id">
				<formElement:formInputBoxAttachIcon idKey="vehicle-agent-name" labelKey="register.vehicle.agentId" path="customerName" labelCSS="row_label" inputCSS="form-control" mandatory="true" notIncludeWrap="true"/>
				<input type="hidden" value="" id="vehicle-agent-id" name="agentId"/>
			</div>
		</div>

		<formElement:formInputBox idKey="register.vehicle.licensePlateNumber" labelKey="register.vehicle.licensePlateNumber" path="licensePlateNumber" labelCSS="row_label" inputCSS="form-control" mandatory="true" />

		<formElement:formInputBox idKey="register.vehicle.vinNumber" labelKey="register.vehicle.vinNumber" path="vinNumber" labelCSS="row_label" inputCSS="form-control" mandatory="true" />

		<formElement:formSelectBox idKey="register-vehicle-vehicleBrand"
								   labelKey="register.vehicle.vehicleBrand"
								   path="vehicleBrandCode"
								   mandatory="true"
								   labelCSS="row_label"
								   skipBlank="false"
								   skipBlankMessageKey=""
								   items="${brandList}"
								   itemValue="code"
								   itemLabel="name"
								   selectedValue=""/>
		<formElement:formSelectBox idKey="register-vehicle-vehicleCategory"
								   labelKey="register.vehicle.vehicleCategory"
								   path="vehicleCategoryCode"
								   labelCSS="row_label"
								   mandatory="true"
								   skipBlank="false"
								   skipBlankMessageKey=""
								   items="${emptyList}"
								   itemValue="code"
								   itemLabel="name"
								   selectedValue=""/>
		<formElement:formSelectBox idKey="register-vehicle-vehicle"
								   labelKey="register.vehicle.vehicle"
								   path="vehicleCode"
								   labelCSS="row_label"
								   mandatory="true"
								   skipBlank="false"
								   skipBlankMessageKey=""
								   items="${emptyList}"
								   itemValue="code"
								   itemLabel="name"
								   selectedValue=""/>
		<formElement:formSelectBox idKey="register-vehicle-color"
								   labelKey="register.vehicle.color"
								   path="color"
								   labelCSS="row_label"
								   mandatory="true"
								   skipBlank="false"
								   skipBlankMessageKey=""
								   items="${emptyList}"
								   itemValue="code"
								   itemLabel="name"
								   selectedValue=""/>

		<div class="row form-group">
			<div class="submityd">
				<div class="text-center yd_formTop yd_form">
					<ycommerce:testId code="register_Register_button">
						<button type="submit" class="btn btn-primary btn-save">
							<spring:theme code='${actionNameKey}' />
						</button>
					</ycommerce:testId>
				</div>
			</div>
		</div>
	</form:form>
</div>
<customer:choseCustomerPopup/>

