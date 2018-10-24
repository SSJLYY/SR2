<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i>
    </div>
    <div class="sidebar-collapse"  style="padding-top:15px;">
        <div class="navigationMenu" style="margin-bottom: 0px;">
            <span class="nav_menuu"><spring:theme code="header.navigation.menu" /></span><a class="navbar-minimalize " href="#"><i class=""><img src="${themeResourcePath}/images/open.png" alt="" class="ry_nav_menu" style="margin-left:15px;"></i></a>
        </div>

        <div style="display: block;">
        <div class="nav-header">
            <div class="dropdown profile-element">
                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                    <span class="clear">
                        <span class="block m-t-xs">
                            <div class="guwen">
                                <sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')" >
                                    <a class="J_menuItem" href="<c:url value='/login'/>">
                                        <div class="imgg" style="width:86px;height:86px;background-color:#fff;border-radius:50%;">
                                        </div>
                                        <p class="After_name"></p>
                                        <p class="After_position"><spring:theme code="header.link.login" /></p>
                                    </a>
                                </sec:authorize>
                                <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
                                    <c:url var="homepage" value="/"/>
                                    <a class="" href="${homepage}">
                                        <div class="imgg" style="width:86px;height:86px;background-color:#fff;border-radius:50%;"></div>
                                        <p class="After_name"><spring:theme code="header.welcome" arguments="${user.firstName},${user.lastName}" htmlEscape="true" /></p>
                                        <p class="After_position"></p>
                                    </a>
                                </sec:authorize>
                            </div>
                        </span>
                    </span>
                </a>
            </div>
            <div class="logo-element">
                <p class="After_name"  style="text-align:center;"></p>
                <!-- <img src="img/dop.png" alt=""> -->
            </div>
        </div>
        </div>

        <cms:pageSlot position="NavigationBar" var="component">
            <cms:component component="${component}"/>
        </cms:pageSlot>
    </div>
</nav>
