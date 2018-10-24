ACC.global = {

    _autoload: [
        ["passwordStrength", $('.password-strength').length > 0],
        "bindToggleOffcanvas",
        "bindToggleXsSearch",
        "bindHoverIntentMainNavigation",
        "initImager",
        "backToHome",
        "bindVehicleProductInfoSelect",
        "bindChoseVehicleDetailTab",
        "bindRelatedTab",
        "bindRelatedForm",
        "bindInsuranceTab",
        "bindInsuranceForm",
        "bindRelatedDelete",
        "bindInsuranceDelete",
        "bindSearchCustomerbox",
        "bindShowSearchCustomer",
        "buildRegionBox",

        "showMesBox",
        "hidenMesBox"


    ],

    buildRegionBox:function(){
        var provinceDataList = {}
        /*if(regionList) provinceDataList =  regionList;
        $("#register-customer-province").html("<option value=\"\"></option>");
        $.each(provinceDataList,function(i,provinceData){
            $("#register-customer-province").append("<option value=\""+provinceData.id+"\">"+provinceData.label+"</option>");
        })
        $("#register-customer-province").on("change",function(){
            $.each(provinceDataList,function(i,provinceData){
                if($("#register-customer-province").val() == provinceData.id){
                    $("#register-customer-city").html("<option value=\"\"></option>");
                    $.each(provinceData.citys,function(i,citydata){
                        $("#register-customer-city").append("<option value=\""+citydata.id+"\">"+citydata.label+"</option>");
                    });
                }
            });
        })
        $("#register-customer-city").on("change",function(){
            $.each(provinceDataList,function(i,provinceData){
                if($("#register-customer-province").val() == provinceData.id){
                    $.each(provinceData.citys,function(i,citydata){
                        if($("#register-customer-city").val() == citydata.id){
                            $("#register-customer-district").html("<option value=\"\"></option>");
                            $.each(citydata.districts,function(i,districtsdata){
                                $("#register-customer-district").append("<option value=\""+districtsdata.id+"\">"+districtsdata.label+"</option>");
                            });
                        }
                    });
                }
            });
        })*/
    },

    bindShowSearchCustomer: function(){
        $('#vehicle-detail-base-name').parent().find('.attach-icon').on('click',function(){
            $('.customer-search-popup-box').attr('data-source-name-box','#vehicle-detail-base-name');
            $('.customer-search-popup-box').attr('data-source-id-box','#vehicle-detail-base-id');
            $('.customer-search-popup-box').removeClass('hide');
        })
        $('#register-vehicle2user-name').parent().find('.attach-icon').on('click',function(){
            $('.customer-search-popup-box').attr('data-source-name-box','#register-vehicle2user-name');
            $('.customer-search-popup-box').attr('data-source-id-box','#register-vehicle2user-uid');
            $('.customer-search-popup-box').removeClass('hide');
        })
    },

    bindSearchCustomerbox: function(){
        $('.close-search-box').on('click',function(){
            $('.customer-search-popup-box').addClass('hide');
        })
        $('#customer-search-form button').on('click', function(){
            var values = ACC.global.getFormValue("#customer-search-form");
            var actionUrl = $('#customer-search-form').attr('action');
            $.ajax({
                url:actionUrl,
                type:"POST",
                contentType: 'application/json',
                dataType:"json",
                data: JSON.stringify(values),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    if(data.success==true){
                        var customerList = data.customerList;
                        if(customerList) {
                            $('.customer-search-popup-box table tbody').html("");
                            $.each(customerList, function (i, customer) {
                                $('.customer-search-popup-box table tbody').append("" +
                                    "<tr data-uid=\""+customer.uid+"\" data-name=\""+customer.name+"\">" +
                                    "<td>"+customer.name+"</td>\n" +
                                    "<td>"+customer.name+"</td>\n" +
                                    "<td>"+customer.mobileNumber+"</td>" +
                                    "</tr>");
                            });
                            $('.customer-search-popup-box table tbody tr').on('click',function(){
                                $('.customer-search-popup-box table tbody tr').removeClass('chose-td');
                                $(this).addClass('chose-td');
                            })
                            $('.customer-search-popup-box .chose-btn-box button').on("click",function(){
                                var choseElement = $('.customer-search-popup-box table tr.chose-td');
                                var popBox = $('.customer-search-popup-box');
                                if(choseElement.length > 0){
                                    var nameBoxName = popBox.attr('data-source-name-box');
                                    var idBoxName = popBox.attr('data-source-id-box');
                                    $(''+nameBoxName).val(choseElement.attr('data-name'));
                                    $(''+idBoxName).val(choseElement.attr('data-uid'));
                                    popBox.addClass('hide');
                                    $('.customer-search-popup-box table tbody').html("");
                                }else{
                                    alert("请选择");
                                }
                            })
                        }
                    }
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("删除失败")
                }
            })
            return false;
        })
    },

    bindInsuranceDelete: function(){
        $('.delete-vehicleinsurance').on('click', function(){
            var pks = {'pk':[]}, actionUrl = $('.delete-vehicleinsurance').attr('delete-url');
            $('.insurance-tab-info input[name="pk"]').each(function(){
                if($(this).is(':checked')){
                    pks['pk'].push($(this).val());
                }
            })
            if(pks['pk'].length<=0){
                return false;
            }
            $.ajax({
                url:actionUrl,
                type:"POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType:"json",
                data: pks,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    location.reload();
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("删除失败")
                }
            })
            return false;
        })
    },

    bindRelatedDelete: function(){
        $('.delete-vehicle2user').on('click', function(){
            var pks = {'pk':[]}, actionUrl = $('.delete-vehicle2user').attr('delete-url');
            $('.related-tab-info input[name="pk"]').each(function(){
                if($(this).is(':checked')){
                    pks['pk'].push($(this).val());
                }
            })
            if(pks['pk'].length<=0){
                return false;
            }
            $.ajax({
                url:actionUrl,
                type:"POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType:"json",
                data: pks,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    location.reload();
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("删除失败")
                }
            })
            return false;
        })
    },

    bindInsuranceForm: function(){
        $("#vehicle-insurance-form").submit(function(){
            var values = ACC.global.getFormValue("#vehicle-insurance-form");
            var actionUrl = $(this).attr("action");
            $.ajax({
                url:actionUrl,
                type:"POST",
                contentType: 'application/json',
                dataType:"json",
                data: JSON.stringify(values),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    console.log("opportunity update result:"+JSON.stringify(data));
                    location.reload();
                },
                error: function(jqXHR, textStatus, errorThrown){
                }
            })
            return false;
        })
    },

    bindRelatedForm: function(){
        $("#vehicle2User-form").submit(function(){
            var values = ACC.global.getFormValue("#vehicle2User-form");
            var actionUrl = $(this).attr("action");
            $.ajax({
                url:actionUrl,
                type:"POST",
                contentType: 'application/json',
                dataType:"json",
                data: JSON.stringify(values),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    console.log("opportunity update result:"+JSON.stringify(data));
                    location.reload();
                },
                error: function(jqXHR, textStatus, errorThrown){
                }
            })
            return false;
        })
    },

    bindInsuranceTab: function(){
        $('.add-vehicleinsurance').on("click",function(){
            $('.vehicleinsurance-popup-box').removeClass('hide');
        })
        $('.vehicleinsurance-popup-box .close-btn').on("click",function(){
            $('.vehicleinsurance-popup-box').addClass('hide');
        })
    },

    bindRelatedTab: function(){
        $('.add-vehicle2user').on("click",function(){
            $('.vehicle2User-popup-box').removeClass('hide');
        })
        $('.vehicle2User-popup-box .close-btn').on("click",function(){
            $('.vehicle2User-popup-box').addClass('hide');
        })
    },

    bindChoseVehicleDetailTab:function(){
        $('#vehicle-detail-tab li').on("click",function(){
            if(!$(this).hasClass("chose")) {
                var className = $(this).attr('class').replace(/^\s+|\s+$/g,"");
                $('#vehicle-detail-tab li').removeClass("chose");
                $(this).addClass("chose");
                $('#vehicle-detail-tab-info div.tab-info').addClass('hide');
                $('#vehicle-detail-tab-info .'+className + '-info').removeClass('hide');
                console.log('#vehicle-detail-tab-info .'+className + '-info');
            }
        })
    },

    bindVehicleProductInfoSelect: function(){
        $('#register-vehicle-vehicleBrand').on("change", function () {
            var value = $(this).val();
            $("#register-vehicle-vehicleCategory").html("<option value=\"\" disabled=\"disabled\" selected=\"selected\"></option>");
            $.each(categoryList, function(i, item){
                if(item.code == value){
                    $.each(item.subList, function(i, subitem) {
                        var options = "<option value=\"" + subitem.code + "\">" + subitem.name + "</option>";
                        $("#register-vehicle-vehicleCategory").append(options);
                    })
                }
            })
        });
        $('#register-vehicle-vehicleCategory').on("change", function () {
            var value = $(this).val();
            $("#register-vehicle-vehicle").html("<option value=\"\" disabled=\"disabled\" selected=\"selected\"></option>");
            $.each(productList, function(i, item){
                if(item.categoryCode == value){
                    var options = "<option value=\"" + item.code + "\">" + item.name + "</option>";
                    $("#register-vehicle-vehicle").append(options);
                }
            })
        });
        $('#register-vehicle-vehicle').on("change", function () {
            var value = $(this).val();
            $("#register-vehicle-color").html("<option value=\"\" disabled=\"disabled\" selected=\"selected\"></option>");
            $.each(productList, function(i, item){
                if(item.code == value){
                    $.each(item.subProduct, function(i, subitem) {
                        var options = "<option value=\"" + subitem.color + "\">" + subitem.color + "</option>";
                        $("#register-vehicle-color").append(options);
                    })
                }
            })
        });
    },

    getFormValue: function (idKey){
        var params = $(idKey).serializeArray();
        var values = {};
        for (var item in params) {
            var fieldName = params[item].name;
            if(fieldName == 'CSRFToken'){
                continue;
            }
            if(params[item].value==null || params[item].value == ""){
                continue;
            }
            if(fieldName.indexOf("[]")>0){
                origFieldName = fieldName.replace("[]","");
                origFieldName = origFieldName.split(".");
                fieldName = origFieldName[0];
                paramsKey = origFieldName[1]
                paramsValue = params[item].value;
                if(origFieldName.length>1){
                    paramsValue = {};
                    paramsValue[paramsKey] = params[item].value;
                }
                if(values[fieldName]){
                    paramsValue[paramsKey] = params[item].value;
                    values[fieldName].push(paramsValue);
                }else{
                    values[fieldName] = new Array(paramsValue);
                }
                continue;
            }

            if(fieldName.indexOf("[]")<0 && fieldName.indexOf("[")>0 && fieldName.indexOf("]")>0){
                origFieldName = fieldName;
                origFieldSet = fieldName.split("[");
                fieldName = origFieldSet[0];
                origFieldSet = origFieldSet[1].split(".");
                if(origFieldSet.length>1){
                    paramsKey = origFieldSet[1];
                    paramsOrder = origFieldSet[0].replace("]", "");
                    if(values[fieldName] && values[fieldName][paramsOrder]){
                        //console.log("");
                        values[fieldName][paramsOrder][paramsKey] = params[item].value;
                    }else{
                        var origFieldType = $("input[name='"+origFieldName+"']");
                        if(origFieldType.attr("type")=="checkbox" || origFieldType.attr("type")=="radio"){
                            if(origFieldType.is(':checked')){
                                if(!values[fieldName]) values[fieldName] = {};
                                if(!values[fieldName][paramsOrder]) values[fieldName][paramsOrder] = {};
                                values[fieldName][paramsOrder][paramsKey] = params[item].value;
                            }
                        }
                    }
                }
                continue;
            }

            if(fieldName.indexOf(".")>0){
                origFieldName = fieldName.split(".");
                fieldName = origFieldName[0];
                paramsKey = origFieldName[1];
                paramsValue = params[item].value;
                newparamsValue = {};
                if(values[fieldName]){
                    newparamsValue = values[fieldName];
                }
                newparamsValue[paramsKey] = paramsValue;
                values[fieldName] = newparamsValue;
                continue;
            }

            if(values[fieldName]){
                if($("input[name='"+fieldName+"']").attr("type")=="radio"){
                    if($("input[name='"+fieldName+"']").attr("checked")=="checked") values[fieldName] = params[item].value;
                }else{
                    if(typeof(values[fieldName])=="string"){
                        values[fieldName] = new Array(values[fieldName],params[item].value);
                    }else{
                        values[fieldName].push(params[item].value);
                    }
                }
            }else{
                values[fieldName] = params[item].value;
            }
        }
        for (var valuekey in values) {
            if(values[valuekey] && typeof(values[valuekey])!="string" && values[valuekey][0]){
                var origFieldType = $("input[name='"+valuekey+"[0].code']");
                var newValue = new Array();
                if(origFieldType.length>0 && (origFieldType.attr("type")=="checkbox" || origFieldType.attr("type")=="radio")){
                    for (var subValueKey in values[valuekey]) {
                        newValue.push(values[valuekey][subValueKey]);
                    }
                    values[valuekey]=newValue;
                }
            }
        }
        return values;
    },

    passwordStrength: function () {
        $('.password-strength').pstrength({
            verdicts: [ACC.pwdStrengthTooShortPwd,
                ACC.pwdStrengthVeryWeak,
                ACC.pwdStrengthWeak,
                ACC.pwdStrengthMedium,
                ACC.pwdStrengthStrong,
                ACC.pwdStrengthVeryStrong],
            minCharText: ACC.pwdStrengthMinCharText
        });
    },

    bindToggleOffcanvas: function () {
        $(document).on("click", ".js-toggle-sm-navigation", function () {
            ACC.global.toggleClassState($("main"), "offcanvas");
            ACC.global.toggleClassState($("html"), "offcanvas");
            ACC.global.toggleClassState($("body"), "offcanvas");
            ACC.global.resetXsSearch();
        });
    },

    bindToggleXsSearch: function () {
        $(document).on("click", ".js-toggle-xs-search", function () {
            ACC.global.toggleClassState($(".site-search"), "active");
            ACC.global.toggleClassState($(".js-mainHeader .navigation--middle"), "search-open");
        });
    },

    resetXsSearch: function () {
        $('.site-search').removeClass('active');
        $(".js-mainHeader .navigation--middle").removeClass("search-open");
    },

    toggleClassState: function ($e, c) {
        $e.hasClass(c) ? $e.removeClass(c) : $e.addClass(c);
        return $e.hasClass(c);
    },

    bindHoverIntentMainNavigation: function () {

        enquire.register("screen and (min-width:" + screenMdMin + ")", {

            match: function () {
                // on screens larger or equal screenMdMin (1024px) calculate position for .sub-navigation
                $(".js-enquire-has-sub").hoverIntent(function () {
                    var $this = $(this),
                        itemWidth = $this.width();
                    var $subNav = $this.find('.js_sub__navigation'),
                        subNavWidth = $subNav.outerWidth();
                    var $mainNav = $('.js_navigation--bottom'),
                        mainNavWidth = $mainNav.width();

                    console.log($subNav);

                    // get the left position for sub-navigation to be centered under each <li>
                    var leftPos = $this.position().left + itemWidth / 2 - subNavWidth / 2;
                    // get the top position for sub-navigation. this is usually the height of the <li> unless there is more than one row of <li>
                    var topPos = $this.position().top;

                    $subNav.css({
                        "left": itemWidth,
                        "top": topPos,
                        "right": "auto",
                        "width": itemWidth
                    });
                    $this.addClass("show-sub");
                }, function () {
                    $(this).removeClass("show-sub")
                });
            },

            unmatch: function () {
                // on screens smaller than screenMdMin (1024px) remove inline styles from .sub-navigation and remove hoverIntent
                $(".js_sub__navigation").removeAttr("style");
                $(".js-enquire-has-sub").hoverIntent(function () {
                    // unbinding hover
                });
            }

        });
    },

    initImager: function (elems) {
        elems = elems || '.js-responsive-image';
        this.imgr = new Imager(elems);
    },

    reprocessImages: function (elems) {
        elems = elems || '.js-responsive-image';
        if (this.imgr == undefined) {
            this.initImager(elems);
        } else {
            this.imgr.checkImagesNeedReplacing($(elems));
        }
    },

    // usage: ACC.global.addGoogleMapsApi("callback function"); // callback function name like "ACC.global.myfunction"
    addGoogleMapsApi: function (callback) {
        if (callback != undefined && $(".js-googleMapsApi").length == 0) {
            $('head').append('<script class="js-googleMapsApi" type="text/javascript" src="//maps.googleapis.com/maps/api/js?key=' + ACC.config.googleApiKey + '&sensor=false&callback=' + callback + '"></script>');
        } else if (callback != undefined) {
            eval(callback + "()");
        }
    },

    backToHome: function () {
        $(".backToHome").on("click", function () {
            var sUrl = ACC.config.contextPath;
            window.location = sUrl;
        });
    },


    /*shaun
   detailForm.jsp
   */
    showMesBox: function () {
        $(".showMesBox").on("click", function () {
            document.getElementById('inputbox').style.display="block";
        });
    },

    hidenMesBox: function () {
        $(".hidenMesBox").on("click", function () {
            document.getElementById('inputbox').style.display="none";
        });

        return false;
    }






};
