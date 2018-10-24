<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="chosepopup" tagdir="/WEB-INF/tags/responsive/serviceorder/chosePopup"%>
<div class="product-search-popup-wrap-box hide">
    <div class="headline">
        <spring:theme code="vehicle.search.title" />
    </div>
    <div class="content-box">
        <div class="modal-content col-md-12 col-sm-12 product-search-popup-box" entry-product-price="#price" data-source-name-box="#vehicle-name" data-source-id-box="#vehicle-id">
            <div class="modal-header">
                <button type="button" class="close close-btn">x</button>
                <h4 class="modal-title" id="myModalLabel"><spring:theme code="add.entry.popup.title" /></h4>
                <chosepopup:searchForm/>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12 col-sm-12">
                        <div class="table-responsive pick_car_list">
                            <table class="table pick_carTable2 pick_carTable_even">
                                <thead>
                                <tr>
                                    <th><spring:theme code="table.sequence" /></th>
                                    <th><spring:theme code="product.id" /></th>
                                    <th><spring:theme code="product.name" /></th>
                                    <th><spring:theme code="product.price" /></th>
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

