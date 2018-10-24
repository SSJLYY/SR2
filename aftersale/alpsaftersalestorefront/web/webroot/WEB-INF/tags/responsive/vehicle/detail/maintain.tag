<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="row ls_nav">
    <div class="ls_nav_nav2  ls_nav_left" style="border-bottom:1px solid #ddd;padding-bottom:20px;">
        <p style="padding-left:15px;">
            <span><spring:theme code='vehicle.detail.base.tab.label.vehicle.id'/></span><span>  ${vehicleInfo.code}</span>
        </p>
    </div>
</div>
<div class="row ls_nav">
    <div class="table-responsive pick_car_list">
        <table class="table  pick_carTable2 pick_carTable_even">
            <thead>
            <tr>
                <th><spring:theme code="vehicle.detail.maintain.tab.label.number" /></th>
                <th><spring:theme code="vehicle.detail.maintain.tab.label.start.date" /></th>
                <th><spring:theme code="vehicle.detail.maintain.tab.label.end.date" /></th>
                <th><spring:theme code="vehicle.detail.maintain.tab.label.store" /></th>
                <th><spring:theme code="vehicle.detail.maintain.tab.label.contactName" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${vehicleInfo.maintain}" var="maintain">
                <tr>
                    <td>${maintain.number}</td>
                    <td>${maintain.startTime}</td>
                    <td>${maintain.endTime}</td>
                    <td>${maintain.store}</td>
                    <td>${maintain.contactName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
