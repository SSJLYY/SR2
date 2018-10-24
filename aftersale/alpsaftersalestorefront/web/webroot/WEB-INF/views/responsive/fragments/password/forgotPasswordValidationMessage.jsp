<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="password" tagdir="/WEB-INF/tags/responsive/password" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>

<template:singleColumnPage pageTitle="${pageTitle}" bodyClass="signin2">
<password:forgotPasswordValidation/>
</template:singleColumnPage>