ACC.CUSTOMER = {
    _autoload: [
        "buildRegionBox",
        "bindSearchCustomerPopup",
        "bindSearchCustomerPopupForRelated"
    ],

    buildRegionBox: function () {
        if(typeof regionList!="undefined") {
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

    bindSearchCustomerPopup: function(){
        $('.call-search-customer-popup-box .attach-icon').on('click', function () {
            $('.customer-search-popup-box').attr('data-source-name-box', $(this).parent().attr("data-source-name-box"));
            $('.customer-search-popup-box').attr('data-source-id-box', $(this).parent().attr("data-source-id-box"));
            ACC.CUSTOMER.popupCustomerSearchBox($(this).parent());
        })
    },

    bindSearchCustomerPopupForRelated: function (parentEle) {
        $(".register-vehicle2user-name-box .attach-icon", parentEle).on('click', function () {
            $('.customer-search-popup-box').attr('data-source-name-box', '#register-vehicle2user-name');
            $('.customer-search-popup-box').attr('data-source-id-box', '#register-vehicle2user-uid');
            ACC.CUSTOMER.popupCustomerSearchBox(parentEle);
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
                ACC.CUSTOMER.bindSearchCustomerbox(parentWindowsBox, $(data.selector))
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
    }
}


$(function(){
    $.each(ACC.CUSTOMER._autoload,function(i, item){
        ACC.CUSTOMER[item]();
    })
});
