<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<template:page pageTitle="${pageTitle}">
    <form:form commandName="quotationConfirmFrom" method="post" enctype="multipart/form-data" action="/alpssalestorefront/alps/zh/quotation/confirm" class="import-csv__form">
        <input type="hidden" name="customerConfirm" value="true"/>
        <input type="hidden" name="quotationCode" value="${quotation.code}"/>
        <div class="mui-content price-content" id="mui-content">
            <div class="mui-card mui-clearfix price-card">
                <div class="mui-card-header">
                    <ul class="mui-table-view">
                        <li class="mui-table-view-cell" id="quotationCode">
                            <spring:theme code="quotation.code.label"/> : ${quotation.code}
                        </li>
                        <li class="mui-table-view-cell" id="creationtime" style="font-weight: 400;">
                                ${quotation.creationtime}
                        </li>
                    </ul>
                </div>

                <div class="mui-card-content">
                    <ul class="mui-table-view mui-clearfix">
                        <li class="mui-table-view-cell">
                            <label><spring:theme code="quotation.customer.name.label"/></label>
                            <var id="customer" data-customerName="${quotation.customer.name}">${quotation.customer.name}</var>
                        </li>
                        <li class="mui-table-view-cell">
                            <label><spring:theme code="quotation.deposit.label"/></label>
                            <var>${quotation.deposit > 0 ? quotation.deposit : 0}</var>
                            <!--<input type="text" name="name" id="flow_name" class="mui-input-clear" placeholder="请输入姓名" value="<spring:theme code="quotation.currency.unit"/>100,000">-->
                        </li>
                        <li class="mui-table-view-cell">
                            <label><spring:theme code="quotation.line.itemName.label"/></label>
                            <var>${quotation.lineItemName }</var>
                        </li>

                    </ul>
                </div>
            </div>

            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell ">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.price.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.optionalProductTotalPrice.label"/></label>
                                <p><span>${quotation.optionalProductTotalPrice >0 ? quotation.optionalProductTotalPrice : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.decorProductTotalPrice.label"/></label>
                                <p><span>${quotation.decorProductTotalPrice > 0 ? quotation.decorProductTotalPrice : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.secondHandCarEvaluationOfPrice.label"/></label>
                                <p><span>${quotation.secondHandCarEvaluationOfPrice}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.licensePlateServiceCharge.label"/></label>
                                <p><span>${quotation.licensePlateServiceCharge>0 ? quotation.licensePlateServiceCharge : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeServiceCharge.label"/></label>
                                <p><span>${quotation.financeServiceCharge>0 ? quotation.financeServiceCharge : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.insuranceProductTotalPrice.label"/></label>
                                <p><span>${quotation.insuranceProductTotalPrice>0 ? quotation.insuranceProductTotalPrice : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.voucherTotalPrice.label"/></label>
                                <p><span>${quotation.voucherTotalPrice>0 ? quotation.voucherTotalPrice : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.extendedWarrantyTotalPrice.label"/></label>
                                <p><span>${quotation.extendedWarrantyTotalPrice>0 ? quotation.extendedWarrantyTotalPrice : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell red">
                                <label class="require"><spring:theme code="quotation.totalPrice.label"/></label>
                                <p><span>${quotation.totalPrice}</span></p>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>

            <!--车辆信息-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.vehicle.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.vehicle.brand.label"/></label>
                                <p><span>${quotation.vehicleBrandName}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.vehicle.category.label"/></label>
                                <p><span>${quotation.carModelName}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.vehicle.label"/></label>
                                <p><span>${quotation.vehicleName}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.outside.color.label"/></label>
                                <p><span>${quotation.carColorName}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.inside.color.label"/></label>
                                <p><span>${quotation.carInsideColor}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.delivery.time.label"/></label>
                                <p><span>${quotation.deliveryDate }</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.carPrice.price.label"/></label>
                                <p><span>${quotation.carPrice}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.price.label"/></label>
                                <p><span>${quotation.carSalesPrice}</span></p>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>

            <!--原厂选装-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.optionalProduct.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <c:forEach var="item" items="${quotation.optionalProduct}">
                                <li class="mui-table-view-cell titles">
                                    <label><spring:theme code="quotation.product.code.label"/>:${item.code}</label>
                                </li>
                                <li class="mui-table-view-cell detailWidth">
                                    <label class="require"><spring:theme code="quotation.product.describe.label"/></label>
                                    <p><span>${ item.name}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.quantity.label"/></label>
                                    <p><span>${ item.quantity}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.price.label"/></label>
                                    <p><span><spring:theme code="quotation.currency.unit"/> ${ item.price}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.actual.price.label"/></label>
                                    <p class=""><span><spring:theme code="quotation.currency.unit"/> ${ item.actualPrice}</span></p>
                                </li>
                            </c:forEach>
                            <p class="line total"><spring:theme code="quotation.row.total.price.label"/>${quotation.optionalProductTotalPrice>0 ? quotation.optionalProductTotalPrice : 0}</p>
                        </ul>
                    </li>
                </ul>
            </div>

            <!--装潢-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.upholstery.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <c:forEach var="item" items="${quotation.upholsteryProduct}">
                                <li class="mui-table-view-cell titles">
                                    <label><spring:theme code="quotation.product.code.label"/>:${ item.code}</label>
                                </li>
                                <li class="mui-table-view-cell detailWidth">
                                    <label class="require"><spring:theme code="quotation.product.describe.label"/></label>
                                    <p><span>${ item.name}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.quantity.label"/></label>
                                    <p><span>${ item.quantity}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.price.label"/></label>
                                    <p><span><spring:theme code="quotation.currency.unit"/> ${ item.price} </span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.actual.price.label"/></label>
                                    <p class=""><span><spring:theme code="quotation.currency.unit"/> ${ item.actualPrice}</span></p>
                                </li>
                            </c:forEach>
                            <p class="line total">
                                <span><spring:theme code="quotation.row.total.price.label"/>${quotation.decorProductTotalPrice > 0 ? quotation.decorProductTotalPrice : 0}</span>
                            </p>
                        </ul>
                    </li>
                </ul>
            </div>

            <!--二手车登记-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.secondHandCar.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.secondHandCar.CarBrand"/> </label>
                                <p><span>${quotation.secondHandCarBrand}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.secondHandCar.CarVhicle"/></label>
                                <p><span>${quotation.secondHandCarVhicle}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.secondHandCar.CarMileage"/></label>
                                <p><span>${quotation.secondHandCarMileage}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.secondHandCar.PurchasedDated"/></label>
                                <p><span>${quotation.secondHandCarPurchasedDated}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.secondHandCar.EvaluationOfPrice"/></label>
                                <p><span>${quotation.secondHandCarEvaluationOfPrice}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.secondHandCar.RecycleType"/></label>
                                <p><span>
                                        ${quotation.secondHandCarRecycleType}
                                </span></p>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--上牌-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.licensePlateService.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.licensePlatePurchaseMethod"/></label>
                                <p><span>${quotation.licensePlatePurchaseMethod}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.licensePlateTax"/></label>
                                <p><span>${quotation.licensePlateTax}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.vehicleTypeForLicensePlate"/></label>
                                <p><span>${quotation.vehicleTypeForLicensePlate}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.licensePlateServiceCharge"/></label>
                                <p><span>${quotation.licensePlateServiceCharge}</span></p>
                            </li>

                            <p class="line total"><spring:theme code="quotation.row.total.price.label"/>${quotation.licensePlateServiceCharge}</p>
                        </ul>
                    </li>
                </ul>
            </div>

            <!--金融产品-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.finance.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeType"/></label>
                                <p><span>${quotation.financeType}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeCycle"/></label>
                                <p><span>${quotation.financeCycle}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeCompany"/></label>
                                <p><span>${quotation.financeCompany}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeProduct"/></label>
                                <p><span>${quotation.financeProduct}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeStartTime"/></label>
                                <p><span>${quotation.financeStartTime}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeServiceCharge"/></label>
                                <p><span>${quotation.financeServiceCharge}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeRate"/></label>
                                <p><span>${quotation.financeRate}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeUnitPrice"/></label>
                                <p><span>${quotation.financeUnitPrice}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.financeMortgage"/></label>
                                <p><span>${quotation.financeMortgage ? 1 : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell detailWidth">
                                <label class="require"><spring:theme code="quotation.financeRemark"/></label>
                                <p><span>${quotation.financeRemark}</span></p>
                            </li>
                            <p class="line total"><spring:theme code="quotation.row.total.price.label"/>${quotation.financeServiceCharge > 0 ? quotation.financeServiceCharge : 0}</p>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--新车车险-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.insurance.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <c:forEach var="item" items="${quotation.insuranceProduct}">
                                <li class="mui-table-view-cell titles">
                                    <label><spring:theme code="quotation.product.code.label"/>：${ item.code}</label>
                                </li>
                                <li class="mui-table-view-cell detailWidth">
                                    <label class="require"><spring:theme code="quotation.product.describe.label"/></label>
                                    <p><span>${ item.name}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.quantity.label"/></label>
                                    <p><span>${ item.quantity}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.price.label"/></label>
                                    <p><span><spring:theme code="quotation.currency.unit"/> ${ item.price}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.actual.price.label"/></label>
                                    <p class=""><span><spring:theme code="quotation.currency.unit"/> ${ item.actualPrice}</span></p>
                                </li>
                            </c:forEach>
                            <p class="line total">
                                <span><spring:theme code="quotation.row.total.price.label"/>${quotation.insuranceProductTotalPrice >0 ? quotation.insuranceProductTotalPrice : 0}</span>
                            </p>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--卡券-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.coupon.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <c:forEach var="item" items="${quotation.coupon}">
                                <li class="mui-table-view-cell titles">
                                    <label><spring:theme code="quotation.product.code.label"/>:${ item.code}</label>
                                </li>
                                <li class="mui-table-view-cell detailWidth">
                                    <label class="require"><spring:theme code="quotation.product.describe.label"/></label>
                                    <p><span>${ item.name}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.quantity.label"/></label>
                                    <p><span>${ item.quantity}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.price.label"/></label>
                                    <p><span><spring:theme code="quotation.currency.unit"/> ${ item.price}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.actual.price.label"/></label>
                                    <p class=""><span><spring:theme code="quotation.currency.unit"/> ${ item.actualPrice}</span></p>
                                </li>
                            </c:forEach>
                            <p class="line total">
                                <span><spring:theme code="quotation.row.total.price.label"/>${quotation.voucherTotalPrice > 0 ? quotation.voucherTotalPrice : 0}</span>
                            </p>

                        </ul>
                    </li>
                </ul>
            </div>
            <!--延保-->
            <div class="mui-card car-card particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.Insurance.box.label"/></a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <c:forEach var="item" items="${quotation.extendedWarrantyProduct}">
                                <li class="mui-table-view-cell titles">
                                    <label><spring:theme code="quotation.product.code.label"/>:${ item.code}</label>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.describe.label"/></label>
                                    <p><span>${ item.name}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.quantity.label"/></label>
                                    <p><span>${ item.quantity}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.price.label"/></label>
                                    <p><span>${ item.price}</span></p>
                                </li>
                                <li class="mui-table-view-cell">
                                    <label class="require"><spring:theme code="quotation.product.actual.price.label"/> </label>
                                    <p><span>${ item.actualPrice}</span></p>
                                </li>
                            </c:forEach>
                            <p class="line total"><spring:theme code="quotation.row.total.price.label"/>${quotation.extendedWarrantyTotalPrice > 0 ? quotation.extendedWarrantyTotalPrice : 0}</p>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--其他项目-->
            <div class="mui-card car-card carDetails particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell mui-collapse">
                        <a class="mui-navigate-right" href="#"><spring:theme code="quotation.other.box.label"/> </a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.service.describe.label"/> </label>
                                <p><span>${quotation.serviceInfo}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.other.describe.label"/></label>
                                <p><span>${quotation.servicePrice > 0 ? quotation.servicePrice : 0}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.other.describe.label"/></label>
                                <p><span>${quotation.otherIncomInfo}</span></p>
                            </li>
                            <li class="mui-table-view-cell">
                                <label class="require"><spring:theme code="quotation.other.price.label"/> </label>
                                <p><span>${quotation.otherPrice>0 ? quotation.otherPrice : 0}</span></p>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="mui-card car-card carDetails particulars">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell ">
                        <a class="mui-tab-item" id="quotation" href="#"><spring:theme code="quotation.customer.sgin.box.label"/> </a>
                    </li>
                    <li class="mui-table-view-cell ">
                        <input type="file" name="signMedia" id="signMedia" class="file-loading" multiple="multiple">
                    </li>
                    <li class="mui-table-view-cell ">
                        <button class="button mui-btn-green mui-pull-right" type="submit"><spring:theme code="quotation.confirm.quotation.box.label"/> </button>
                    </li>
                </ul>
            </div>
        </div>
    </form:form>
</template:page>
