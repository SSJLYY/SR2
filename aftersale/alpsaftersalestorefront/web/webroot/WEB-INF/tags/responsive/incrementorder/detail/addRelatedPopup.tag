<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ attribute name="dontUsejson" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<div class="vehicle2User-popup-box hide">
    <div class="headline">
        <spring:theme code="register.new.vehicle2user" />
    </div>
    <div class="content-box">
        <form:form id="vehicle2User-form" data-dont-usejson="${dontUsejson}" method="post" commandName="relatedRoleData" action="${action}">
            <div class="modal-dialog-box">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-8 col-sm-8 Ry_flex form-group col_p">
                            <formElement:formSelectBox idKey="register.vehicle2user.userType"
                                               labelKey="register.vehicle2user.userType"
                                               path="userType"
                                               labelCSS="row_label"
                                               mandatory="true"
                                               skipBlank="false"
                                               skipBlankMessageKey=""
                                               items="${service2RoleTypeList}"
                                               itemValue="id"
                                               itemLabel="label"
                                               selectedValue=""
                                               notIncludeWrap="true" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 col-sm-8 Ry_flex form-group col_p register-vehicle2user-name-box">
                            <formElement:formInputBoxAttachIcon idKey="register-vehicle2user-name" labelKey="register.vehicle2user.uid" path="name" labelCSS="row_label" inputCSS="form-control" mandatory="true" notIncludeWrap="true" />
                        </div>
                    </div>
                    <input type="hidden" value="" id="register-vehicle2user-uid" name="uid">
                    <input type="hidden" name="serviceOrderCode" value="${incrementOrderData.code}"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default btn_save">
                        <spring:theme code='register.vehicle2user.close' />
                    </button>
                    <button type="submit" class="btn btn-primary btn_save">
                        <spring:theme code='${actionNameKey}' />
                    </button>
                </div>
            </div>
        </form:form>
    </div>
</div>
