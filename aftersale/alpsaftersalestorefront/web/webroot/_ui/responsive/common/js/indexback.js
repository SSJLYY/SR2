ACC.global = {
    _autoload: [
        "bindVehicleProductInfoSelect",
        "bindChoseVehicleDetailTab",
        "bindRelatedTab",
        "bindInsuranceTab",
        "bindRelatedDelete",
        "bindInsuranceDelete",
        "buildRegionBox",
        "buildCustomerInfo",
        "bindSearchCustomerPopup",
        "bindSearchVehiclePopup",
        "bindAddEntryPopup",
        "bindMutiStepServiceOrder",

        "showMesBox",
        "hidenMesBox",
        "showdetailsPag",
        "showclosedReason",

        "nextStepToCreatePickup",
        "selectPurpose",
        "selectpickUp",
        "selectcustomer",

        "hidenMesBox"


    ],
    buildCustomerInfo: function(){
        $('#customerInfo-form .yd_submit').on('click',function(){
            $('#customerInfo-form').submit();
        })
    },

    buildRegionBox: function () {
        if(typeof regionList!="undefined") {
            $("#register-customer-province").html("<option value=\"\"></option>");
            $.each(regionList, function (i, provinceData) {
                var selected = "";
                if (provinceData.id == $("#register-customer-province").attr("data-value")) {
                    selected = "selected=\"selected\"";
                }
                $("#register-customer-province").append("<option " + selected + " value=\"" + provinceData.id + "\">" + provinceData.label + "</option>");
            })
            $("#register-customer-province").on("change", function () {
                $.each(regionList, function (i, provinceData) {
                    if ($("#register-customer-province").val() == provinceData.id) {
                        $("#register-customer-city").html("<option value=\"\"></option>");
                        $.each(provinceData.citys, function (i, citydata) {
                            var selected = "";
                            if (citydata.id == $("#register-customer-city").attr("data-value")) {
                                selected = "selected=\"selected\"";
                            }
                            $("#register-customer-city").append("<option " + selected + " value=\"" + citydata.id + "\">" + citydata.label + "</option>");
                        });
                    }
                });
            })
            $("#register-customer-city").on("change", function () {
                $.each(regionList, function (i, provinceData) {
                    if ($("#register-customer-province").val() == provinceData.id) {
                        $.each(provinceData.citys, function (i, citydata) {
                            if ($("#register-customer-city").val() == citydata.id) {
                                $("#register-customer-district").html("<option value=\"\"></option>");
                                $.each(citydata.districts, function (i, districtsdata) {
                                    var selected = "";
                                    if (districtsdata.id == $("#register-customer-district").attr("data-value")) {
                                        selected = "selected=\"selected\"";
                                    }
                                    $("#register-customer-district").append("<option " + selected + " value=\"" + districtsdata.id + "\">" + districtsdata.label + "</option>");
                                });
                            }
                        });
                    }
                });
            })

            if ($("#register-customer-province").val() != "") {
                $("#register-customer-province").trigger("change");
            }

            if ($("#register-customer-city").val() != "") {
                $("#register-customer-city").trigger("change");
            }
        }
    },

    bindMutiStepServiceOrder: function(){
        $('#service-order-form .next-step').on('click',function(){
            $('.base-info').addClass('hide');
            $('.product-info').removeClass('hide');
        })
        $('#service-order-form .last-step').on('click',function(){
            $('.base-info').removeClass('hide');
            $('.product-info').addClass('hide');
        })
        $('#service-order-form').on('submit',function(){
            if($('.service-order-entry-table tbody tr',this).length>0){
                return true;
            }
            layer.msg("请添加行项目",{
                icon:2,
                btn: ['关闭']
            });
            return false;
        });
    },

    bindAddEntryPopup: function(){
        var entrysequence=1;
        $('.add-entry').on('click', function () {
            var index = layer.open({
                type: 1,
                title: $('.add-entry-popup-wrap-box .headline').html(),
                content: $('.add-entry-popup-wrap-box .content-box').html(),
                area:'600px',
                shadeClose: true,
                success:function (data, indexNumber) {
                    databox = $(data.selector);

                    ACC.global.bindChoseProductPopup(databox)
                    var priceBox = $('#entry-product-price', databox);
                    var quantityBox = $('#entry-quantity', databox);
                    var rateBox = $('#entry-discount-rate', databox);
                    var actualPriceBox = $('#entry-actual-price', databox);

                    $('#entry-product-price', databox).on("change",function(){
                        quantityBox.val(1);
                        var rate = rateBox.val()/100;
                        actualPriceBox.val($(this).val()*rate);
                    })
                    $('#entry-discount-rate', databox).on("change",function(){
                        var price =priceBox.val();
                        var quantity = quantityBox.val();
                        var actualprice = $(this).val()/100*price;
                        actualPriceBox.val(Math.round(actualprice*100)/100);
                    })
                    $('#entry-actual-price', databox).on("change",function(){
                        var price =priceBox.val();
                        var rate = $(this).val()/price*100;
                        rateBox.val(Math.round(rate*10)/10);
                    })
                    $('.btn-confirm', databox).on("click",function(){
                        var serviceTypeHtml = $("#entry-type option:selected",databox).html();
                        var serviceTypeVal = $("#entry-type option:selected",databox).attr("value");
                        var itemCategoryHtml = $("#entry-itemCategory option:selected",databox).html();
                        var itemCategoryVal = $("#entry-itemCategory option:selected",databox).attr("value");
                        var productName = $("#entry-product-name",databox).val();
                        var price = $("#entry-product-price",databox).val();
                        var productVal = $("#entry-product-id",databox).val();
                        var quantity = $("#entry-quantity",databox).val();
                        var rate = $("#entry-discount-rate",databox).val();
                        var actualPrice = $("#entry-actual-price",databox).val();

                        var html = "<tr><td>"+entrysequence+"</td>" +
                            "<td>"+serviceTypeHtml+"<input type='hidden' name='entries["+entrysequence+"].serviceTypeCode' value='"+serviceTypeVal+"'/></td>" +
                            "<td>"+itemCategoryHtml+"<input type='hidden' name='entries["+entrysequence+"].categoryCode' value='"+itemCategoryVal+"'/></td>" +
                            "<td>"+productVal+"<input type='hidden' name='entries["+entrysequence+"].code' value='"+productVal+"'/></td>" +
                            "<td>"+productName+"<input type='hidden' name='entries["+entrysequence+"].name' value='"+productVal+"'/></td>" +
                            "<td>"+quantity+"<input type='hidden' name='entries["+entrysequence+"].quantity' value='"+quantity+"'/></td>" +
                            "<td>"+price+"<input type='hidden' name='entries["+entrysequence+"].price' value='"+price+"'/></td>" +
                            "<td>"+rate+"%<input type='hidden' name='entries["+entrysequence+"].rate' value='"+rate+"'/></td>" +
                            "<td>"+actualPrice+"<input type='hidden' name='entries["+entrysequence+"].actualPrice' value='"+actualPrice+"'/></td>" +
                            "<td>"+(actualPrice*quantity)+"<input type='hidden' name='entries["+entrysequence+"].rowTotal' value='"+(actualPrice*quantity)+"'/></td>" +
                            "<td><a class=\"delete-entry\" href=\"#\">删除</a></td>"+
                            "</tr>";

                        $(".entry-operation-box table tbody").append(html);
                        entrysequence++;

                        $('.delete-entry').on("click",function(){
                            $(this).parent().parent().remove();
                            return false;
                        })

                        layer.close(indexNumber)
                    })

                }
            })
        })
    },

    bindChoseProductPopup(currentBox){
        $('.call-search-product-popup-box .attach-icon', currentBox).on("click", function () {
            var index = layer.load();
            var categoryBoxId = $(this).parent().attr('data-source-category-box');
            var categoryValue = $(categoryBoxId, currentBox).val();
            var actionUrl = $(this).parent().attr('data-request-url');
            $('.product-search-popup-box').attr('data-source-name-box', $(this).parent().attr('data-source-name-box'));
            $('.product-search-popup-box').attr('data-source-id-box', $(this).parent().attr('data-source-id-box'));
            $('.product-search-popup-box').attr('entry-product-price', $(this).parent().attr('entry-product-price'));

            if(categoryValue=="" || categoryValue==null){
                layer.close(index);
                layer.msg('无可用分类', {
                    icon:2,
                    btn: ['关闭']
                });
                return;
            }
            ACC.global.getProductPopupData(actionUrl,
                categoryValue,
                "",
                function(actionUrl, categoryValue, data){
                    layer.close(index);
                    ACC.global.ProcessProductPopup(actionUrl, categoryValue, data, currentBox);
            });
        })
    },

    getProductPopupData(actionUrl, categoryValue, searchTextValue, callback){
        $.ajax({
            url: actionUrl,
            type: "GET",
            dataType: "json",
            data: {categoryCode:categoryValue, searchText:searchTextValue},
            success: function (data) {
                callback(actionUrl, categoryValue, data);
            }
        });
    },

    ProcessProductPopup(actionUrl, categoryValue, productData, parentWindowsBox){
        var index = layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            shadeClose: true,
            area:'600px',
            content: $('.product-search-popup-wrap-box .content-box').html(),
            success:function (data) {
                var popBox = $(data.selector);
                $('.close-btn',popBox).on('click',function(){
                    layer.close(popBox.attr('times'))
                })
                ACC.global.fillProductDataIntoTable(productData, popBox, parentWindowsBox);
                $("#product-search-form", $(data.selector)).on("submit", function(){
                    ACC.global.getProductPopupData(actionUrl,
                        categoryValue,
                        $(".request-product-searchText", $(data.selector)).val(),
                        function(actionUrl, categoryValue, productData){
                            ACC.global.fillProductDataIntoTable(productData, popBox, parentWindowsBox);
                    });
                    return false;
                })
            }
        })
    },

    fillProductDataIntoTable(productData, box, parentWindowsBox){
        $('table tbody', box).html("");
        $.each(productData.productList, function (i, product) {
            $('table tbody', box).append("" +
                "<tr data-uid=\"" + product.code + "\" data-price=\"" + product.price + "\" data-name=\"" + product.name + "\">" +
                "<td>" + (i+1) + "</td>\n" +
                "<td>" + product.code + "</td>\n" +
                "<td>" + product.name + "</td>\n" +
                "<td>" + product.price + "</td>" +
                "</tr>");
        });
        $('table tbody tr', box).on('click', function () {
            $('table tbody tr', box).removeClass('chose-td');
            $('table tbody tr td', box).attr('style',"");
            $(this).addClass('chose-td');
            $("td", this).attr('style', "background-color: #ccc;");
        })
        $('.btn-confirm', box).on("click", function () {
            var choseElement = $('table tr.chose-td', box);
            if (choseElement.length > 0) {
                var attrdiv = $('.modal-content',box);
                var nameBoxName = attrdiv.attr('data-source-name-box');
                var idBoxName = attrdiv.attr('data-source-id-box');
                var productPrice = attrdiv.attr('entry-product-price');
                $('' + nameBoxName, parentWindowsBox).val(choseElement.attr('data-name'));
                $('' + idBoxName, parentWindowsBox).val(choseElement.attr('data-uid'));
                $('' + productPrice, parentWindowsBox).val(choseElement.attr('data-price'));
                $('' + productPrice, parentWindowsBox).trigger("change");
                $('table tbody', box).html("");
                layer.close(box.attr('times'))
            } else {
                layer.msg("请选择",{
                    icon:2,
                    btn: ['关闭']
                });
            }
        })
    },

    bindSearchVehiclePopup: function(){
        $('.call-search-vehicle-popup-box .attach-icon').on('click', function () {
            $('.vehicle-search-popup-box').attr('data-source-name-box', $(this).parent().attr("data-source-name-box"));
            $('.vehicle-search-popup-box').attr('data-source-id-box', $(this).parent().attr("data-source-id-box"));
            ACC.global.popupVehicleSearchBox($(this).parent());
        })
    },

    popupVehicleSearchBox: function(parentWindowsBox){
        var index = layer.open({
            type: 1,
            area:'600px',
            title: $('.vehicle-search-popup-wrap-box .headline').html(),
            content: $('.vehicle-search-popup-wrap-box .content-box').html(),
            scrollbar: true,
            shadeClose: true,
            success:function (data) {
                ACC.global.bindSearchVehiclebox(parentWindowsBox, $(data.selector))
            }
        })
    },

    bindSearchVehiclebox: function (parentWindowsBox, currentWindowsBox) {
        $('.search-btn', currentWindowsBox).on('click', function () {
            var values = ACC.global.getFormValue($("form", currentWindowsBox));
            var actionUrl = $('form', currentWindowsBox).attr('action');
            $.ajax({
                url: actionUrl,
                type: "POST",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(values),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    if (data.success == true) {
                        var vehicleList = data.vehicleList;
                        if (vehicleList) {
                            $('table tbody', currentWindowsBox).html("");
                            $.each(vehicleList, function (i, vehicle) {
                                $('table tbody', currentWindowsBox).append("" +
                                    "<tr data-uid=\"" + vehicle.code + "\" data-name=\"" + vehicle.licensePlateNumber + "\">" +
                                    "<td>" + vehicle.sequenceNumber + "</td>\n" +
                                    "<td>" + vehicle.licensePlateNumber + "</td>\n" +
                                    "<td>" + vehicle.vinNumber + "</td>" +
                                    "<td>" + vehicle.customerName + "</td>" +
                                    "</tr>");
                            });
                            $('table tbody tr', currentWindowsBox).on('click', function () {
                                $('table tbody tr', currentWindowsBox).removeClass('chose-td');
                                $('table tbody tr td', currentWindowsBox).attr('style',"");
                                $(this).addClass('chose-td');
                                $("td", this).attr('style', "background-color: #ccc;");
                            })
                            $('.btn-confirm', currentWindowsBox).on("click", function () {
                                var choseElement = $('table tr.chose-td', currentWindowsBox);
                                var popBox = $('.vehicle-search-popup-box', currentWindowsBox);
                                if (choseElement.length > 0) {
                                    var nameBoxName = popBox.attr('data-source-name-box');
                                    var idBoxName = popBox.attr('data-source-id-box');
                                    $('' + nameBoxName, parentWindowsBox).val(choseElement.attr('data-name'));
                                    $('' + idBoxName, parentWindowsBox).val(choseElement.attr('data-uid'));
                                    $('table tbody', currentWindowsBox).html("");
                                    layer.close(currentWindowsBox.attr('times'))
                                } else {
                                    layer.msg("请选择",{
                                        icon:2,
                                            btn: ['关闭']
                                    });
                                }
                            })
                        }
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    layer.msg("删除失败",{
                        icon:2,
                            btn: ['关闭']
                    });
                }
            })
            return false;
        })
    },

    bindSearchCustomerPopup: function(){
        $('.call-search-customer-popup-box .attach-icon').on('click', function () {
            $('.customer-search-popup-box').attr('data-source-name-box', $(this).parent().attr("data-source-name-box"));
            $('.customer-search-popup-box').attr('data-source-id-box', $(this).parent().attr("data-source-id-box"));
            ACC.global.popupCustomerSearchBox($(this).parent());
        })
    },

    bindSearchCustomerPopupForRelated: function (parentEle) {
        $(".register-vehicle2user-name-box .attach-icon", parentEle).on('click', function () {
            $('.customer-search-popup-box').attr('data-source-name-box', '#register-vehicle2user-name');
            $('.customer-search-popup-box').attr('data-source-id-box', '#register-vehicle2user-uid');
            ACC.global.popupCustomerSearchBox(parentEle);
        })
    },

    popupCustomerSearchBox: function(parentWindowsBox){
        var index = layer.open({
            type: 1,
            title: $('.customer-search-popup-wrap-box .headline').html(),
            area:'600px',
            content: $('.customer-search-popup-wrap-box .content-box').html(),
            scrollbar: true,
            shadeClose: true,
            success:function (data) {
                ACC.global.bindSearchCustomerbox(parentWindowsBox, $(data.selector))
            }
        })
    },

    bindSearchCustomerbox: function (parentWindowsBox, currentWindowsBox) {
        $('.customer-search-popup-box .search-btn', currentWindowsBox).on('click', function () {
            var values = ACC.global.getFormValue($(".customer-search-popup-box form", currentWindowsBox));
            var actionUrl = $('.customer-search-popup-box form', currentWindowsBox).attr('action');
            $.ajax({
                url: actionUrl,
                type: "POST",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(values),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    if (data.success == true) {
                        var customerList = data.customerList;
                        if (customerList) {
                            $('.customer-search-popup-box table tbody', currentWindowsBox).html("");
                            $.each(customerList, function (i, customer) {
                                $('.customer-search-popup-box table tbody', currentWindowsBox).append("" +
                                    "<tr data-uid=\"" + customer.uid + "\" data-name=\"" + customer.name + "\">" +
                                    "<td>" + customer.name + "</td>\n" +
                                    "<td>" + customer.name + "</td>\n" +
                                    "<td>" + customer.mobileNumber + "</td>" +
                                    "</tr>");
                            });
                            $('.customer-search-popup-box table tbody tr', currentWindowsBox).on('click', function () {
                                $('.customer-search-popup-box table tbody tr', currentWindowsBox).removeClass('chose-td');
                                $('.customer-search-popup-box table tbody tr td', currentWindowsBox).attr('style',"");
                                $(this).addClass('chose-td');
                                $("td", this).attr('style', "background-color: #ccc;");
                            })
                            $('.customer-search-popup-box .btn-confirm', currentWindowsBox).on("click", function () {
                                var choseElement = $('.customer-search-popup-box table tr.chose-td', currentWindowsBox);
                                var popBox = $('.customer-search-popup-box', currentWindowsBox);
                                if (choseElement.length > 0) {
                                    var nameBoxName = popBox.attr('data-source-name-box');
                                    var idBoxName = popBox.attr('data-source-id-box');
                                    $('' + nameBoxName, parentWindowsBox).val(choseElement.attr('data-name'));
                                    $('' + idBoxName, parentWindowsBox).val(choseElement.attr('data-uid'));
                                    $('.customer-search-popup-box table tbody', currentWindowsBox).html("");
                                    layer.close(currentWindowsBox.attr('times'))
                                } else {
                                    layer.msg("请选择",{
                                        icon:2,
                                        btn: ['关闭']
                                    });
                                }
                            })
                        }
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    layer.msg("删除失败",{
                        icon:2,
                        btn: ['关闭']
                    });
                }
            })
            return false;
        })
    },

    bindInsuranceDelete: function () {
        $('.delete-vehicleinsurance').on('click', function () {
            var pks = {'pk': []}, actionUrl = $('.delete-vehicleinsurance').attr('delete-url');
            $('.insurance-tab-info input[name="pk"]').each(function () {
                if ($(this).is(':checked')) {
                    pks['pk'].push($(this).val());
                }
            })
            if (pks['pk'].length <= 0) {
                return false;
            }
            $.ajax({
                url: actionUrl,
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: "json",
                data: pks,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("删除失败")
                }
            })
            return false;
        })
    },

    bindRelatedDelete: function () {
        $('.delete-vehicle2user').on('click', function () {
            var pks = {'pk': []}, actionUrl = $('.delete-vehicle2user').attr('delete-url');
            $('.related-tab-info input[name="pk"]').each(function () {
                if ($(this).is(':checked')) {
                    pks['pk'].push($(this).val());
                }
            })
            if (pks['pk'].length <= 0) {
                return false;
            }
            $.ajax({
                url: actionUrl,
                type: "POST",
                contentType: 'application/x-www-form-urlencoded',
                dataType: "json",
                data: pks,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("删除失败")
                }
            })
            return false;
        })
    },

    bindInsuranceForm: function (currentWindowsBox) {
        $("#vehicle-insurance-form",currentWindowsBox).submit(function () {
            var values = ACC.global.getFormValue($("form", currentWindowsBox));
            var actionUrl = $(this).attr("action");
            $.ajax({
                url: actionUrl,
                type: "POST",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(values),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    console.log("opportunity update result:" + JSON.stringify(data));
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            })
            return false;
        })
    },

    bindRelatedForm: function (currentWindowsBox) {
        $("#vehicle2User-form",currentWindowsBox).submit(function () {
            var values = ACC.global.getFormValue($("form", currentWindowsBox));
            var actionUrl = $(this).attr("action");
            $.ajax({
                url: actionUrl,
                type: "POST",
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(values),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("CSRFToken", $("input[name='CSRFToken']").val());
                },
                success: function (data) {
                    console.log("opportunity update result:" + JSON.stringify(data));
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            })
            return false;
        })
    },

    bindInsuranceTab: function () {
        $('.add-vehicleinsurance').on("click", function () {
            var index = layer.open({
                type: 1,
                title: $('.vehicleinsurance-popup-box .headline').html(),
                content: $('.vehicleinsurance-popup-box .content-box').html(),
                scrollbar: false,
                shadeClose: true,
                area:'600px',
                success:function (data) {
                    $(".layui-layer-content .btn-default").on('click', function(){
                        layer.close(index)
                    })
                    ACC.global.bindInsuranceForm($(data.selector))
                }
            });
        });
    },


    /*shaun*/
    showdetailsPag: function () {
        $('.pick_carTable a').on("click",function(){
            var actionUrl = $(this).attr('popup-url');
            $.ajax({
                url:actionUrl,
                type:"GET",
                data:{},
                success:function (htmldata){
                    var index = layer.open({
                        type:1,
                        title:"",
                        content:htmldata,
                        scrollbar:false,
                        shadeClose:true,
                        success:function(data){
                            $(".layui-layer-content .btn-default").on('click',function(){
                                layer.close(index)
                            })
                            ACC.global.showclosedReason($(htmldata))
                        }
                    });
                },
                error:function(){
                }
            })
        })
    },


    /*shaun*/
    showclosedReason: function (dataEelement) {
        $('#details #reason').on("click",function(){
                    var index = layer.open({
                        type:1,
                        title:"",
                        content:$('.closedReason-popup-box',dataEelement).html(),
                        scrollbar:false,
                        shadeClose:true,
                        success:function(data){
                            $(".layui-layer-content .btn-default").on('click',function(){
                                layer.close(index)
                            })
                        }
                    });
        })
    },

    /*shaun*/
    nextStepToCreatePickup:function choose (){
        $('.related-tab').on("click",function(){
            $('.consultant').removeClass('hide');
            $('.pickupbaseinfo').addClass('hide');
        })
    },

    /*shaun*/
    selectpickUp:function(){
        $(".but_objective").on("click",function () {
            $("#purpose").val(
                $(this).attr("data-value")
            )
        })
    },

    /*shaun*/
    selectcustomer:function(){
        $(".choosecustomer-box tr td").on("click",function () {
            $('.choosecustomer',$(this).parent().parent()).prop('checked',false);
            $('.choosecustomer',$(this).parent()).prop('checked',true);
            $("#serviceConsultant").val(
                $('.choosecustomer',$(this).parent()).attr("value")
            )
            return false;
        })
    },




    bindRelatedTab: function () {
        $('.add-vehicle2user').on("click", function () {
            var index = layer.open({
                type: 1,
                title: $('.vehicle2User-popup-box .headline').html(),
                content: $('.vehicle2User-popup-box .content-box').html(),
                scrollbar: false,
                shadeClose: true,
                area:'600px',
                success:function (data) {
                    $(".layui-layer-content .btn-default").on('click', function(){
                        layer.close(index)
                    });
                    ACC.global.bindSearchCustomerPopupForRelated($(data.selector));
                    ACC.global.bindRelatedForm($(data.selector))
                }
            });
        });
    },

    bindChoseVehicleDetailTab: function () {
        $('#vehicleInfo-update .yd_submit').on("click", function () {
            $('#vehicleInfo-update').submit();
        });
        $('#chose-tab-box li').on("click", function () {
            if (!$(this).hasClass("active")) {
                var className = $(this).attr('data-box-id');
                $(this).attr("style","");
                $('#chose-tab-info-box').children().removeClass('active');
                $('#chose-tab-info-box .' + className).addClass('active');
            }
        })
    },

    bindVehicleProductInfoSelect: function () {
        $('#register-vehicle-vehicleBrand').on("change", function () {
            var value = $(this).val();
            $("#register-vehicle-vehicleCategory").html("<option value=\"\" disabled=\"disabled\" selected=\"selected\"></option>");
            $.each(categoryList, function (i, item) {
                if (item.code == value) {
                    $.each(item.subList, function (i, subitem) {
                        var options = "<option value=\"" + subitem.code + "\">" + subitem.name + "</option>";
                        $("#register-vehicle-vehicleCategory").append(options);
                    })
                }
            })
        });
        $('#register-vehicle-vehicleCategory').on("change", function () {
            var value = $(this).val();
            $("#register-vehicle-vehicle").html("<option value=\"\" disabled=\"disabled\" selected=\"selected\"></option>");
            $.each(productList, function (i, item) {
                if (item.categoryCode == value) {
                    var options = "<option value=\"" + item.code + "\">" + item.name + "</option>";
                    $("#register-vehicle-vehicle").append(options);
                }
            })
        });
        $('#register-vehicle-vehicle').on("change", function () {
            var value = $(this).val();
            $("#register-vehicle-color").html("<option value=\"\" disabled=\"disabled\" selected=\"selected\"></option>");
            $.each(productList, function (i, item) {
                if (item.code == value) {
                    $.each(item.subProduct, function (i, subitem) {
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
    },

    /*shaun*/
    showdetailsPag: function () {
        $('.pick_carTable a').on("click",function(){
            var actionUrl = $(this).attr('popup-url');
            $.ajax({
                url:actionUrl,
                type:"GET",
                data:{},
                success:function (htmldata){
                    var index = layer.open({
                        type:1,
                        title:"",
                        content:htmldata,
                        scrollbar:false,
                        shadeClose:true,
                        success:function(data){
                            $(".layui-layer-content .btn-default").on('click',function(){
                                layer.close(index)
                            })
                            ACC.global.showclosedReason($(htmldata))
                        }
                    });
                },
                error:function(){
                }
            })
        })
    },



    /*shaun*/
    nextStepToCreatePickup:function choose () {
        $('#pickupInStoreData .next-btn').on("click", function () {
            $('.consultant').removeClass('hide');
            $('.pickupbaseinfo').addClass('hide');
        })
        $('#pickupInStoreData .last-btn').on("click", function () {
            $('.consultant').addClass('hide');
            $('.pickupbaseinfo').removeClass('hide');
        })
    },


    selectPurpose:function(){
        $(".Ry_flex .but_objective").on("click",function(){
            $(this).css({
                "backgroundColor":"#C71B1E",
                "color":"#fff",
                "borderColor":"#C71B1E"
            }).siblings().css({
                "backgroundColor":"#fff",
                "color":"#999999",
                "borderColor":"#E4E4E4"
            })
        })
    }


}


$(function(){
    $(".Ry_flex .but_objective").click(function(){
        $(this).css({
            "backgroundColor":"#C71B1E",
            "color":"#fff",
            "borderColor":"#C71B1E"
        }).siblings().css({
            "backgroundColor":"#fff",
            "color":"#999999",
            "borderColor":"#E4E4E4"
        })
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

    $.each(ACC.global._autoload,function(i, item){
        ACC.global[item]();
    })
});
