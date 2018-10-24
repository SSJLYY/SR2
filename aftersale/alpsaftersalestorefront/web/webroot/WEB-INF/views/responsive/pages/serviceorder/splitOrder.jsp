<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="splitOrder" tagdir="/WEB-INF/tags/responsive/serviceorder/splitOrder"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>

<template:defaultPage pageTitle="${pageTitle}">
	<splitOrder:firstSplitPage/>
	<splitOrder:alreadySplitPreviewList/>
	<div class="split-preview-template hide">
		<splitOrder:splitPreviewTemplate/>
	</div>
	<customer:choseCustomerPopup/>
</template:defaultPage>
