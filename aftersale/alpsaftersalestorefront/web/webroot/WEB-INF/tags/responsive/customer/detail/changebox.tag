<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>

<div class="row row_left row_right changebox">
    <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
        <formElement:formInputBox idKey="register.customer.name" labelKey="customer.name" path="name" labelCSS="row_label2" inputCSS="form-control" mandatory="true" notIncludeWrap="true"/>
    </div>
    <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
        <formElement:formInputBox idKey="register.customer.mobileNumber" labelKey="customer.mobileNumber" path="mobileNumber" labelCSS="row_label2" inputCSS="form-control" mandatory="true" notIncludeWrap="true"/>
    </div>
    <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
        <label for=""><spring:theme code="customer.creator"/></label>
        <p>-</p>
    </div>
</div>
<div class="row row_left row_right changebox">
    <div class="col-md-4 col-sm-5 Ry_flex form-group col_p">
        <formElement:formSelectBox idKey="register-customer-type"
                                   labelKey="customer.type"
                                   path="customerTypeCode"
                                   labelCSS="row_label2"
                                   wrapCSS="com_first"
                                   mandatory="true"
                                   skipBlank="false"
                                   skipBlankMessageKey=""
                                   items="${buyerType}"
                                   itemValue="id"
                                   itemLabel="label"
                                   notIncludeWrap="true"/>
    </div>
    <div class="col-md-4 col-sm-5 Ry_flex form-group col_p">
        <formElement:formSelectBox idKey="register-customer-attribute"
                                   labelKey="customer.attribute"
                                   path="attributeCode"
                                   labelCSS="row_label2"
                                   mandatory="true"
                                   skipBlank="false"
                                   skipBlankMessageKey=""
                                   items="${buyerCategory}"
                                   itemValue="id"
                                   itemLabel="label"
                                   notIncludeWrap="true"/>
    </div>
    <div class="col-md-4 col-sm-5 Ry_flex form-group col_p">
        <formElement:formSelectBox idKey="register-customer-role"
                                   labelKey="customer.role"
                                   path="roleCode"
                                   labelCSS="row_label2"
                                   mandatory="true"
                                   skipBlank="false"
                                   skipBlankMessageKey=""
                                   items="${customerRole}"
                                   itemValue="id"
                                   itemLabel="label"
                                   notIncludeWrap="true"/>
    </div>
</div>
<div class="row row_left row_right changebox">
    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
        <formElement:formInputBox idKey="register.customer.identityType" labelKey="customer.identityType" path="identityType" inputCSS="form-control" labelCSS="row_label2" mandatory="true" notIncludeWrap="true"/>
    </div>
    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
        <formElement:formInputBox idKey="register.customer.identityNumber" labelKey="customer.identityNumber" path="identityNumber" inputCSS="form-control" labelCSS="row_label2" mandatory="true" notIncludeWrap="true"/>
    </div>
    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
        <formElement:formInputBox idKey="register.customer.otherContactNumber" labelKey="customer.otherContactNumber" path="otherContactNumber" labelCSS="row_label2" inputCSS="form-control" mandatory="true" notIncludeWrap="true"/>
    </div>
</div>
<div class="row row_left row_right changebox">
    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
        <label for="" class="word_spac"><spring:theme code="register.customer.address"/> </label>
        <select id="register-customer-province" name="provinceCode" class="form-control hell" data-value="${customerInfoData.provinceCode}"></select>
        <select id="register-customer-city" name="cityCode" class="form-control hell" data-value="${customerInfoData.cityCode}"></select>
        <select id="register-customer-district" name="districtCode" class="form-control hell" data-value="${customerInfoData.districtCode}"></select>
    </div>
</div>
