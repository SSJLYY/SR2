<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>


<template:defaultPage pageTitle="${pageTitle}">


    <%--右侧--%>
    <div class="wrapper wrapper-content animated fadeInRight wrapper_bottom">
        <div class="row">
            <div class="col-md-12 col-sm-12">
                <div class="col-md-12 col-sm-12 ls_nav">
                    <div class=" ls_nav_nav ls_nav_bottom">
                        <p><spring:theme code="text.aftersales.title.queryconditions" /></p>
                        <%--今日预约看板--%>
                        <form:form id="bookToday" action="/alpsaftersalestorefront/pickup/bookToday" method="post" commandName="pickupInStoreListRequest">
                            <div  style="display: none;" >
                                <input id="isToday" name="isToday" class="form-control form-control" type="text" value="true">
                            </div>
                            <p>
                                <a onclick="document:bookToday.submit()"><spring:theme code="text.aftersales.title.booktoday" /></a>
                            </p>
                        </form:form>
                    </div>


                    <%--搜素表单--%>
                    <div class="row">
                        <form:form action="/alpsaftersalestorefront/pickup/list" method="post" commandName="pickupInStoreListRequest">
                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <label  for="sender"><spring:theme code="text.aftersales.pickup.list.customername" /></label>
                                <input id="sender" name="sender" type="text" placeholder=<spring:theme code="text.aftersales.searchbox.sender" /> value="${sender}">
                            </div>


                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <label for="vehicleNumber"><spring:theme code="text.aftersales.pickup.list.carnumber" /></label>
                                <input id="vehicleNumber" name="vehicleNumber"  type="text" placeholder=<spring:theme code="text.aftersales.searchbox.carnumber" /> value="${vehicleNumber}">
                            </div>

                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <label  for="status"><spring:theme code="text.aftersales.pickup.list.status" /></label>
                                <select id="status" name="status" class="form-control hell">
                                    <option value="" disabled="disabled" selected="selected"><spring:theme code="text.aftersales.pickup.list.all" /></option>
                                   <c:url value="${status_task}" var="taskstatus"></c:url>
                                        <c:forEach items="${pickupinstoretask_status}" var="pickupInStoreTaskStatu">
                                                <option  value="${pickupInStoreTaskStatu.id}" >${pickupInStoreTaskStatu.label}</option>
                                            <c:if test="${taskstatus eq pickupInStoreTaskStatu.id}">
                                                <option style="display: none" selected="selected" value="${pickupInStoreTaskStatu.id}" >${pickupInStoreTaskStatu.label}</option>
                                            </c:if>
                                        </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <label for="workOrderStatus"><spring:theme code="text.aftersales.pickup.list.orderstatus" /></label>
                                <select id="workOrderStatus" name="workOrderStatus" class="form-control hell">
                                    <option value="" disabled="disabled" selected="selected"><spring:theme code="text.aftersales.pickup.list.all" /></option>
                                    <c:url value="${workOrderStatus}" var="workOrderStatus"></c:url>
                                        <c:forEach items="${order_status}" var="status">
                                            <option value="${status.id}">${status.label}</option>
                                            <c:if test="${workOrderStatus eq status.id}">
                                                <option style="display: none" selected="selected" value="${status.id}">${status.label}</option>
                                            </c:if>
                                        </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3 yd_flex form-group col-sm-4 pull-right" style="flex-direction:row-reverse">
                                <button type="submit" class="btn btn-primary yd_submit" ><spring:theme code="search.button" /></button>
                            </div>
                        </form:form>

                    </div>
                </div>
                <%--右上end--%>

                <%--右表格--%>
                <div class="col-md-12 col-sm-12 pick_car">
                    <div class="table-responsive pick_car_list">
                        <table class="table  pick_carTable pick_carTable_even">
                            <thead>
                            <tr>
                                <th><spring:theme code="text.aftersales.title.sequence" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.taskcode" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.time" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.carnumber" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.sender" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.phonenumber" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.isreserve" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.status" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.ordernumber" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.orderstatus" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.serviceconsulat" /></th>
                                <th><spring:theme code="text.aftersales.pickup.list.closedreason" /></th>
                            </tr>
                            </thead>

                            <c:forEach items="${pickupList}" var="pickupInStore" varStatus="rows">
                                <spring:url value="recordDetail" var="viewPickupInStoreUrl" htmlEscape="false">
                                    <spring:param name="code" value="${pickupInStore.code}"/>
                                </spring:url>
                                <div id="id-${rows.index}" class="pull-left">
                                    <tbody>
                                    <tr>
                                        <td>${rows.index+1}&nbsp;</td>
                                        <td>
                                            <a popup-url="${viewPickupInStoreUrl}">
                                                    ${fn:escapeXml(pickupInStore.code)}
                                            </a>
                                        </td>
                                        <td>${fn:escapeXml(pickupInStore.arrivalTime)}&nbsp;</td>
                                        <td>${fn:escapeXml(pickupInStore.vehicleNumber)}&nbsp;</td>
                                        <td>${fn:escapeXml(pickupInStore.sender)}&nbsp;</td>
                                        <td>${fn:escapeXml(pickupInStore.phoneNumber)}&nbsp;</td>

                                        <c:if test="${pickupInStore.reserve==false}">
                                            <td><spring:theme code="text.aftersales.pickup.list.isreserve.false" />&nbsp;</td>
                                        </c:if>
                                        <c:if test="${pickupInStore.reserve==true}">
                                            <td><spring:theme code="text.aftersales.pickup.list.isreserve.true" />&nbsp;</td>
                                        </c:if>

                                        <td> ${pickupInStore.status}</td>
                                        <c:url value="/service-order/detail?code=${fn:escapeXml(pickupInStore.workOrderNumber)}" var="serviceOrderUrl"/>
                                        <td><a href="${serviceOrderUrl}">${fn:escapeXml(pickupInStore.workOrderNumber)}</a></td>


                                        <td>${fn:escapeXml(pickupInStore.workOrderStatus)}&nbsp;</td>

                                        <td>${fn:escapeXml(pickupInStore.serviceConsultant)}&nbsp;</td>
                                        <td>${fn:escapeXml(pickupInStore.closedReason)}&nbsp;</td>
                                    </tr>
                                    </tbody>
                                </div>

                            </c:forEach>
                        </table>
                    </div>
                </div>
                    <%--右中end--%>
            </div>
        </div>
    </div>
</template:defaultPage>
