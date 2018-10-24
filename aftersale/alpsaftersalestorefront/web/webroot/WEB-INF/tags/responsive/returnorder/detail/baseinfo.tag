<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="returnorder" tagdir="/WEB-INF/tags/responsive/returnorder"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<c:url var="updateAction" value="/return-order/createOrUpdate"/>

<form:form id="increment-order-update" method="post" commandName="incrementOrderData" updateAction="${updateAction}" action="${updateAction}">
<div class="row  ls_nav">
        <div class="ls_nav_nav2  ls_nav_left" style="border-bottom:1px solid #ddd;padding-bottom:20px;">
            <p style="padding-left:15px;">
                <span><spring:theme code="detail.return.order.code" /></span><span>  ${incrementOrderData.code}</span>
                <span><spring:theme code="detail.increment.order.status" /></span><span>  ${incrementOrderData.status}</span>
                <input type="hidden" value="${incrementOrderData.code}" name="code"/>
                <input type="hidden" value="${incrementOrderData.returnOrderCode}" name="returnOrderCode"/>
            </p>
            <div class="work_edit">
                <p class="pull-right"><a href="#" class="yd_submit"><spring:theme code='detail.base.tab.label.save'/></a></p>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2 showbox">
                <label class="word_spac"><spring:theme code="detail.service.order.vehicle.licensePlateNumber" /></label>
                <p>${incrementOrderData.vehicle.licensePlateNumber}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first changebox call-search-vehicle-popup-box" data-source-name-box="#increment-order-vehicle" data-source-id-box="#increment-order-vehicle-code">
                <label class="row_label"><spring:theme code="detail.service.order.vehicle.licensePlateNumber" /></label>
                <input id="increment-order-vehicle" name="vehicle.licensePlateNumber" class="form-control attach-icon" style="width: 59%;" type="text" value="${incrementOrderData.vehicle.licensePlateNumber}">
                <button type="button" class="attach-icon btn btn-primary" style="height:32px;width: 36%;margin-left:-2px;"><spring:theme code='attach.icon.lable' /></button>
                <input type="hidden" value="${incrementOrderData.vehicle.code}" id="increment-order-vehicle-code" name="vehicle.code">
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                <label class="word_spac"><spring:theme code="detail.service.order.serviceConsultant" /></label>
                <p>${incrementOrderData.serviceConsultant.name}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                <label class="word_spac"><spring:theme code="detail.increment.order.preorder" /></label>
                <p></p>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 showbox">
                <label class="word_spac"><spring:theme code="detail.increment.order.customer.name" /></label>
                <p>${incrementOrderData.customer.name}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2 changebox call-search-customer-popup-box" data-source-name-box="#increment-order-customer-name" data-source-id-box="#increment-order-customer-uid">
                <label class="row_label"><spring:theme code="detail.increment.order.customer.name" /></label>
                <input id="increment-order-customer-name" name="customer.name" class="form-control attach-icon" style="width: 59%;" type="text" value="${incrementOrderData.customer.name}">
                <button type="button" class="attach-icon btn btn-primary" style="height:32px;width: 36%;margin-left:-2px;"><spring:theme code='attach.icon.lable' /></button>
                <input type="hidden" value="${incrementOrderData.customer.uid}" id="increment-order-customer-uid" name="customer.uid">
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.increment.order.moblie.number" /></label>
                <p>${incrementOrderData.customer.mobileNumber}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.increment.order.subTotal" /></label>
                <p><spring:theme code="currency.icon"/>${incrementOrderData.total}</p>
            </div>
        </div>
        <div class="row row_left row_right">
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <label class="word_spac"><spring:theme code="detail.increment.order.creationtime" /></label>
                <p>${incrementOrderData.creationtime}</p>
            </div>
            <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                <formElement:formSelectBox idKey="increment-order-status"
                                           labelKey="detail.service.order.status"
                                           path="statusCode"
                                           mandatory="true"
                                           skipBlank="false"
                                           skipBlankMessageKey=""
                                           items="${orderStatus}"
                                           itemValue="id"
                                           itemLabel="label"
                                           selectedValue="${incrementOrderData.statusCode}"
                                           notIncludeWrap="true"/>
            </div>
        </div>
</div>
<div class="row ls_nav entry-operation-box" style="margin-top:15px;padding:0;">
    <div class="table-responsive pick_car_list">
        <table class="table  pick_carTable2 pick_carTable_even changeEntryBox">
            <thead>
                <tr>
                    <th><spring:theme code="table.sequence" /></th>
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
                <c:forEach items="${incrementOrderData.entries}" var="entrie" varStatus="status">
                    <tr sequence-data='${status.index+1}'>
                        <td>${status.index+1}</td>
                        <td>
                            ${entrie.categoryName}
                            <input type="hidden" name="entries[${status.index+1}].categoryCode" value="${entrie.categoryCode}"/>
                        </td>
                        <td class="product-code">
                            <a href="#">${entrie.code}</a>
                            <input type="hidden" name="entries[${status.index+1}].code" value="${entrie.code}"/>
                        </td>
                        <td>${entrie.name}
                            <input type="hidden" name="entries[${status.index+1}].name" value="${entrie.name}"/>
                        </td>
                        <td>${entrie.quantity}
                            <input type="hidden" name="entries[${status.index+1}].quantity" value="${entrie.quantity}"/>
                        </td>
                        <td>${entrie.price}
                            <input type="hidden" name="entries[${status.index+1}].price" value="${entrie.price}"/>
                        </td>
                        <td>${entrie.rate}%
                            <input type="hidden" name="entries[${status.index+1}].rate" value="${entrie.rate}"/>
                        </td>
                        <td>${entrie.actualPrice}
                            <input type="hidden" name="entries[${status.index+1}].actualPrice" value="${entrie.actualPrice}"/>
                        </td>
                        <td>
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
<returnorder:addEntryPopup/>
<returnorder:choseProductPopup/>
<vehicle:choseVehiclePopup/>
