<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<template:defaultPage pageTitle="${pageTitle}">
    <div>
    <form:form action="/alpsaftersalestorefront/pickup/recordCreated" method="post" commandName="pickupInStoreData">
        <div class="wrapper wrapper-content animated fadeInRight wrapper_bottom">
            <div class="row">
                <div class="col-md-12 col-sm-12 ls_nav">


                        <%--第一个tab--%>
                        <div class="pickupbaseinfo">
                            <div class="reseration_inf">
                                <p><spring:theme code="text.aftersales.pickup.create.baseinfo" /></p>
                            </div>

                            <div class="row row_left">
                                <div class="col-md-3 col-sm-5 Ry_flex com_first form-group col_p">
                                    <span class="ry_required">*</span>
                                    <label for="vehicleNumber" class="word_spac"><spring:theme code="text.aftersales.pickup.list.carnumber" /></label>
                                    <input id="vehicleNumber" name="vehicleNumber" type="text" placeholder="<spring:theme code="text.aftersales.pickup.create.carnumber" />" class="form-control">
                                </div>
                            </div>
                            <div class="row row_left">
                                <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                                    <span class="ry_required">*</span>
                                    <label class="word_spac" for="sender"><spring:theme code="text.aftersales.pickup.list.sender" /></label>
                                    <input id="sender" name="sender" class="form-control" type="text" placeholder="<spring:theme code="text.aftersales.pickup.create.sender" />">
                                </div>
                            </div>
                            <div class="row row_left">
                                <div class="col-md-3 col-sm-5 Ry_flex form-group col_p">
                                    <span class="ry_required">*</span>
                                    <label class="word_spac" for="phoneNumber"><spring:theme code="text.aftersales.pickup.list.phonenumber" /></label>
                                    <input id="phoneNumber" name="phoneNumber" class="form-control" type="text" placeholder="<spring:theme code="text.aftersales.pickup.create.phone" />">
                                </div>
                            </div>
                            <div class="row row_left">
                                <div class=" Ry_flex form-group">
                                    <span class="ry_required required_dis">*</span>
                                    <label class="word_spac" for="purpose" ><spring:theme code="text.aftersales.pickup.create.purpose" /></label>
                                    <%--到店目的--%>
                                        <c:forEach items="${pickupinstore_purpose}" var="pickupInStorePurpose">
                                            <div class="but_objective"  data-value="${pickupInStorePurpose.id}">
                                                    ${pickupInStorePurpose.label}
                                            </div>
                                        </c:forEach>

                                        <input id="purpose" name="purpose" class="but_objective" type="hidden">

                                </div>
                            </div>

                            <%--cancle,next--%>
                            <div class="row form-group">
                                <div class="submityd">
                                    <div class="text-center yd_formTop yd_form">
                                        <a href="/alpsaftersalestorefront/desktop/show">
                                            <button class="btn btn-default btn_save" type="button" >
                                                <spring:theme code="text.aftersales.pickup.create.cancel" />
                                            </button>
                                        </a>

                                        <a>
                                            <div class="createChoose next-btn btn btn-primary btn_save">
                                                <spring:theme code="text.aftersales.pickup.create.next" />
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>



                            <%--第二个tab--%>
                            <div class="consultant hide" >
                                <div class="reseration_inf">
                                    <p><spring:theme code="text.aftersales.pickup.create.consult" /></p>
                                </div>

                                <div class="wrapper wrapper-content animated fadeInRight wrapper_bottom">
                                    <div class="row">
                                        <div class="col-md-12 col-sm-12">
                                            <div class="col-md-12 col-sm-12 ls_nav">
                                                <div class="col-md-12 col-sm-12 pick_car">
                                                    <div class="table-responsive pick_car_list">
                                                        <table class="table table-hover2  pick_carTable pick_carTable_even choosecustomer-box">
                                                            <thead>
                                                            <tr>
                                                                <th><spring:theme code="text.aftersales.title.sequence" /></th>
                                                                <th><spring:theme code="text.aftersales.pickup.list.serviceconsulat" /></th>
                                                                <th><spring:theme code="text.aftersales.pickup.create.pickupnumber" /></th>
                                                                <th><spring:theme code="text.aftersales.pickup.create.Waitnumber" /></th>
                                                                <th><spring:theme code="text.aftersales.pickup.create.assign" /></th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${service_consultant}" var="serviceConsultantListResponse" varStatus="rows">
                                                                    <tr>
                                                                        <td>${rows.index+1}&nbsp;</td>

                                                                        <td>
                                                                            <div id="consultant" class="customer">
                                                                                    ${serviceConsultantListResponse.customerName}
                                                                            </div>
                                                                        </td>
                                                                        <td>
                                                                                ${serviceConsultantListResponse.nofPickupToday}
                                                                        </td>
                                                                        <td>
                                                                                ${serviceConsultantListResponse.currentWaitingVehicles}
                                                                        </td>
                                                                        <td>
                                                                            <input name='couponRadio' class='Radio choosecustomer' type='radio' value="${serviceConsultantListResponse.customerId}" />
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                            <input id="serviceConsultant" name="serviceConsultant" class="but_objective" type="hidden">

                                                        </table>
                                                    </div>
                                                </div>
                                                <div class="row form-group">
                                                    <div class="submityd">
                                                        <div class="text-right yd_formTop yd_form">
                                                            <button class="btn btn-default last-btn btn_save" type="button"><spring:theme code="last.btn" />
                                                            </button>
                                                            <button class="btn btn-primary btn_save" type="submit"><spring:theme code="text.aftersales.pickup.create.created" />
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>

                    </div>
                </div>
        </div>
    </form:form>
</div>

</template:defaultPage>
