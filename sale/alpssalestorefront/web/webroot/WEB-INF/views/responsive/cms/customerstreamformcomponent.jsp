<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form action="/alpssalestorefront/customer/steam/creates" method="post" commandName="customerFlowData">
    <div class="form-group">
        <label class="control-label " for="name">
            name
            <span class="mandatory"></span>
            <span class="skip"></span>
        </label>
        <input id="name" name="name" class="form-control form-control" type="text" value=""></div>
    </div>
    <div class="form-group">
        <label class="control-label " for="gender">gender</label>
        <select id="gender" name="gender" class="form-control">
            <option value="" disabled="disabled" selected="selected"></option>
            <c:forEach items="${genders}" var="genderItem">
                <option value="${genderItem.id}">${genderItem.label}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <label class="control-label " for="phone">phone</label>
        <input id="phone" name="phone" class="form-control form-control" type="text" value="">
    </div>
    <div class="form-group">
        <label class="control-label " for="inMan">inman</label>
        <input id="inMan" name="inMan" class="form-control form-control" type="text" value="">
    </div>
    <div class="form-group">
        <label class="control-label " for="inWoman">inwoman</label>
        <input id="inWoman" name="inWoman" class="form-control form-control" type="text" value="">
    </div>
    <div class="form-group">
        <label class="control-label " for="typeCode">type</label>
        <select id="typeCode" name="typeCode" class="form-control">
            <option value="" disabled="disabled" selected="selected"></option>
            <c:forEach items="${types}" var="type">
                <option value="${type.id}">${type.label}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <label class="control-label " for="statusCode">status</label>
        <select id="statusCode" name="statusCode" class="form-control">
            <option value="" disabled="disabled" selected="selected"></option>
            <c:forEach items="${status_list}" var="status">
                <option value="${status.id}">${status.label}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <label class="control-label " for="salesConsultantId">sales</label>
        <select id="salesConsultantId" name="salesConsultantId" class="form-control">
            <option value="" disabled="disabled" selected="selected"></option>
            <c:forEach items="${sales_consultant}" var="sales">
                <option value="${sales.customerId}">${sales.customerName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form_field-elements control-group js-recaptcha-captchaaddon"></div>
    <div class="form-actions clearfix">
        <button type="submit" class="btn btn-default btn-block">register</button>
    </div>
</form:form>
