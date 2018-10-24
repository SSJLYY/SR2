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
	<c:url value="/customer" var="actionurl"/>
	<form:form id="customer-search-form" method="get" commandName="customerSearchRequest" action="${actionurl}">
		<div class="col-md-3 col-sm-4 Ry_flex form-group">
			<formElement:formInputBox idKey="search.customer.name" labelKey="search.customer.name" path="name" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
		</div>
		<div class="col-md-3 col-sm-4 Ry_flex form-group">
			<formElement:formSelectBox idKey="search.customer.type"
									   labelKey="search.customer.type"
									   path="customerType"
									   mandatory="true"
									   skipBlank="false"
									   skipBlankMessageKey=""
									   items="${buyerType}"
									   itemValue="id"
									   itemLabel="label"
									   selectedValue=""
									   notIncludeWrap="true"/>
		</div>
		<div class="col-md-3 col-sm-4 Ry_flex form-group">
			<formElement:formSelectBox idKey="search.customer.role"
									   labelKey="search.customer.role"
									   path="role"
									   mandatory="true"
									   skipBlank="false"
									   skipBlankMessageKey=""
									   items="${customerRole}"
									   itemValue="id"
									   itemLabel="label"
									   selectedValue=""
									   notIncludeWrap="true"/>
		</div>
		<div class="col-md-3 col-sm-4 Ry_flex form-group">
			<formElement:formSelectBox idKey="search.customer.attribute"
									   labelKey="search.customer.attribute"
									   path="attribute"
									   mandatory="true"
									   skipBlank="false"
									   skipBlankMessageKey=""
									   items="${buyerCategory}"
									   itemValue="id"
									   itemLabel="label"
									   selectedValue=""
									   notIncludeWrap="true"/>
		</div>
		<div class="col-md-3 yd_flex form-group col-sm-4 pull-right" style="flex-direction:row-reverse">
			<button class="btn btn-primary yd_submit" type="submit"><spring:theme code="search.box.search.btn" /></button>
		</div>
	</form:form>
</div>
