<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="vehicle" tagdir="/WEB-INF/tags/responsive/vehicle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="serviceorder" tagdir="/WEB-INF/tags/responsive/serviceorder"%>
<%@ taglib prefix="detail" tagdir="/WEB-INF/tags/responsive/returnorder/detail"%>

<template:defaultPage pageTitle="${pageTitle}">
	<div class="col-md-12 col-sm-12">
		<div class="row">
			<div class="tabs-container">
				<div class="col-md-12 col-sm-12">
					<div class="tab-content" id="chose-tab-info-box">
						<div class="base-tab-info tab-pane active">
							<div class="row  ls_nav">
								<div class="ls_nav_nav2  ls_nav_left" style="margin: 0 auto;display: block;width: 50%;">
                                    <cms:pageSlot position="Section2" var="feature">
                                        <cms:component component="${feature}" />
                                    </cms:pageSlot>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template:defaultPage>
