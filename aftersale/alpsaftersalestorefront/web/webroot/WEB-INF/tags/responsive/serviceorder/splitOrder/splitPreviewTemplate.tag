<%@ attribute name="subOrder" required="false" type="com.bp.alps.core.facades.order.SubServiceOrderData"%>
<%@ attribute name="sequence" required="false" type="java.lang.String"%>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class="col-md-12 col-sm-12 ls_nav split-preview-order" style="margin-bottom:15px;">
    <div class=" ls_nav_nav ls_nav_bottom dismanting_click">
        <p><spring:theme code="split.tab.title" /></p>
        <div class="work_edit">
            <p style="border-left:none;"><a href="#" class="deleted-split-order"><spring:theme code="deleted.btn.label"/></a></p>
        </div>
    </div>
    <div class="row">
        <div class="" style="border-bottom:1px solid #ddd;padding-bottom:20px;color:#C71B1E;">
            <p style="padding-left:15px;">
                <span style="padding-left:30px;"><spring:theme code="service.order.sub.type"/>: </span>
                <span class="subtypelabel">${subOrder.serviceSubType}</span>
                <span style="padding-left:30px;"><spring:theme code="service.order.buyer.label"/>: </span>
                <span class="buyerlabel">${subOrder.buyerName}</span>
                <input type="hidden" name="suborder[${sequence!=null?sequence:"x"}].serviceSubTypeCode" value="${subOrder.serviceOrderCode}"/>
                <input type="hidden" name="suborder[${sequence!=null?sequence:"x"}].buyer" value="${subOrder.buyer}"/>
                <input type="hidden" name="suborder[${sequence!=null?sequence:"x"}].serviceOrderCode" value="${serviceOrderData.code}"/>
                <input type="hidden" name="suborder[${sequence!=null?sequence:"x"}].code" value="${subOrder.code}"/>
            </p>
        </div>
        <div class="col-md-12 col-sm-12 pick_car dismanting_none" style="display: none;">
            <div class="table-responsive pick_car_list">
                <table class="table  pick_carTable2 pick_carTable_even">
                    <thead>
                    <tr>
                        <th><spring:theme code="table.sequence" /></th>
                        <th><spring:theme code="service.entry.type" /></th>
                        <th><spring:theme code="entry.itemCategory" /></th>
                        <th><spring:theme code="entry.product.name" /></th>
                        <th><spring:theme code="entry.row.splitrate" /></th>
                        <th><spring:theme code="entry.row.splitAmount" /></th>
                        <th><spring:theme code="entry.row.operation" /></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${subOrder.entries}" varStatus="status">
                            <tr>
                                <td>
                                    ${status.index+1}
                                </td>
                                <td>
                                    ${entry.serviceType}
                                </td>
                                <td>
                                    ${entry.categoryName}
                                </td>
                                <td>
                                    ${entry.name}
                                    <input value="${entry.code}" name="suborder[${sequence!=null?sequence:"x"}].item[${status.index+1}].productCode" type="hidden"/>
                                    <input class="oldPriceValue" value="${entry.price}" name="suborder[${sequence!=null?sequence:"x"}].item[${status.index+1}].price" type="hidden"/>
                                </td>
                                <td class="td_border td_not_padding">
                                    <div class="splitRate-icon">
                                        <input class="split-input splitRateValue" value="${entry.rate}" name="suborder[${sequence!=null?sequence:"x"}].item[${status.index+1}].rate" type="text"/>
                                    </div>
                                </td>
                                <td class="td_border td_not_padding">
                                    <input class="split-input splitPriceValue" value="${entry.actualPrice}"
                                           name="suborder[${sequence!=null?sequence:"x"}].item[${status.index+1}].actualPrice" type="text"/>
                                </td>
                                <td>
                                    <input value="${entry.quantity}" name="suborder[${sequence!=null?sequence:"x"}].item[${status.index+1}].quantity" type="hidden"/>

                                    <c:if test="${subOrder.entries.size()>1}">
                                        <a href="#" class="deleted-split-tr"><spring:theme code="deleted.btn.label" /></a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
