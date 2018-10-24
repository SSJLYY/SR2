<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>




<template:defaultPage pageTitle="${pageTitle}">

    <div class="col-md-12 col-sm-12">
    <%--功能选择--%>
    <div class="ls_nav">
        <div class="ls_nav_nav">
            <p><spring:theme code="text.aftersales.title.commonfunctions" /></p>
            <p class="ry_setUp showbox yd_change"><a href="#"><spring:theme code="text.aftersales.title.setting" /></a></p>
            <p class="ry_setUp changebox yd_cancle"><a href="#"><spring:theme code="cancel.btn" /></a></p>
        </div>
        <div class="ls_fun Ry_flex showbox">
            <c:forEach var="item" items="${customNavList.customerNavList}">
                <c:if test="${item.display}">
                    <div align="center" class="ry_icon">
                        <c:url var="linkurl" value="${item.linkUrl}"/>
                        <a href="${linkurl}">
                            <img src="${item.image}" alt="">
                            <p style="color: rgb(51, 51, 51);">${item.linkName}</p>
                        </a>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <div class="ls_fun2 changebox" style="padding-bottom: 20px;">
            <c:url var="action" value="/customnav"/>
            <form:form id="service-order-update" method="post" action="${action}">
                <div class="Ry_flex input_but" style="margin:30px 40px;flex-wrap:wrap;">
                    <c:forEach var="item" items="${customNavList.defaultNavList}">
                        <div class="nav_button">
                            <input type="button" class="but_objective2 customnav-btn" value="${item.linkName}">
                            <img src="${themeResourcePath}/images/duoxuan2.png" class="duanxuan_btn" alt="">
                            <input class="customnav-value-box" type="checkbox" name="customnavcode" value="${item.code}" style="display: none">
                        </div>
                    </c:forEach>
                </div>
                <div class="text-right yd_formTop yd_form">
                    <button class="btn btn-primary btn_save renwu_save" type="submit">
                        <spring:theme code="detail.base.tab.label.save"/>
                    </button>
                </div>
            </form:form>
        </div>
    </div>

    <%--接车列表--%>
    <div class="pick_car">
        <div class="ls_nav_nav">
            <p><spring:theme code="text.aftersales.pickup.pickupinstoreform" /></p>
            <p><a href="/alpsaftersalestorefront/pickup/list">MORE</a></p>
        </div>
            <%--表格内容--%>
        <div class="table-responsive pick_car_list">
            <table class="table table-hover2 pick_carTable">
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
                    <th><spring:theme code="text.aftersales.pickup.list.vehicleStatus" /></th>
                </tr>
                </thead>

                <c:forEach items="${pickupList}" var="pickupInStore" varStatus="rows">

                    <c:url value="/pickup/recordDetail?code=${pickupInStore.code}" var="viewPickupInStoreUrl"/>

                    <div id="id-${rows.index}" class="pull-left">
                        <tbody>
                        <tr>
                            <td>${rows.index+1}&nbsp;</td>
                            <td>
                                <a popup-url="${viewPickupInStoreUrl}" >
                                        ${fn:escapeXml(pickupInStore.code)}
                                </a>
                            </td>
                            <td>${fn:escapeXml(pickupInStore.arrivalTime)}&nbsp;</td>
                            <td>${fn:escapeXml(pickupInStore.vehicleNumber)}&nbsp;</td>
                            <td>${fn:escapeXml(pickupInStore.sender)}&nbsp;</td>
                            <td>${fn:escapeXml(pickupInStore.phoneNumber)}&nbsp;</td>

                            <c:if test="${pickupInStore.reserve==false}">
                                <td><spring:theme code="text.no.button.label" />&nbsp;</td>
                            </c:if>
                            <c:if test="${pickupInStore.reserve==true}">
                                <td><spring:theme code="text.yes.button.label" />&nbsp;</td>
                            </c:if>

                            <c:forEach items="${pickupinstoretask_status}" var="pickupInStoreTaskStatus">
                                <c:if test="${pickupInStoreTaskStatus.id==pickupInStore.status}">
                                    <td> ${pickupInStoreTaskStatus.label}&nbsp;</td>
                                </c:if>
                            </c:forEach>

                            <td>${fn:escapeXml(pickupInStore.workOrderNumber)}&nbsp;</td>

                            <c:forEach items="${vehicle_status}" var="vehicleStatus">
                                <c:if test="${vehicleStatus.id==pickupInStore.vehicleStatus}">
                                    <td> ${vehicleStatus.label}&nbsp;</td>
                                </c:if>
                            </c:forEach>

                        </tr>
                        </tbody>
                    </div>
                </c:forEach>
            </table>
        </div>
    </div>
    <%--接车列表结束--%>
    </div>

</template:defaultPage>
