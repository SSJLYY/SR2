ACC.global = {
    _autoload: [
        "bindDetailTabInfo",
        "bindShowProductConfigurationSubInfo"
    ],

    bindDetailTabInfo: function () {
        $('form').each(function(index,domEle){
            $('.yd_submit', domEle).on("click", function () {
                domEle.submit();
            });
        })

        $('#chose-tab-box li').on("click", function () {
            if (!$(this).hasClass("active")) {
                var className = $(this).attr('data-box-id');
                $(this).attr("style","");
                $('#chose-tab-info-box').children().removeClass('active');
                $('#chose-tab-info-box .' + className).addClass('active');
            }
        })
    },

    bindShowProductConfigurationSubInfo: function(){
        $(".configuration-product-box .main-row").click(function(){
            var actionUrl = $(this).parents('table').attr('request-url-data');
            var productCode = $(this).attr('product-code-data');
            var subdescriptionBox = $(this).next('tr');
            $('.subdescription-box').hide();
            $.ajax({
                url: actionUrl+productCode,
                type: "GET",
                contentType: 'application/json',
                success: function (data) {
                    $('td',subdescriptionBox).html(data);
                    subdescriptionBox.slideDown();
                }
            });
        })
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
}


$(function(){
    $(".Ry_flex .but_objective").click(function(){
        $(this).css({
            "backgroundColor":"#C71B1E",
            "color":"#fff",
            "borderColor":"#C71B1E"
        }).addClass("seleted").siblings().css({
            "backgroundColor":"#fff",
            "color":"#999999",
            "borderColor":"#E4E4E4"
        }).removeClass("seleted")
    })

    $(".special-selection-box .option").on("click",function(){
        $("input[type='hidden']",$(this).parent()).val($(this).attr('data-value'));
    })

    //菜单点击
    $(".J_menuItem").on('click',function(){
        var url = $(this).attr('href');
        $("#J_iframe").attr('src',url);
        return false;
    });

    $(".dismanting_click").click(function(){
        $(this).siblings().children(".dismanting_none").slideToggle("slow");
    });

    $(".nav_button").click(function(){
        $(this).children("input").toggleClass("but_objective3");
        $(this).children("img").toggle();
    });

    $(".customnav-btn").click(function(){
        $(this).parent().children("input.customnav-value-box").attr("checked", true);
    });

    $.each(ACC.global._autoload,function(i, item){
        ACC.global[item]();
    })
});
