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
    <c:url value="/return-order/list" var="actionurl"/>
    <form:form id="service-order-search-form" method="get" commandName="searchIncrementOrderRequest" action="${actionurl}">
        <div class="col-md-3 col-sm-4 Ry_flex form-group">
            <formElement:formInputBox idKey="service-order-starttime" labelKey="search.service.order.starttime" path="startTime" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
        </div>
        <div class="col-md-3 col-sm-4 Ry_flex form-group">
            <formElement:formInputBox idKey="service-order-endTime" labelKey="search.service.order.endTime" path="endTime" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
        </div>
        <div class="col-md-3 col-sm-4 Ry_flex form-group">
            <formElement:formInputBox idKey="service-order-code" labelKey="service.order.code" path="code" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
        </div>
        <div class="col-md-3 col-sm-4 Ry_flex form-group">
            <formElement:formSelectBox idKey="service-order-status"
                                       labelKey="service.order.status"
                                       path="status"
                                       mandatory="true"
                                       skipBlank="false"
                                       skipBlankMessageKey=""
                                       items="${orderStatus}"
                                       itemValue="id"
                                       itemLabel="label"
                                       selectedValue=""
                                       notIncludeWrap="true"/>
        </div>
        <div class="col-md-3 col-sm-4 Ry_flex form-group">
            <formElement:formInputBox idKey="service-order-customer" labelKey="search.service.order.customer" path="customerName" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
        </div>
        <div class="col-md-3 col-sm-4 Ry_flex form-group">
            <formElement:formInputBox idKey="service-order-customerPhoneNumber" labelKey="search.service.order.customerPhoneNumber" path="customerMobileNumber" inputCSS="form-control" mandatory="true" notIncludeWrap="true"  />
        </div>


        <div class="col-md-3 yd_flex form-group col-sm-4 pull-right" style="flex-direction:row-reverse">
            <button class="btn btn-primary yd_submit" type="submit"><spring:theme code="search.box.search.btn" /></button>
        </div>
    </form:form>
</div>
