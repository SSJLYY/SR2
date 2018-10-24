<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="path" required="true" rtexprvalue="true"%>
<%@ attribute name="errorPath" required="false" rtexprvalue="true"%>
<%@ attribute name="notIncludeWrap" required="false" rtexprvalue="true"%>
<%@ attribute name="wrapCSS" required="false" rtexprvalue="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:bind path="${not empty errorPath ? errorPath : path}">
	<c:if test="${!notIncludeWrap}">
		<div class="row row_left">
		<div class="col-md-4 col-sm-5 Ry_flex form-group col_p ${wrapCSS}">
	</c:if>
	<c:choose>
		<c:when test="${not empty status.errorMessages}">
			<div class="has-error">
				<jsp:doBody />
				<div class="help-block">
					<form:errors path="${not empty errorPath ? '' : path}" />
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<jsp:doBody />
		</c:otherwise>
	</c:choose>
	<c:if test="${!notIncludeWrap}">
		</div>
		</div>
	</c:if>
</spring:bind>

