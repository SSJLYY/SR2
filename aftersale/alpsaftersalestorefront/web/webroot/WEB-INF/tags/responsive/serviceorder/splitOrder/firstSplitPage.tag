<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="splitOrder" tagdir="/WEB-INF/tags/responsive/serviceorder/splitOrder"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>

<div class="split-order-box col-md-12 col-sm-12">
    <div class=" ls_nav_nav ls_nav_bottom">
        <p><spring:theme code='split.tab.title'/></p>
        <div class="pull-right">
            <button class="btn btn-primary btn_save split-btn" type="button"><spring:theme code='split.btn.label'/></button>
            <button class="btn btn-default preview-btn" type="button" style="margin-left:10px;"><spring:theme code='split.preview.btn.label'/></button>
        </div>
    </div>
    <splitOrder:splitMethod/>
    <div class="col-md-12 col-sm-12 ls_nav">
        <div class=" Ry_flex form-group" style="margin-top:20px;margin-left:15px;">
            <span class="ry_required">*</span><label for=""><spring:theme code="service.order.sub.type"/></label>
            <div class="sub-type-box" style="margin-left:40px;">
                <c:forEach items="${serviceSubType}" var="subtype">
                    <button class="but_objective" data-id="${subtype.id}" data-value="${subtype.label}">${subtype.label}</button>
                </c:forEach>
            </div>
        </div>
        <div class="Ry_flex form-group col-md-5 col-sm-5 call-search-customer-popup-box" data-source-name-box="#service-order-buyer-label" data-source-id-box="#service-order-buyer-id">
            <formElement:formInputBoxAttachIcon labelCSS="row_label" idKey="service-order-buyer-label" labelKey="service.order.buyer.label" path="serviceOrderData.sender.name" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
            <input type="hidden" value="${serviceOrderData.sender.uid}" id="service-order-buyer-id" name="serviceOrderData.sender.uid"/>
        </div>
    </div>
    <splitOrder:ableSplitList/>
</div>
