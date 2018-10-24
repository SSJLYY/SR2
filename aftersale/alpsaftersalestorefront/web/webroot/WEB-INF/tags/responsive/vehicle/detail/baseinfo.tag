<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="vehicle-detail" tagdir="/WEB-INF/tags/responsive/vehicle/detail"%>
<c:url var="action" value="/vehicle/createOrUpdate"/>
<div class="tab-box">
    <script type="text/javascript">
        var categoryList = ${categoryList};
        var productList = ${productList};
    </script>
    <form:form id="vehicleInfo-update" method="post" commandName="vehicleInfo" action="${action}">
        <input type="hidden" value="${vehicleInfo.code}" name="code">
        <div class="row  ls_nav showbox">
            <div class=" ls_nav_nav ls_nav_bottom" style="border-bottom:1px solid #dddddd">
                <p><spring:theme code="detail.page.baseinfo" /></p>
                <div class="work_edit">
                    <p class="pull-right" style="border-left: none;"><a href="#" class="yd_submit"><spring:theme code='detail.base.tab.label.save'/></a></p>
                    <p class="pull-right"><a href="#" class="yd_change"><spring:theme code='detail.base.tab.label.update'/></a></p>
                </div>
            </div>
            <div class="row row_left row_right">
                <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.vin.code"/> </label>
                    <p>${vehicleInfo.vinNumber}</p>
                </div>
                <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.owner.name"/> </label>
                    <p>${vehicleInfo.customerName}</p>
                </div>
            </div>

            <div class="row row_left row_right">
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.license.plate.number"/> </label>
                    <p>${vehicleInfo.licensePlateNumber}</p>
                </div>
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.brand"/> </label>
                    <p>${vehicleInfo.vehicleBrand}</p>
                </div>
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.status"/> </label>
                    <p>${vehicleInfo.statusName}</p>
                </div>
            </div>

            <div class="row row_left row_right">
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.category'/> </label>
                    <p>${vehicleInfo.vehicleCategory}</p>
                </div>
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.model'/></label>
                    <p>${vehicleInfo.vehicle}</p>
                </div>
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.store'/></label>
                    <p>${vehicleInfo.store}</p>
                </div>
            </div>

            <div class="row row_left row_right">
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.type'/></label>
                    <p>${vehicleInfo.vehicleType}</p>
                </div>
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.color'/></label>
                    <p>${vehicleInfo.color}</p>
                </div>
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class=""><spring:theme code="vehicle.detail.base.tab.label.human.workload.type" /></label>
                </div>
            </div>

            <div class="row row_left row_right">
                <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.enter.creator'/></label>
                    <p>${vehicleInfo.creator}</p>
                </div>
            </div>
        </div>

        <div class="row  ls_nav showbox" style="margin-top:15px;">
            <div class=" ls_nav_nav ls_nav_bottom" style="border-bottom:1px solid #dddddd">
                <p><spring:theme code="vehicle.detail.base.tab.label.date.info" /></p>
            </div>
            <div class="row row_left row_right">

                <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.enter.factor.date'/></label>
                    <p>${vehicleInfo.enterFactoryDate}</p>
                </div>
                <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
                    <label class="word_spac"><spring:theme code='vehicle.detail.base.tab.label.warranty.start.date'/></label>
                    <p>${vehicleInfo.warrantyStartDate}</p>
                </div>
            </div>
        </div>

        <div class="row  ls_nav showbox" style="margin-top:15px;">
            <div class="row row_left row_right">
                <div class=" ls_nav_nav ls_nav_bottom" style="border-bottom:1px solid #dddddd">
                    <p><spring:theme code="vehicle.detail.base.tab.label.maintain.info" /></p>
                </div>
                <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.warranty.cycle"/> </label>
                    <p>${vehicleInfo.warrantyCycle}</p>
                </div>
                <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.warranty.mileage"/> </label>
                    <p>${vehicleInfo.warrantyMileage}</p>
                </div>
            </div>
            <div class="row row_left row_right">
                <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                    <label class="word_spac"><spring:theme code="vehicle.detail.base.tab.label.warranty.end.date"/> </label>
                    <p>${vehicleInfo.warrantyLastDate}</p>
                </div>
            </div>
        </div>
        <vehicle-detail:changebox/>
    </form:form>
</div>
