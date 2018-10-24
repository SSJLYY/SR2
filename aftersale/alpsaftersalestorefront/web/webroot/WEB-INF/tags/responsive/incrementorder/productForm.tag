<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="detail" tagdir="/WEB-INF/tags/responsive/vehicle/detail"%>
<div class="entry-operation-box">
    <div class="col-md-12 col-sm-12">
        <div class="ls_nav">
            <div class="ls_nav_nav2">
                <p><a href="#" class="add-multiple-entries"><span>+</span><spring:theme code="detail.operate.tab.label.add" /></a></p>
                <p><a href="#" class="delete-all-selected"><spring:theme code="detail.operate.tab.label.deleted" /></a></p>
            </div>
        </div>
    </div>
    <div class="col-md-12 col-sm-12 pick_car_list">
        <table class="table confirm-entries-table">
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
            </tbody>
        </table>
        <div class="row form-group">
            <div class="submityd">
                <div class="text-center yd_formTop yd_form">
                    <a class="btn btn-default btn_save last-step" href="#"><spring:theme code="last.btn" />
                    </a>
                    <button class="btn btn-primary btn_save" type="submit"><spring:theme code="create.submit" />
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
