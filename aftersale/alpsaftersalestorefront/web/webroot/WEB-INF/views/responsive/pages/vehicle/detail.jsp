<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="detail" tagdir="/WEB-INF/tags/responsive/vehicle/detail"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/responsive/customer"%>

<template:defaultPage pageTitle="${pageTitle}">
	<div class="col-md-12 col-sm-12">
	<div class="row">
		<div class="tabs-container">
			<ul class="nav nav-tabs" id="chose-tab-box">
				<li class="active chose" data-box-id="base-tab-info">
					<a data-toggle="tab" href="#" style="font-size:14px;border-left:none;">
						<spring:theme code="detail.base.tab.name" /></a>
				</li>
				<li class="" data-box-id="related-tab-info">
					<a data-toggle="tab" href="#" style="font-size:14px;">
						<spring:theme code="detail.related.tab.name" /></a>
				</li>
				<li class="" data-box-id="insurance-tab-info">
					<a data-toggle="tab" href="#" style="font-size:14px;">
						<spring:theme code="vehicle.detail.insurance.tab.name" /></a>
				</li>
				<li class="" data-box-id="maintain-tab-info">
					<a data-toggle="tab" href="#" style="font-size:14px;">
						<spring:theme code="vehicle.detail.maintain.tab.name" /></a>
				</li>
				<li class="" data-box-id="increase-tab-info">
					<a data-toggle="tab" href="#" style="font-size:14px;">
						<spring:theme code="vehicle.detail.increase.tab.name" /></a>
				</li>
			</ul>
			<div class="col-md-12 col-sm-12">
				<div class="tab-content" id="chose-tab-info-box">
					<div class="base-tab-info tab-pane active">
						<detail:baseinfo />
					</div>
					<div class="related-tab-info tab-pane">
						<detail:related />
					</div>
					<div class="insurance-tab-info tab-pane">
						<div class="row ls_nav">
							<detail:insurance />
						</div>
					</div>
					<div class="maintain-tab-info tab-pane">
						<div class="row ls_nav">
							<detail:maintain />
						</div>
					</div>
					<div class="increase-tab-info tab-pane">
						<div class="row ls_nav">
							<detail:increase />
						</div>
					</div>
					<customer:choseCustomerPopup/>
				</div>
			</div>
		</div>
	</div>
	</div>
</template:defaultPage>
