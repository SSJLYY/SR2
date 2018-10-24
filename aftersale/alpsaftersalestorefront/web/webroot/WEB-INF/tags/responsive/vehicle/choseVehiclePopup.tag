<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<div class="vehicle-search-popup-wrap-box hide">
    <div class="headline">
        <spring:theme code="vehicle.search.title" />
    </div>
    <div class="content-box">
        <div class="modal-content vehicle-search-popup-box" data-source-name-box="#vehicle-name" data-source-id-box="#vehicle-id">
            <div class="modal-header">
                <div class="col-md-12 col-sm-12 ls_nav">
                    <vehicle:searchForm/>
                </div>
            </div>
            <div class="modal-body">
                <table class="table pick_carTable2 pick_carTable_even">
                    <thead>
                    <tr>
                        <th><spring:theme code="vehicle.list.table.sequence" /></th>
                        <th><spring:theme code="vehicle.list.table.licensePlateNumber" /></th>
                        <th><spring:theme code="vehicle.list.table.VINNumber" /></th>
                        <th><spring:theme code="vehicle.list.table.customer" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-confirm btn_save">
                    <spring:theme code="search.confirm.button" />
                </button>
            </div>
        </div>
    </div>
</div>

