<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class="col-md-12 col-sm-12 pick_car">
    <div class="table-responsive pick_car_list">
        <table class="table pick_carTable2 pick_carTable_even able-split-list">
            <thead>
            <tr>
                <th><spring:theme code="table.sequence" /></th>
                <th><spring:theme code="service.entry.type" /></th>
                <th><spring:theme code="entry.itemCategory" /></th>
                <th><spring:theme code="entry.product.code" /></th>
                <th><spring:theme code="entry.product.name" /></th>
                <th><spring:theme code="entry.row.total" /></th>
                <th><spring:theme code="entry.row.remainSum" /></th>
                <th><spring:theme code="entry.row.chose" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${serviceOrderData.entries}" var="entrie" varStatus="status">
                <tr>
                    <td>
                        ${status.index+1}
                    </td>
                    <td>
                        ${entrie.serviceType}
                    </td>
                    <td>
                        ${entrie.categoryName}
                    </td>
                    <td class="need-remove">
                        ${entrie.code}
                    </td>
                    <td>
                        ${entrie.name}
                        <input value="${entrie.code}" name="suborder[x].item[${status.index+1}].productCode" type="hidden"/>
                        <input class="oldPriceValue" value="${entrie.price}" name="suborder[x].item[${status.index+1}].price" type="hidden"/>
                        <input value="1" name="suborder[x].item[${status.index+1}].quantity" type="hidden"/>
                    </td>
                    <td class="totalprice need-remove">
                        ${entrie.actualPrice*entrie.quantity}
                    </td>
                    <td class="remainSum need-remove">
                        ${entrie.remain}
                    </td>
                    <td class="td_border td_not_padding" style="display: none;">
                        <div class="splitRate-icon">
                            <input class="split-input splitRateValue" value="0" name="suborder[${sequence!=null?sequence:"x"}].item[${status.index+1}].rate" type="text"/>
                        </div>
                    </td>
                    <td class="td_border td_not_padding" style="display: none;">
                        <input class="split-input splitPriceValue" value="${entry.actualPrice}"
                               name="suborder[${sequence!=null?sequence:"x"}].item[${status.index+1}].actualPrice" type="text"/>
                    </td>
                    <td class="need-remove">
                        <input class="split-selection-box" type="checkbox" name="code" value="${entrie.code}"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
