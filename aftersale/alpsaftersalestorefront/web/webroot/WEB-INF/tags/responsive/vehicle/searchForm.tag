<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class=" ls_nav_nav ls_nav_bottom">
	<p>
		<spring:theme code="search.box.header" />
	</p>
</div>
<div class="row">
	<c:url value="/vehicle/listData" var="actionurl"/>
	<form:form id="vheicle-search-form" method="get" commandName="searchVehicleRequest" action="${actionurl}">
		<div class="col-md-4 col-sm-4 Ry_flex form-group col_p">
			<formElement:formInputBox idKey="request-vehicle-licensePlateNumber" labelKey="register.vehicle.licensePlateNumber" path="vinCode" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
		</div>
		<div class="col-md-5 col-sm-5 Ry_flex form-group col_p">
			<formElement:formInputBox idKey="request-vehicle-vinNumber" labelKey="register.vehicle.vinNumber" path="vinCode" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
		</div>
		<div class="col-md-3 yd_flex form-group col-sm-4 pull-right search-btn" style="flex-direction:row-reverse">
			<button class="btn btn-primary yd_submit" type="button"><spring:theme code="search.box.search.btn" /></button>
		</div>
	</form:form>
</div>
