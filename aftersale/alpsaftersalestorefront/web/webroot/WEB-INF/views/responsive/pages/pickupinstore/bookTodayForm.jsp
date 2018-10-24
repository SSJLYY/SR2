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


<div class="wrapper wrapper-content animated fadeInRight wrapper_bottom">
<div class="row">
    <%--右中--%>
    <div class="col-sm-12 col-md-12">
        <div class="ls_title">
            <p><spring:theme code="text.aftersales.title.booktoday" /></p>
        </div>
    </div>

    <div class="col-md-12 col-sm-12">
        <div class="col-md-12 col-sm-12 ls_nav">
            <div class="reseration_inf">
                <p>${now}</p>
            </div>

               <%-- <div style="width:10%;float: left;color: orange" >
                    <button style="color: orange" class="todayBtn"></button>
                     completed
                </div>
                <div style="width:10%;float: left; color: red" >
                    <button style="color: red" class="todayBtn"></button>
                    overtime
                </div>
                <div style="width:10%;float: left; color: green ">
                    <button style="color: green" class="todayBtn"></button>
                    working
                </div>--%>


                <table class="table table-hover2 pick_carTable">
                    <thead>
                        <tr class="blackborder">
                                <th class="blackborder"><spring:theme code="text.aftersales.pickup.list.time" /></th>
                                <th class="blackborder"><spring:theme code="text.aftersales.pickup.list.sender" /></th>
                                <th class="blackborder"><spring:theme code="text.aftersales.pickup.list.carnumber" /></th>
                                <th class="blackborder"><spring:theme code="text.aftersales.pickup.list.phonenumber" /></th>
                        </tr>
                    </thead>

                    <div style="float: left;">
                        <c:forEach  begin="9" end="20" step="1" var="i">
                            <c:set var="display" value="true"/>
                                <tbody>
                                    <c:forEach items="${pickupList}" var="pickupInStore" varStatus="rows">
                                        <c:if test="${pickupInStore.arrivalTime <i+1}">
                                            <c:if test="${pickupInStore.arrivalTime >=i}">
                                                <tr>
                                                    <td>
                                                        <c:if test="${display}">
                                                            ${i}:00-${i+1}:00
                                                        </c:if>
                                                    </td>
                                                    <c:set var="display" value="false"/>
                                                    <td  class="blackborder">${fn:escapeXml(pickupInStore.sender)}&nbsp;</td>
                                                    <td class="blackborder">${fn:escapeXml(pickupInStore.vehicleNumber)}&nbsp;</td>
                                                    <td class="blackborder">${fn:escapeXml(pickupInStore.phoneNumber)}&nbsp;</td>
                                                </tr>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                        </c:forEach>
                    </div>

                </table>
        </div>
    </div>
    <%--右中end--%>
</div>
</div>


</template:defaultPage>
