<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="splitOrder" tagdir="/WEB-INF/tags/responsive/serviceorder/splitOrder"%>

<c:url value="/service-order/splitorder/process?code=${serviceOrderData.code}" var="actionurl"/>
<div class="preview-split-order-box hide">
    <form:form id="service-order-split-form" method="POST" commandName="splitServiceOrderRequest" action="${actionurl}">
        <div class="col-md-12 col-sm-12" style="margin-bottom:15px;">
            <div class="pull-right">
                <button class="btn btn-default btn_save back-split-btn" type="button"><spring:theme code='text.back.btn'/></button>
                <button class="btn btn-primary confirm-split-btn" type="submit" style="margin-left:10px;"><spring:theme code='split.confirm.btn.label'/></button>
            </div>
        </div>
        <div class="already-split-order">
            <c:forEach var="suborder" items="${subOrderDataList}" varStatus="status">
                <splitOrder:splitPreviewTemplate subOrder="${suborder}" sequence="${status.index}"/>
            </c:forEach>
        </div>
    </form:form>
</div>

