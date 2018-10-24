<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<div class="customer-search-popup-wrap-box hide">
    <div class="headline">
        <spring:theme code="customer.search.title" />
    </div>
    <div class="content-box">
        <div class="modal-content customer-search-popup-box" data-source-name-box="#vehicle-detail-base-name" data-source-id-box="#vehicle-detail-base-id">
            <div class="modal-header">
                <div class="col-md-12 col-sm-12 ls_nav">
                    <div class=" ls_nav_nav ls_nav_bottom">
                        <p><spring:theme code="search.box.header" /></p>
                    </div>
                    <c:url var="action" value="/customer/search"/>
                    <form:form id="customer-search-form" method="post" commandName="customerSearchRequest" action="${action}">
                        <div class="col-md-4 col-sm-4 Ry_flex form-group">
                            <formElement:formInputBox idKey="search.customer.name" labelKey="search.customer.name" path="name" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
                        </div>
                        <div class="col-md-5 col-sm-5 Ry_flex form-group">
                            <formElement:formInputBox idKey="search.customer.phone" labelKey="search.customer.phone" path="phone" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
                        </div>
                        <div class="col-md-3 yd_flex form-group col-sm-4 pull-right search-btn" style="flex-direction:row-reverse">
                            <button class="btn btn-primary yd_submit" type="button"><spring:theme code="search.button" /></button>
                        </div>
                    </form:form>
                </div>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                        <div class="table-responsive pick_car_list">
                            <table class="table  pick_carTable2 pick_carTable_even">
                                <thead>
                                <tr>
                                    <th><spring:theme code="customer.list.sequence" /></th>
                                    <th><spring:theme code="customer.list.name" /></th>
                                    <th><spring:theme code="customer.list.phone" /></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-confirm btn_save">
                    <spring:theme code="search.confirm.button" />
                </button>
            </div>
        </div>
    </div>
</div>

