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
    <div class="table-responsive pick_car_list tab-box">
        <table class="table  pick_carTable2 pick_carTable_even">
            <thead>
            <tr>
                <th><spring:theme code="vehicle.detail.increase.tab.label.number" /></th>
                <th><spring:theme code="vehicle.detail.increase.tab.label.store" /></th>
                <th><spring:theme code="vehicle.detail.increase.tab.label.price" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${vehicleInfo.maintain}" var="maintain">
                <tr>
                    <td>${maintain.number}</td>
                    <td>${maintain.store}</td>
                    <td>${maintain.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
