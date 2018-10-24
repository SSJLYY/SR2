<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:page pageTitle="${pageTitle}">
    <form:form commandName="followConfirmFrom" method="post" enctype="multipart/form-data" action="/alpssalestorefront/alps/zh/follow/confirm" class="import-csv__form">
        <div class="yCmsContentSlot landingLayout2PageSection2C">
            <div class="form-group">
                <label class="control-label">Follow Type</label>
                <div>${followOpportunity.type}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Sales Consultant Name</label>
                <div>${followOpportunity.salesConsultantName}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Creation Time</label>
                <div>${followOpportunity.creationtime}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Market Activity Name</label>
                <div>${followOpportunity.marketActivityName}</div>
            </div>
            <div class="form-group">
                <label class="control-label">Content</label>
                <c:forEach items="${followOpportunity.contentList}" var="content">
                    <div>${content.creationtime} -- ${content.content}</div>
                </c:forEach>
            </div>
            <input id="followCode" name="followCode" class="form-control" type="hidden" value="${followOpportunity.code}">
            <div class="form-group">
                <label class="control-label">license</label>
                <input type="file" name="license" id="license" class="file-loading" multiple="multiple">
            </div>
            <div class="form-group">
                <label class="control-label">Customer Name</label>
                <input id="customerName" name="customerName" class="form-control" type="text" value="">
            </div>
            <div class="form-group">
                <label class="control-label">License Number</label>
                <input id="licenseNumber" name="licenseNumber" class="form-control" type="text" value="">
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
