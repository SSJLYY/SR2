<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:page pageTitle="${pageTitle}">
    <form:form commandName="quotationConfirmFrom" method="post" enctype="multipart/form-data" action="/alpssalestorefront/alps/zh/quotation/confirm" class="import-csv__form">
        <div class="yCmsContentSlot landingLayout2PageSection2C">
            <div class="form-group">
                <label class="control-label">Optional Product Tota lPrice</label>
                <div>${quotation.optionalProductTotalPrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Decor Product Total Price</label>
                <div>${quotation.decorProductTotalPrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Insurance Product Total Price</label>
                <div>${quotation.insuranceProductTotalPrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Extended Warranty Total Price</label>
                <div>${quotation.extendedWarrantyTotalPrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Voucher Total Price</label>
                <div>${quotation.voucherTotalPrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">SecondHand Car Evaluation Of Price</label>
                <div>${quotation.secondHandCarEvaluationOfPrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Finance Service Charge</label>
                <div>${quotation.financeServiceCharge}</div>
            </div>
            <div class="form-group">
                <label class="control-label">License Plate Tax</label>
                <div>${quotation.licensePlateTax}</div>
            </div>
            <div class="form-group">
                <label class="control-label">License Plate Service Charge</label>
                <div>${quotation.licensePlateServiceCharge}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Service Price</label>
                <div>${quotation.servicePrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Other Price</label>
                <div>${quotation.otherPrice}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Total Price</label>
                <div>${quotation.totalPrice}</div>
            </div>
            <input id="quotationCode" name="quotationCode" class="form-control" type="hidden" value="${quotation.code}">
            <div class="form-group">
                <label class="control-label">license</label>
                <input type="file" name="signMedia" id="signMedia" class="file-loading" multiple="multiple">
            </div>
            <div class="form-group">
                <label class="control-label">Customer Confirm</label>
                <select id="customerConfirm" name="customerConfirm" class="form-control">
                    <option value="true">yes</option>
                    <option value="false">no</option>
                </select>
            </div>
            <div class="form-actions clearfix">
                <button type="submit" class="btn btn-default btn-block">submit</button>
            </div>
        </div>
    </form:form>
</template:page>
