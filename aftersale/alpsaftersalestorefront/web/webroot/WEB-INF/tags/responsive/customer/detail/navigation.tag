<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="choseli" required="true" type="java.lang.String"%>
<c:url var="baseUrl" value="/customer/detail?code=${customerCode}"/>
<c:url var="relatedVehicleUrl" value="/customer/related-vehicle/detail?code=${customerCode}"/>

<ul class="nav nav-tabs">
    <li <c:if test="${choseli == 'base-tab'}">class="active"</c:if>>
        <a href="${baseUrl}" style="font-size:14px;border-left:none;">
            <spring:theme code="detail.base.tab.name" /></a>
    </li>
    <li <c:if test="${choseli == 'vehicle-tab'}">class="active"</c:if>>
        <a href="${relatedVehicleUrl}" aria-expanded="false" style="font-size:14px;">
            <spring:theme code="vehicle.detail.tab.label" /></a>
    </li>
</ul>
