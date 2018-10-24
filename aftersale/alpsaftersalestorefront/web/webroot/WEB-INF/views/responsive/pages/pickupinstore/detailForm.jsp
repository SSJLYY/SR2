<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="pickupdetails" class="vehicle2User-popup-box">
    <div class="reseration_inf">
        <p><spring:theme code="text.aftersales.pickup.details"/></p>
    </div>
    <div class="content-box">
        <c:forEach items="${recordList}" var="pickupInStore" varStatus="rows">

            <form:form id="details" action="/alpsaftersalestorefront/pickup/recordUpdate" method="post"
                       commandName="pickupInStoreData">
                <div class="col-md-12 col-sm-12 ls_nav">
                    <div class="row row_left">
                        <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                            <label class="word_spac" for="arrivalTime">
                                <spring:theme code="text.aftersales.pickup.list.time"/>:&nbsp;&nbsp;${pickupInStore.arrivalTime}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        </div>
                    </div>

                    <div class="row row_left">
                        <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                            <label for="vehicleNumber" class="word_spac"><spring:theme
                                    code="text.aftersales.pickup.list.carnumber"/>:</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="vehicleNumber" name="vehicleNumber" type="text"
                                   value="${fn:escapeXml(pickupInStore.vehicleNumber)}">
                        </div>
                    </div>

                    <div class="row row_left">
                        <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                            <label class="word_spac" for="sender"><spring:theme
                                    code="text.aftersales.pickup.list.sender"/>:</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="sender" name="sender" type="text" value="${fn:escapeXml(pickupInStore.sender)}">
                        </div>
                    </div>

                    <div class="row row_left">
                        <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                            <label class="word_spac" for="phoneNumber"><spring:theme
                                    code="text.aftersales.pickup.list.phonenumber"/>:</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="phoneNumber" name="phoneNumber" type="text"
                                   value="${fn:escapeXml(pickupInStore.phoneNumber)}">
                        </div>
                    </div>

                    <div class="row row_left">
                        <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                            <c:forEach items="${pickupinstoretask_status}" var="pickupInStoreTaskStatus">
                                <c:if test="${pickupInStoreTaskStatus.id==pickupInStore.status}">
                                    <label class="word_spac" >
                                        <spring:theme code="text.aftersales.pickup.list.status"/>:&nbsp;&nbsp;&nbsp;&nbsp;${pickupInStoreTaskStatus.label}
                                    </label>&nbsp;&nbsp;&nbsp;&nbsp;
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <input id="code" name="code" type="hidden" value="${fn:escapeXml(pickupInStore.code)}">

                    <input id="serviceConsultant" name="serviceConsultant" type="hidden" value="${fn:escapeXml(pickupInStore.serviceConsultant)}">


                    <div class="row form-group">
                        <div class="submityd">
                            <div class="text-center yd_formTop yd_form">

                                <div class="col-md-3 yd_flex form-group col-sm-4 pull-right" style="flex-direction:row-reverse">
                                    <button type="submit" class="btn btn-primary btn_save">
                                        <spring:theme code="text.aftersales.pickup.close"/>
                                    </button>
                                </div>

                                <c:url var="serviceOrderUrl" value="/service-order/form?pickupInStoreCode=${pickupInStore.code}"/>
                                <a href="${serviceOrderUrl}">
                                    <div class="col-md-3 yd_flex form-group col-sm-4 pull-right" style="flex-direction:row-reverse;width: 110px">
                                        <button type="button" class="btn btn-default btn_save">
                                            <spring:theme code="text.aftersales.pickup.serviceBilling"/>
                                        </button>
                                    </div>
                                </a>

                                <a id="reason">
                                    <div class="col-md-3 yd_flex form-group col-sm-4 pull-right" style="flex-direction:row-reverse">
                                        <button type="button" class="btn btn-default btn_save">
                                            <spring:theme code="text.aftersales.pickup.closetask"/>
                                        </button>
                                    </div>
                                </a>

                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </c:forEach>
    </div>

    <%--关闭原因表单--%>
    <div id='inputbox' class="closedReason-popup-box" style="display: none">
        <div class="reseration_inf">
            <p><spring:theme code="text.aftersales.pickup.closetask"/></p>
        </div>

        <div class="content-box">
            <c:forEach items="${recordList}" var="pickupInStore" varStatus="rows">

                <form:form id="reasons" action="/alpsaftersalestorefront/pickup/recordUpdate" method="post"
                           commandName="pickupInStoreData">
                <div class="col-md-12 col-sm-12">
                    <div class="row row_left">
                        <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                            <label class="word_spac" for="closedReason"><spring:theme
                                    code="text.aftersales.pickup.closereason"/>:</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="closedReason" name="closedReason" type="text" value=""/>
                        </div>
                    </div>

                <input id="code2" name="code" type="hidden" value="${fn:escapeXml(pickupInStore.code)}">

                <input id="serviceConsultant2" name="serviceConsultant" type="hidden" value="${fn:escapeXml(pickupInStore.serviceConsultant)}">

                    <div class="row form-group">
                        <div class="submityd">
                            <div class="text-center yd_formTop yd_form">
                                <button type="submit" class="btn btn-primary btn_save">
                                    <spring:theme code="text.aftersales.pickup.close"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                </form:form>
            </c:forEach>
        </div>
    </div>


</div>





