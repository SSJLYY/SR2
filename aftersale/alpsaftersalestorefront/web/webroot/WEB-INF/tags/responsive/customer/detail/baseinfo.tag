<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>
<%@ taglib prefix="customer-detail" tagdir="/WEB-INF/tags/responsive/customer/detail"%>
<c:url var="action" value="/customer/createOrUpdate"/>
<div class="col-md-12 col-sm-12">
    <div class="tab-content">
    <script type="text/javascript">
        var regionList = ${regionList};
    </script>
        <div class="row  ls_nav">
            <form:form id="customerInfo-form" method="post" commandName="customerInfoData" action="${action}">
                <div class="ls_nav_nav2 ls_nav_left" style="border-bottom:1px solid #ddd;padding-bottom:20px;">
                    <p style="padding-left:15px;">
                        <span><spring:theme code='register.customer.uid'/></span><span>  ${customerInfoData.uid}</span>
                        <input type="hidden" value="${customerInfoData.uid}" name="uid"/>
                    </p>
                    <div class="work_edit">
                        <p class="pull-right" style="border-left: none;"><a href="#" class="yd_submit"><spring:theme code='detail.base.tab.label.save'/></a></p>
                        <p class="pull-right"><a href="#" class="yd_change"><spring:theme code='detail.base.tab.label.update'/></a></p>
                    </div>
                </div>
                <div class="row row_left row_right showbox">
                    <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
                        <label for="" class="word_spac"><spring:theme code="customer.name"/> </label>
                        <p>${customerInfoData.name}</p>
                    </div>
                    <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
                        <label for=""><spring:theme code="customer.mobileNumber"/></label>
                        <p>${customerInfoData.mobileNumber}</p>
                    </div>
                    <div class="col-md-4 col-sm-4  Ry_flex form-group com_first col_p2">
                        <label for=""><spring:theme code="customer.creator"/></label>
                        <p>${customerInfoData.creator}</p>
                    </div>
                </div>
                <div class="row row_left row_right showbox">
                    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                        <label for="" class="word_spac"><spring:theme code="customer.type"/> </label>
                        <p>${customerInfoData.customerType}</p>
                    </div>
                    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                        <label for=""><spring:theme code="customer.attribute"/></label>
                        <p>${customerInfoData.attribute}</p>
                    </div>
                    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                        <label for=""><spring:theme code="customer.role"/></label>
                        <p>${customerInfoData.role}</p>
                    </div>
                </div>
                <div class="row row_left row_right showbox">
                    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                        <label for="" class="word_spac"><spring:theme code="customer.identityType"/> </label>
                        <p>${customerInfoData.identityType}</p>
                    </div>
                    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                        <label for=""><spring:theme code="customer.identityNumber"/></label>
                        <p>${customerInfoData.identityNumber}</p>
                    </div>
                    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                        <label for=""><spring:theme code="customer.otherContactNumber"/></label>
                        <p>${customerInfoData.otherContactNumber}</p>
                    </div>
                </div>
                <div class="row row_left row_right showbox">
                    <div class="col-md-4 col-sm-4  Ry_flex form-group col_p2">
                        <label for="" class="word_spac"><spring:theme code="register.customer.address"/> </label>
                        <p>${customerInfoData.province} ${customerInfoData.city} ${customerInfoData.districtCode}</p>
                    </div>
                </div>

                <customer-detail:changebox/>
            </form:form>
        </div>
    </div>
</div>
