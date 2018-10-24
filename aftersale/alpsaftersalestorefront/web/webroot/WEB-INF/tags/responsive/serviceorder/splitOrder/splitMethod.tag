<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class="row">
    <div class="col-md-6 col-sm-6 form-group  ry_padding_none">
        <div class="work_middle ls_nav">
            <div class="col-md-8 col-sm-8 col-md-offset-2 col-sm-offset-2 dismantlingMethod">
                <label for="" style="text-align:center;width:100%;"><spring:theme code='split.price.type1.box.label'/></label>
                <div class="Ry_flex">
                    <label for="" class="he"><spring:theme code="split.price.type1.label"/></label>
                    <input type="text" disabled="disabled" name="splitByPrice" placeholder="<spring:theme code="split.price.type1.value.placeholder"/>" class="form-control">
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6 col-sm-6 form-group  ry_padding_none">
        <div class="work_middle ls_nav">
            <div class="col-md-8 col-sm-8 col-md-offset-2 col-sm-offset-2 dismantlingMethod">
                <label for="" style="text-align:center;width:100%;"><spring:theme code='split.price.type2.box.label'/></label>
                <div class="Ry_flex">
                    <label for=""><spring:theme code="split.price.type2.label"/></label>
                    <input type="text" disabled="disabled" name="splitByRate" placeholder="<spring:theme code="split.price.type2.value.placeholder"/>" class="form-control">%
                </div>
            </div>
        </div>
    </div>
</div>
