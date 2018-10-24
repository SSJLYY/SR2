<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class="col-md-12 col-sm-12 ls_nav">
	<div class="reseration_inf">
		<p><spring:theme code="register.new.customer" /></p>
	</div>
	<script type="text/javascript">
		var regionList = ${regionList};
	</script>
	<form:form id="vehicleInfo-form" method="post" commandName="customerInfoData" action="${action}">
		<div class="row row_left">
			<div class=" Ry_flex form-group com_first col-md-12 col-sm-12 special-selection-box">
				<label for="" class="word_spac row_label" style="width:56px;">
					<spring:theme code="customer.type"/>
				</label>
				<div>
					<c:forEach var="item" items="${buyerType}">
						<input type="button" class="but_objective option" data-value="${item.id}" value="${item.label}">
					</c:forEach>
					<input type="hidden" value="" name="customerType"/>
				</div>
			</div>
		</div>

		<div class="row row_left">
			<div class=" Ry_flex form-group col-md-12 col-sm-12 special-selection-box">
				<label for="" class="word_spac row_label" style="width:56px;">
					<spring:theme code="customer.attribute"/>
				</label>
				<div>
					<c:forEach var="item" items="${buyerCategory}">
						<input type="button" class="but_objective option" data-value="${item.id}" value="${item.label!=null?item.label:item.id}">
					</c:forEach>
					<input type="hidden" value="" name="attribute"/>
				</div>
			</div>
		</div>

		<div class="row row_left">
			<div class=" Ry_flex form-group col-md-12 col-sm-12 special-selection-box">
				<label for="" class="word_spac row_label" style="width:56px;">
					<spring:theme code="customer.role"/>
				</label>
				<div>
					<c:forEach var="item" items="${customerRole}">
						<input type="button" class="but_objective option" data-value="${item.id}" value="${item.label}">
					</c:forEach>
					<input type="hidden" value="" name="role"/>
				</div>
			</div>
		</div>

		<div class="row row_left">
			<div class="col-md-4 col-sm-4 Ry_flex form-group col_p">
				<label for="" class="row_label2"><spring:theme code="register.customer.address"/></label>
				<select id="register-customer-province" name="provinceCode" class="form-control hell ">
					<option value="" disabled="disabled" selected="selected"><spring:theme code="customer.province.placeholder"/></option>
				</select>
			</div>
			<div class="col-md-3 col-sm-4 col-xs-3 Ry_flex form-group col_p">
				<select id="register-customer-city" name="cityCode" class="form-control hell ">
					<option value="" disabled="disabled" selected="selected"><spring:theme code="customer.city.placeholder"/></option>
				</select>
			</div>
			<div class="col-md-3 col-sm-4 col-xs-3 Ry_flex form-group col_p">
				<select id="register-customer-district" name="districtCode" class="form-control hell ">
					<option value="" disabled="disabled" selected="selected"><spring:theme code="customer.district.placeholder"/></option>
				</select>
			</div>
		</div>

		<formElement:formInputBox idKey="register.customer.name" labelKey="customer.name" path="name" inputCSS="form-control" labelCSS="row_label2" mandatory="true" placeholder="customer.name.placeholder"/>

		<formElement:formInputBox idKey="register.customer.mobileNumber" labelKey="customer.mobileNumber" path="mobileNumber" inputCSS="form-control" labelCSS="row_label2" mandatory="true" placeholder="customer.mobileNumber.placeholder"/>

		<formElement:formInputBox idKey="register.customer.otherContactNumber" labelKey="customer.otherContactNumber" path="otherContactNumber" inputCSS="form-control" labelCSS="row_label2" mandatory="true" placeholder="customer.otherContactNumber.placeholder"/>

		<div class="row row_left">
			<div class="col-md-4 col-sm-5 Ry_flex form-group col_p">
				<label for="" class="row_label2"><spring:theme code="customer.identityType"/></label>
				<select class="form-control " name="identityType">
					<option value="" disabled="disabled" selected="selected"><spring:theme code="customer.identityType.placeholder"/></option>
					<option value="<spring:theme code="customer.identityType.option1"/>"><spring:theme code="customer.identityType.option1"/></option>
					<option value="<spring:theme code="customer.identityType.option2"/>"><spring:theme code="customer.identityType.option2"/></option>
				</select>
			</div>
		</div>

		<formElement:formInputBox idKey="register.customer.identityNumber" labelKey="customer.identityNumber" path="identityNumber" inputCSS="form-control" labelCSS="row_label2" mandatory="true" placeholder="customer.identityNumber.placeholder"/>

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
