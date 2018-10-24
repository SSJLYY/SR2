<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<template:page pageTitle="${pageTitle}">
    <form:form commandName="followConfirmFrom" method="post" enctype="multipart/form-data" action="/alpssalestorefront/alps/zh/follow/confirm" class="import-csv__form">
        <div class="mui-content confirm">
            <p class="mui-clearfix">
                <span><spring:theme code="follow.customer.title"/></span>
                <span>xxxxxxxx</span>
            </p>
            <div class="undertake">
                <p class="title"><spring:theme code="follow.confrim.title"/> </p>
                <div class="matter">
                    <spring:theme code="follow.confrim.content"/>
                </div>
            </div>
            <div class="check"><input type="checkbox" />&nbsp;&nbsp;<span><spring:theme code="follow.confrim.btn.label"/></span></div>
            <br/>
            <div class="certificate">
                <p><spring:theme code="follow.image.input.label"/></p>
                <input type="file" name="license" id="license" class="file-loading" multiple="multiple">
            </div>
            <input id="followCode" name="followCode" class="form-control" type="hidden" value="${followOpportunity.code}">
            <input id="customerConfirm" name="customerConfirm" type="hidden" value="true"/>

            <div class="signature">
                <button type="submit" class="btn btn-default btn-block"><spring:theme code="follow.submit.btn.label"/></button>
            </div>
        </div>
    </form:form>
</template:page>
