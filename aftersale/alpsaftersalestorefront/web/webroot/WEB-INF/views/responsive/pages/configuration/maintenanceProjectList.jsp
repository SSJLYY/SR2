<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<template:defaultPage pageTitle="${pageTitle}">
    <%--右侧--%>
    <div class="wrapper wrapper-content animated fadeInRight wrapper_bottom">
        <div class="row">
            <div class="col-md-12 col-sm-12">
                <div class="col-md-12 col-sm-12 ls_nav">
                    <div class=" ls_nav_nav ls_nav_bottom">
                        <p><spring:theme code="text.aftersales.title.queryconditions" /></p>
                    </div>

                        <%--搜素表单--%>
                    <div class="row">
                        <form:form action="/alpsaftersalestorefront/configuration/maintenanceProject?categoryCode=maintenanceProject&searchText=" method="post" commandName="productSearchRequest">
                            <div class="col-md-4 col-sm-4 Ry_flex form-group">
                                <label  for="name"><spring:theme code="text.aftersales.products.maintenanceproject.name" /></label>
                                <input id="name" name="name" type="text" placeholder=<spring:theme code="text.aftersales.searchbox.maintenanceproject.name" />  class="form-control" value="${name}">
                            </div>
                            <div class="col-md-4 col-sm-4 Ry_flex form-group">
                                <label for="artificialMainType"><spring:theme code="text.aftersales.products.maintenanceproject.artificialMainType" /></label>
                                <input id="artificialMainType" name="artificialMainType"  type="text" placeholder=<spring:theme code="text.aftersales.searchbox.maintenanceproject.artificialMainType" /> class="form-control" value="${artificialMainType}">
                            </div>
                            <div class="col-md-4 col-sm-4 Ry_flex form-group">
                                <label for="maintenanceItemCode"><spring:theme code="text.aftersales.products.maintenanceproject.maintenanceItemCode" /></label>
                                <input id="maintenanceItemCode" name="maintenanceItemCode"  type="text" placeholder=<spring:theme code="text.aftersales.searchbox.maintenanceproject.maintenanceItemCode" /> class="form-control" value="${maintenanceItemCode}">
                            </div>
                            <div class="col-md-3 yd_flex form-group col-sm-4 pull-right" style="flex-direction:row-reverse">
                                <button type="submit" class="btn btn-primary yd_submit" ><spring:theme code="search.button" /></button>
                            </div>
                        </form:form>
                    </div>
                </div>



                    <%--右表格--%>
                <div class="col-md-12 col-sm-12 pick_car">
                    <div class="ls_nav_nav">
                        <p><spring:theme code="text.aftersales.products.maintenanceproject.list" /></p>
                    </div>
                    <div class="table-responsive pick_car_list">
                        <c:url var="requestUrl" value="/configuration/maintenanceProject/configuration?productCode="/>
                        <table class="table  pick_carTable2 pick_carTable_even configuration-product-box" request-url-data="${requestUrl}">
                            <thead>
                            <tr>
                                <th><spring:theme code="text.aftersales.title.sequence" /></th>
                                <th><spring:theme code="text.aftersales.products.maintenanceproject.code" /></th>
                                <th><spring:theme code="text.aftersales.products.maintenanceproject.name" /></th>
                                <th><spring:theme code="text.aftersales.products.maintenanceproject.manualDirectoryIdentifier" /></th>
                                <th><spring:theme code="text.aftersales.products.maintenanceproject.maintenanceItemCode" /></th>
                                <th><spring:theme code="text.aftersales.products.maintenanceproject.pricingReferenceMaterial" /></th>
                                <th><spring:theme code="text.aftersales.products.maintenanceproject.unitHourSalesPrice" /></th>
                                <th><spring:theme code="text.aftersales.products.currency" /></th>
                                <th><spring:theme code="text.aftersales.products.maintenanceproject.recentlyModifiedPerson" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${productList}" var="productList" varStatus="rows">
                                <div id="id-${rows.index}" class="pull-left">
                                    <tr class="main-row" product-code-data="${productList.code}">
                                        <td>${rows.index+1}&nbsp;</td>
                                        <td><a href="#">${productList.code}</a></td>
                                        <td>${fn:escapeXml(productList.name)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.manualDirectoryIdentifier)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.maintenanceItemCode)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.pricingReferenceMaterial)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.price)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.currency)}&nbsp;</td>
                                        <td><%--${fn:escapeXml(productList.recentlyModifiedPerson)}--%>&nbsp;</td>
                                    </tr>
                                    <tr class="dis_tr subdescription-box" style="display: none;">
                                        <td colspan="9" class="dis_td">
                                        </td>
                                    </tr>
                                </div>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template:defaultPage>
