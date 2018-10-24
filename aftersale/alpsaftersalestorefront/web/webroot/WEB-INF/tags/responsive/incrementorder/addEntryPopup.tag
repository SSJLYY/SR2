<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<div class="add-entry-popup-wrap-box hide">
    <div class="headline">
        <h4><spring:theme code="add.entry.title" /></h4>
    </div>
    <div class="content-box">
        <div class="modal-dialog-box">
            <div class="modal-body">
                <select id="entry-itemCategory" name="product.itemCategory" style="display: none;">
                    <option value="components" selected="selected"><spring:theme code="category.components.name"/></option>
                </select>
                <div class="row">
                    <c:url var="requestProductUrl" value="/product"/>
                    <div class="col-md-8 col-sm-8 Ry_flex form-group col_p call-search-product-popup-box" data-source-name-box="#entry-product-name" data-source-id-box="#entry-product-id" data-source-category-box="#entry-itemCategory" entry-product-price="#entry-product-price" entry-actual-price="#entry-actual-price" data-request-url="${requestProductUrl}">
                        <label for="entry-product-name" class="label_color label_width"><spring:theme code='entry.product.name' /></label>
                        <input id="entry-product-name" name="products.name" class="attach-icon form-control" style="width:65%" type="text" value=""/>
                        <button type="button" class="attach-icon btn btn-primary" style="height:32px;margin-left:-2px;">
                            <spring:theme code='attach.icon.lable' />
                        </button>
                        <input type="hidden" value="" id="entry-product-id" name="entry.product.id">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                        <label for="" class="label_color label_width"><spring:theme code='entry.quantity' /></label>
                        <input id="entry-quantity" name="products.quantity" class="input_number form-control" type="text" value="">

                        <label for="" class="input_company label_color"></label>
                        <label for="" class="label_color"><spring:theme code='entry.actual.price' /></label>
                        <p id="entry-product-price" class="label_p">0</p>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                        <label for="entry-discount-rate" class="label_color label_width"><spring:theme code='entry.discount.rate' /></label>
                        <input id="entry-discount-rate" name="products.discountRate" class="input_number form-control" type="text" value="100"><label for="" class="label_color input_company">%</label>
                        <label for="entry-actual-price" class="label_color"><spring:theme code='entry.actual.price' /></label>
                        <input id="entry-actual-price" name="products.actualPrice" class="input_number form-control" type="text" value="">
                        <label for="" class="label_color input_company"></label>
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
