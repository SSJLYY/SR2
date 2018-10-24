<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="serviceorder" tagdir="/WEB-INF/tags/responsive/serviceorder"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<c:url var="action" value="/service-order/createOrUpdate"/>
<form:form id="service-order-update" method="post" commandName="serviceOrderData" action="${action}">
<div class="row  ls_nav">
        <div class="ls_nav_nav2  ls_nav_left" style="border-bottom:1px solid #ddd;padding-bottom:20px;">
            <p style="padding-left:15px;">
                <span><spring:theme code="detail.service.order.code" /></span><span>  ${serviceOrderData.code}</span>
                <span><spring:theme code="detail.service.order.type" /></span><span>  ${serviceOrderData.serviceType}</span>
                <input type="hidden" value="${serviceOrderData.code}" name="code"/>
            </p>
            <div class="work_edit">
                <c:url value="/service-order/splitorder/order?code=${serviceOrderData.code}" var="splitOrderBtn"/>
                <a href="${splitOrderBtn}" class="btn btn-primary pull-right btn_save" style="margin-top:-6px;margin-right:15px;"><spring:theme code='split.btn.label'/></a>
                <p class="pull-right"><a href="#" class="yd_submit"><spring:theme code='detail.base.tab.label.save'/></a></p>
                <p class="pull-right"><a href="#" class="yd_change"><spring:theme code='detail.base.tab.label.update'/></a></p>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                <label class="row_label"><spring:theme code="detail.service.order.code.infactory" /></label>
                <p>${serviceOrderData.code}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                <label class="row_label"><spring:theme code="detail.service.order.serviceConsultant" /></label>
                <p>${serviceOrderData.serviceConsultant.name}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                <label class="row_label"><spring:theme code="detail.service.order.cost.center" /> </label>
                <p>-</p>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 showbox">
                <label class="row_label"><spring:theme code="detail.service.order.sender" /></label>
                <p class="showbox">${serviceOrderData.sender.name}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 changebox call-search-customer-popup-box" data-source-name-box="#service-order-sender-name" data-source-id-box="#service-order-sender-uid">
                <label class="row_label"><spring:theme code="detail.service.order.sender" /></label>
                <input id="service-order-sender-name" name="sender.name" class="form-control attach-icon" style="width: 59%;" type="text" value="${serviceOrderData.sender.name}">
                <button type="button" class="attach-icon btn btn-primary" style="height:32px;width: 36%;margin-left:-2px;"><spring:theme code='attach.icon.lable' /></button>
                <input type="hidden" value="${serviceOrderData.sender.uid}" id="service-order-sender-uid" name="sender.uid">
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="row_label"><spring:theme code="detail.service.order.sender.moblie.number" /></label>
                <p>${serviceOrderData.sender.mobileNumber}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="row_label"><spring:theme code="detail.service.order.work.type" /></label>
                <p>-</p>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 showbox">
                <label class="row_label"><spring:theme code="detail.service.order.vehicle.licensePlateNumber" /></label>
                <p>${serviceOrderData.vehicle.licensePlateNumber}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 changebox call-search-vehicle-popup-box" data-source-name-box="#service-order-vehicle" data-source-id-box="#service-order-vehicle-code">
                <label class="row_label"><spring:theme code="detail.service.order.vehicle.licensePlateNumber" /></label>
                <input id="service-order-vehicle" name="vehicle.licensePlateNumber" class="form-control attach-icon" style="width: 59%;" type="text" value="${serviceOrderData.vehicle.licensePlateNumber}">
                <button type="button" class="attach-icon btn btn-primary" style="height:32px;width: 36%;margin-left:-2px;"><spring:theme code='attach.icon.lable' /></button>
                <input type="hidden" value="${serviceOrderData.vehicle.code}" id="service-order-vehicle-code" name="vehicle.code">
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="row_label"><spring:theme code="detail.service.order.vehicle.owner" /></label>
                <p>${serviceOrderData.vehicle.customerName}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="row_label"><spring:theme code="detail.service.order.vehicle.category" /> </label>
                <p>${serviceOrderData.vehicle.vehicleBrand} ${serviceOrderData.vehicle.vehicleCategory}</p>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 showbox">
                <label class="row_label"><spring:theme code="detail.service.order.mileageInFactory" /></label>
                <p>${serviceOrderData.mileageInFactory}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 changebox">
                <label class="row_label"><spring:theme code="detail.service.order.mileageInFactory" /></label>
                <input name="mileageInFactory" class="form-control hell" style="width: 59%;" type="text" value="${serviceOrderData.mileageInFactory}">
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <formElement:formSelectBox idKey="service-order-status"
                                           labelKey="detail.service.order.status"
                                           path="statusCode"
                                           mandatory="true"
                                           skipBlank="false"
                                           skipBlankMessageKey=""
                                           items="${serviceOrderStatusList}"
                                           itemValue="id"
                                           itemLabel="label"
                                           selectedValue=""
                                           notIncludeWrap="true"/>
            </div>
        </div>
</div>
<div class="row ls_nav entry-operation-box" style="margin-top:15px;padding:0;">
    <div class="ls_nav">
        <div class="ls_nav_nav2">
            <p><a href="#" class="add-multiple-entries"><span>+</span><spring:theme code="detail.operate.tab.label.add" /></a></p>
            <!--在已完成的状态下是没有删除和批量删除的 -->
            <p><a href="#" class="delete-all-selected"><spring:theme code="detail.operate.tab.label.deleted" /></a></p>
        </div>
    </div>
    <div class="table-responsive pick_car_list">
        <table class="table pick_carTable2 pick_carTable_even changeEntryBox confirm-entries-table">
            <thead>
                <tr>
                    <th><spring:theme code="table.sequence" /></th>
                    <th><spring:theme code="service.entry.type" /></th>
                    <th><spring:theme code="entry.itemCategory" /></th>
                    <th><spring:theme code="entry.product.code" /></th>
                    <th><spring:theme code="entry.product.name" /></th>
                    <th><spring:theme code="entry.quantity" /></th>
                    <th><spring:theme code="entry.price" /></th>
                    <th><spring:theme code="entry.discount.rate" /></th>
                    <th><spring:theme code="entry.actual.price" /></th>
                    <th><spring:theme code="entry.row.total" /></th>
                    <th><spring:theme code="entry.row.chose" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${serviceOrderData.entries}" var="entrie" varStatus="status">
                    <tr sequence-data='${status.index+1}'>
                        <td>${status.index+1}</td>
                        <td>${entrie.serviceType}
                            <input type="hidden" name="entries[${status.index+1}].serviceTypeCode" value="${entrie.serviceTypeCode}"/>
                        </td>
                        <td>${entrie.categoryName}
                            <input type="hidden" name="entries[${status.index+1}].categoryCode" value="${entrie.categoryCode}"/>
                        </td>
                        <td class='product-code'>
                            <a href="#">${entrie.code}</a>
                            <input type="hidden" name="entries[${status.index+1}].code" value="${entrie.code}"/>
                        </td>
                        <td>${entrie.name}
                            <input type="hidden" name="entries[${status.index+1}].name" value="${entrie.name}"/>
                        </td>
                        <td class="entry-quantity"><span>${entrie.quantity}</span>
                            <input type="hidden" name="entries[${status.index+1}].quantity" value="${entrie.quantity}"/>
                        </td>
                        <td>${entrie.price}
                            <input type="hidden" name="entries[${status.index+1}].price" value="${entrie.price}"/>
                        </td>
                        <td class="discountRate"><span>${entrie.rate}</span>%
                            <input type="hidden" name="entries[${status.index+1}].rate" value="${entrie.rate}"/>
                        </td>
                        <td class="actualPrice"><span>${entrie.actualPrice}</span>
                            <input type="hidden" name="entries[${status.index+1}].actualPrice" value="${entrie.actualPrice}"/>
                        </td>
                        <td class='total-price'>
                            ${entrie.actualPrice*entrie.quantity}
                        </td>
                        <td>
                            <input type="checkbox" class="operationBox" name="entries[${status.index+1}].operationBox" value="${entrie.code}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</form:form>
<serviceorder:addEntryPopup/>
<vehicle:choseVehiclePopup/>
