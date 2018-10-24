<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>

<template:defaultPage pageTitle="${pageTitle}">
	<c:url value="/customer/createOrUpdate" var="registerActionUrl" />
	<customer:register actionNameKey="register.submit"
					   action="${registerActionUrl}"/>
</template:defaultPage>
