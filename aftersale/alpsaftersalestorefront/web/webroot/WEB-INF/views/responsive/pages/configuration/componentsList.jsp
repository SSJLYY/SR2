<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>


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
                        <c:url var="componentsUrl" value="/configuration/components"/>
                        <form:form action="${componentsUrl}" method="post" commandName="productSearchRequest">
                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <formElement:formInputBox idKey="components.name" labelKey="text.aftersales.products.components.name" path="name" inputCSS="form-control" placeholder="text.aftersales.searchbox.components.name" mandatory="true" notIncludeWrap="true" />
                            </div>
                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <formElement:formInputBox idKey="components.code" labelKey="text.aftersales.products.components.code" path="code" inputCSS="form-control" placeholder="text.aftersales.searchbox.components.code" mandatory="true" notIncludeWrap="true" />
                            </div>
                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <formElement:formInputBox idKey="components.materialType" labelKey="text.aftersales.products.components.materialType" path="materialType" placeholder="text.aftersales.searchbox.components.materialType" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
                            </div>
                            <div class="col-md-3 col-sm-4 Ry_flex form-group">
                                <formElement:formInputBox idKey="components.specificationModel" labelKey="text.aftersales.products.components.specificationModel" path="specificationModel" placeholder="text.aftersales.searchbox.components.specificationModel" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
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
                        <p><spring:theme code="text.aftersales.products.components.list" /></p>
                    </div>
                    <div class="table-responsive pick_car_list">
                        <c:url var="requestUrl" value="/configuration/component/stock?productCode="/>
                        <table class="table  pick_carTable2 pick_carTable_even configuration-product-box" request-url-data="${requestUrl}">
                            <thead>
                            <tr>
                                <th><spring:theme code="text.aftersales.title.sequence" /></th>
                                <th><spring:theme code="text.aftersales.products.components.code" /></th>
                                <th><spring:theme code="text.aftersales.products.components.name" /></th>
                                <th><spring:theme code="text.aftersales.products.components.materialGroup" /></th>
                                <th><spring:theme code="text.aftersales.products.components.materialType" /></th>
                                <th><spring:theme code="text.aftersales.products.components.specificationModel" /></th>
                                <th><spring:theme code="text.aftersales.products.components.unitPrice" /></th>
                                <th><spring:theme code="text.aftersales.products.currency" /></th>
                                <th><spring:theme code="text.aftersales.products.components.suggestedRetailPrice" /></th>
                                <th><spring:theme code="text.aftersales.products.currency" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${productList}" var="productList" varStatus="rows">
                                <div id="id-${rows.index}" class="pull-left">
                                    <tr class="main-row" product-code-data="${productList.code}">
                                        <td>${rows.index+1}&nbsp;</td>
                                        <td><a href="#">${fn:escapeXml(productList.code)}</a></td>
                                        <td>${fn:escapeXml(productList.name)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.materialGroup)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.materialType)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.specificationModel)}&nbsp;</td>
                                        <td>${fn:escapeXml(productList.price)}&nbsp;</td>
                                       <td>${fn:escapeXml(productList.currency)}&nbsp;</td>
                                       <td>${fn:escapeXml(productList.suggestedRetailPrice)}&nbsp;</td>
                                       <td>${fn:escapeXml(productList.currency)}&nbsp;</td>
                                    </tr>
                                    <tr class="dis_tr subdescription-box" style="display: none;">
                                        <td colspan="10" class="dis_td">
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
