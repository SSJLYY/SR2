<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<form:form id="product-search-form" method="get" commandName="productSearchRequest" action="${actionurl}">
	<input type="text" class="request-product-searchText listQuery pull-right" placeholder="<spring:theme code="add.entry.popup.search.default.text"/>"/>
	<button type="submit" class="btn btn-primary" style="height:16px;margin-left:-2px;display: none;"><spring:theme code="search.box.search.btn" /></button>
</form:form>
