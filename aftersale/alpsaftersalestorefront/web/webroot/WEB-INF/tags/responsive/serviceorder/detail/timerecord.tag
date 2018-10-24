<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<c:url var="action" value="/vehicle/createOrUpdate"/>
<div class="row  ls_nav">
    <div class="ls_nav_nav2  ls_nav_left" style="border-bottom:1px solid #ddd;padding-bottom:20px;">
        <p style="padding-left:15px;">
            <span><spring:theme code="detail.service.order.code" /></span><span>  ${serviceOrderData.code}</span>
            <span><spring:theme code="detail.service.order.type" /></span><span>  ${serviceOrderData.serviceType}</span>
            <input type="hidden" value="${serviceOrderData.code}" name="code"/>
        </p>
    </div>
</div>
<div class="row ls_nav">
    <form:form id="service-order-update" method="post" commandName="serviceOrderData" action="${action}">
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                <label class="word_spac"><spring:theme code="detail.service.order.creationtime" /> ${serviceOrderData.creationtime}</label>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.service.order.estimated.deliveryTime" /> ${serviceOrderData.estimatedDeliveryTime}</label>
            </div>

        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.service.order.actual.deliveryTime" /> ${serviceOrderData.actualDeliveryTime}</label>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.service.order.dispatch.worksTime" /> ${serviceOrderData.dispatchWorksTime}</label>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.service.order.completedTime" /> ${serviceOrderData.completedTime}</label>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.service.order.checkoutTime" /> ${serviceOrderData.checkoutTime}</label>
            </div>
        </div>
    </form:form>
</div>
