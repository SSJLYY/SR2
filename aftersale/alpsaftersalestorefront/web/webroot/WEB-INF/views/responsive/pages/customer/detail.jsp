<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="detail" tagdir="/WEB-INF/tags/responsive/customer/detail"%>

<template:defaultPage pageTitle="${pageTitle}">
	<div class="col-md-12 col-sm-12">
		<div class="row">
			<div class="tabs-container">
				<detail:navigation choseli="base-tab" />
				<detail:baseinfo/>
			</div>
		</div>
	</div>
</template:defaultPage>
