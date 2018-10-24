<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:url value="/my-account/update-profile" var="updateProfileUrl"/>
<spring:url value="/my-account/update-password" var="updatePasswordUrl"/>
<spring:url value="/my-account/update-email" var="updateEmailUrl"/>
<spring:url value="/my-account/address-book" var="addressBookUrl"/>
<spring:url value="/my-account/payment-details" var="paymentDetailsUrl"/>
<spring:url value="/my-account/orders" var="ordersUrl"/>

<template:defaultPage pageTitle="${pageTitle}">
    <c:url value="/my-account/update-profile" var="action" />
    <div class="col-md-12 col-sm-12">
        <div class="col-md-12 col-sm-12 ls_nav">
            <form:form id="account-form" method="post" commandName="customerData" action="${action}">
            <div class="row row_left row_right" style="padding:40px 0 60px 0;">
                <div class="col-md-2 col-sm-4 form-group com_first col_p2 my_setting">
                    <div class="col-md-12 col-sm-12 my_top">
                        <img src="${themeResourcePath}/images/myself3.png" alt="" class="my_img">
                    </div>
                    <c:url var="changePassword" value="/login/pw/change"/>
                    <a href="${changePassword}" class="setting_pass"><spring:theme code="account.detail.change.password" /></a>
                </div>
                <div class="col-md-6 col-sm-6 form-group com_first col_p2 my_setting showbox">
                    <div class="col-md-12 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.user.name" /></label>
                        <p>${user.name}</p>
                    </div>
                    <div class="col-md-12 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.phone.number" /></label>
                        <p>${user.mobileNumber}</p>
                    </div>
                    <div class="col-md-12 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.user.uid" /></label>
                        <p>${user.uid}</p>
                    </div>
                    <div class="col-md-12 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.group.name" /></label>
                        <p>${user.store}</p>
                    </div>
                    <div class="col-md-12 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.role.name" /></label>
                        <p>${user.group}</p>
                    </div>
                    <div class="col-md-12 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.user.sign" /></label>
                        <p>${user.description}</p>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 form-group com_first col_p2 my_setting my_editXinxi changebox">
                    <div class="col-md-8 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.user.name" /></label>
                        <span class="ry_required">*</span><input type="text" name="name" placeholder="${user.name}" class="form-control">
                    </div>
                    <div class="col-md-8 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.old.phone.number" /></label>
                        <span class="ry_required">*</span><input type="text" name="oldmobileNumber" placeholder="${user.mobileNumber}" class="form-control">
                    </div>
                    <div class="col-md-8 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.new.phone.number" /></label>
                        <span class="ry_required">*</span><input type="text" name="mobileNumber" placeholder="${user.mobileNumber}" class="form-control">
                    </div>
                    <div class="col-md-8 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac" style="width:113px;"><spring:theme code="account.detail.user.uid" /></label>
                        <p>${user.uid}</p>
                    </div>
                    <div class="col-md-8 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac" style="width:113px;"><spring:theme code="account.detail.group.name" /></label>
                        <p>${user.store}</p>
                    </div>
                    <div class="col-md-8 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac" style="width:113px;"><spring:theme code="account.detail.role.name" /></label>
                        <p>${user.group}</p>
                    </div>
                    <div class="col-md-8 col-sm-12 Ry_flex my_top">
                        <label for="" class="word_spac"><spring:theme code="account.detail.user.sign" /></label>
                        <span class="ry_required required_dis">*</span><input type="text" name="description" placeholder="${user.description}" class="form-control">
                    </div>
                </div>
                <p><a href="#" class="yd_change my_edit pull-right"><spring:theme code="account.detail.change.btn" /></a></p>
            </div>
            <div class="row form-group changebox">
                <div class="submityd">
                    <div class="text-center yd_formTop yd_form">
                        <button class="btn btn-default btn_save yd_cancle" type="button"><spring:theme code="register.cancel"/>
                        </button>
                        <button class="btn btn-primary btn_save" type="submit"><spring:theme code="text.button.save"/>
                        </button>
                    </div>
                </div>
            </div>
            </form:form>
        </div>
    </div>
</template:defaultPage>
