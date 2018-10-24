<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="detail" tagdir="/WEB-INF/tags/responsive/vehicle/detail"%>
<c:url value="/vehicle/user/delete" var="deleteUrl"/>
<div class="row ls_nav">
    <div class="ls_nav_nav2  ls_nav_left" style="border-bottom:1px solid #ddd;padding-bottom:20px;">
        <p style="padding-left:15px;">
            <span><spring:theme code='vehicle.detail.base.tab.label.vehicle.id'/></span><span>  ${vehicleInfo.code}</span>
        </p>
    </div>
    <div class="ls_nav">
        <div class="ls_nav_nav2">
            <p><a href="#" class="add-vehicle2user"><span>+</span><spring:theme code="detail.operate.tab.label.add" /></a></p>
            <p><a href="#" class="delete-vehicle2user" delete-url="${deleteUrl}"><spring:theme code="detail.operate.tab.label.deleted" /></a></p>
        </div>
    </div>
</div>
<div class="row ls_nav">
    <div class="table-responsive pick_car_list">
        <table class="table  pick_carTable2 pick_carTable_even">
            <thead>
            <tr>
                <th><spring:theme code="vehicle.detail.operate.tab.label.select" /></th>
                <th><spring:theme code="vehicle.detail.related.tab.label.customer.role" /></th>
                <th><spring:theme code="vehicle.detail.related.tab.label.customer.name" /></th>
                <th><spring:theme code="vehicle.detail.related.tab.label.customer.phone" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${vehicleInfo.vehicle2User}" var="user">
                <tr>
                    <td><input type="checkbox" name="pk" value="${user.pk}"></td>
                    <td>${user.userType}</td>
                    <td>${user.name}</td>
                    <td>${user.mobile}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<c:url value="/vehicle/user/create" var="registerActionUrl" />
<detail:addRelatedPopup actionNameKey="register.sumit.btn" action="${registerActionUrl}"/>
