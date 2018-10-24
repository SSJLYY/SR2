<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<div class="vehicleinsurance-popup-box hide">
    <div class="headline">
        <spring:theme code="register.new.vehicleinsurance" />
    </div>
    <div class="content-box">
        <form:form id="vehicle-insurance-form" method="post" commandName="insuranceData" action="${action}">
            <div class="modal-dialog-box">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                            <label class="row_label " for="register.vehicleInsurance.type"><spring:theme code='register.vehicleInsurance.type' /></label>
                            <select id="register.vehicleInsurance.type" class="form-control hell " name="type">
                                <option value="" disabled="disabled" selected="selected"></option>
                                <option value="<spring:theme code='register.vehicleInsurance.type.option1' />"><spring:theme code='register.vehicleInsurance.type.option1' /></option>
                                <option value="<spring:theme code='register.vehicleInsurance.type.option2' />"><spring:theme code='register.vehicleInsurance.type.option2' /></option>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                            <label class="row_label " for="register.vehicleInsurance.company"><spring:theme code='register.vehicleInsurance.company' /></label>
                            <select id="register.vehicleInsurance.company" class="form-control hell " name="company">
                                <option value="" disabled="disabled" selected="selected"></option>
                                <option value="<spring:theme code='register.vehicleInsurance.company.option1' />"><spring:theme code='register.vehicleInsurance.company.option1' /></option>
                                <option value="<spring:theme code='register.vehicleInsurance.company.option2' />"><spring:theme code='register.vehicleInsurance.company.option2' /></option>
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                            <formElement:formInputBox idKey="register.vehicleInsurance.number" labelKey="register.vehicleInsurance.number" path="number" labelCSS="row_label" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                            <formElement:formInputBox idKey="register.vehicleInsurance.startTime" labelKey="register.vehicleInsurance.startTime" path="startTime" labelCSS="row_label" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                            <formElement:formInputBox idKey="register.vehicleInsurance.endTime" labelKey="register.vehicleInsurance.endTime" path="endTime" labelCSS="row_label" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
                        </div>
                    </div>
                    <input type="hidden" name="vehicleCode" value="${vehicleInfo.code}"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default btn_save">
                        <spring:theme code='register.vehicle2user.close' />
                    </button>
                    <button type="submit" class="btn btn-primary btn_save">
                        <spring:theme code='${actionNameKey}' />
                    </button>
                </div>
            </div>
        </form:form>
    </div>
</div>
