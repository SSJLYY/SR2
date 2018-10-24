<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="vehicle-detail" tagdir="/WEB-INF/tags/responsive/vehicle/detail"%>
<div class="row  ls_nav changebox">
    <div class=" ls_nav_nav ls_nav_bottom" style="border-bottom:1px solid #dddddd">
        <p><spring:theme code="detail.page.baseinfo" /></p>
        <div class="work_edit">
            <p class="pull-right" style="border-left: none;"><a href="#" class="yd_submit"><spring:theme code='detail.base.tab.label.save'/></a></p>
            <p class="pull-right"><a href="#" class="yd_change"><spring:theme code='detail.base.tab.label.update'/></a></p>
        </div>
    </div>
    <div class="row row_left row_right">
        <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.vin.code" labelKey="vehicle.detail.base.tab.label.vin.code" path="vinNumber" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
        <div class="col-md-4 col-sm-4 Ry_flex form-group com_first col_p2 call-search-customer-popup-box" data-source-name-box="#vehicle-detail-base-name" data-source-id-box="#vehicle-detail-base-id">
            <formElement:formInputBoxAttachIcon idKey="vehicle-detail-base-name" labelKey="vehicle.detail.base.tab.label.owner.name" path="customerName" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
            <input type="hidden" value="${vehicleInfo.customerId}" id="vehicle-detail-base-id" name="customerId"/>
        </div>
    </div>

    <div class="row row_left row_right">
        <div class="col-md-4 col-sm-4 Ry_flex form-group">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.license.plate.number" labelKey="vehicle.detail.base.tab.label.license.plate.number" path="licensePlateNumber" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
        <div class="col-md-4 col-sm-4 Ry_flex form-group">
            <formElement:formSelectBox idKey="register-vehicle-vehicleBrand"
                                       labelKey="vehicle.detail.base.tab.label.brand"
                                       path="vehicleBrandCode"
                                       labelCSS="row_label"
                                       mandatory="true"
                                       skipBlank="false"
                                       skipBlankMessageKey=""
                                       items="${brandList}"
                                       itemValue="code"
                                       itemLabel="name"
                                       selectedValue="${vehicleInfo.vehicleBrandCode}"
                                       notIncludeWrap="true"/>
        </div>
        <div class="col-md-4 col-sm-4 Ry_flex form-group">
            <formElement:formSelectBox
                    idKey="vehicle.detail.base.tab.label.status"
                    labelKey="vehicle.detail.base.tab.label.status"
                    items="${vehicleStatus}"
                    path="status"
                    labelCSS="row_label"
                    itemValue="id"
                    itemLabel="label"
                    selectedValue="${vehicleInfo.status}"
                    notIncludeWrap="true" />
        </div>
    </div>

    <div class="row row_left row_right">
        <div class="col-md-4 col-sm-4 Ry_flex form-group">
            <label class="row_label " for="register-vehicle-vehicleCategory">
                <spring:theme code='vehicle.detail.base.tab.label.category'/>
            </label>
            <select id="register-vehicle-vehicleCategory" class="form-control hell " name="vehicleCategoryCode">
                <option value="${vehicleInfo.vehicleCategoryCode}">${vehicleInfo.vehicleCategory}</option>
            </select>
        </div>
        <div class="col-md-4 col-sm-4 Ry_flex form-group">
            <label class="row_label " for="register-vehicle-vehicle">
                <spring:theme code='vehicle.detail.base.tab.label.model'/>
            </label>
            <select id="register-vehicle-vehicle" class="form-control hell " name="vehicleCode">
                <option value="${vehicleInfo.vehicleCode}">${vehicleInfo.vehicle}</option>
            </select>
        </div>
        <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.store" labelKey="vehicle.detail.base.tab.label.store" path="store" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
    </div>

    <div class="row row_left row_right">
        <div class="col-md-4 col-sm-4 Ry_flex form-group">
            <formElement:formSelectBox
                    idKey="vehicle.detail.base.tab.label.type"
                    labelKey="vehicle.detail.base.tab.label.type"
                    items="${vehicleType}"
                    path="vehicleType"
                    labelCSS="row_label"
                    itemValue="id"
                    itemLabel="label"
                    selectedValue="${vehicleInfo.vehicleType}"
                    notIncludeWrap="true"/>
        </div>
        <div class="col-md-4 col-sm-4 Ry_flex form-group">
            <label class="row_label " for="register-vehicle-color">
                <spring:theme code='vehicle.detail.base.tab.label.color'/>
            </label>
            <select id="register-vehicle-color" class="form-control hell " name="color">
                <option value="${vehicleInfo.color}">${vehicleInfo.color}</option>
            </select>
        </div>
        <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
            <label class=""><spring:theme code="vehicle.detail.base.tab.label.human.workload.type" /></label>
        </div>
    </div>

    <div class="row row_left row_right">
        <div class="col-md-4 col-sm-4 Ry_flex form-group col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.enter.creator" labelKey="vehicle.detail.base.tab.label.enter.creator" path="creator" labelCSS="row_label" inputCSS="form-control" disabled="true" notIncludeWrap="true" />
        </div>
    </div>
</div>

<div class="row  ls_nav changebox" style="margin-top:15px;">
    <div class=" ls_nav_nav ls_nav_bottom" style="border-bottom:1px solid #dddddd">
        <p><spring:theme code="vehicle.detail.base.tab.label.date.info" /></p>
    </div>
    <div class="row row_left row_right">

        <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.enter.factor.date" labelKey="vehicle.detail.base.tab.label.enter.factor.date" path="enterFactoryDate" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
        <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.warranty.start.date" labelKey="vehicle.detail.base.tab.label.warranty.start.date" path="warrantyStartDate" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
    </div>
</div>

<div class="row  ls_nav changebox" style="margin-top:15px;">
    <div class="row row_left row_right">
        <div class=" ls_nav_nav ls_nav_bottom" style="border-bottom:1px solid #dddddd">
            <p><spring:theme code="vehicle.detail.base.tab.label.maintain.info" /></p>
        </div>
        <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.warranty.cycle" labelKey="vehicle.detail.base.tab.label.warranty.cycle" path="warrantyCycle" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
        <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.warranty.mileage" labelKey="vehicle.detail.base.tab.label.warranty.mileage" path="warrantyMileage" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
    </div>
    <div class="row row_left row_right">
        <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
            <formElement:formInputBox idKey="vehicle.detail.base.tab.label.warranty.end.date" labelKey="vehicle.detail.base.tab.label.warranty.end.date" path="warrantyLastDate" labelCSS="row_label" inputCSS="form-control" notIncludeWrap="true" />
        </div>
    </div>
</div>
