ACC.VEHICLE = {
    _autoload: [
        "bindVehicleProductInfoSelect",
        "bindRelatedTab",
        "bindInsuranceTab",
        "bindRelatedDelete",
        "bindInsuranceDelete",
        "bindSearchVehiclePopup"
    ],

    bindSearchVehiclePopup: function(){
        $('.call-search-vehicle-popup-box .attach-icon').on('click', function () {
            $('.vehicle-search-popup-box').attr('data-source-name-box', $(this).parent().attr("data-source-name-box"));
            $('.vehicle-search-popup-box').attr('data-source-id-box', $(this).parent().attr("data-source-id-box"));
            ACC.VEHICLE.popupVehicleSearchBox($(this).parent());
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
                ACC.VEHICLE.bindSearchVehiclebox(parentWindowsBox, $(data.selector))
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
            var dontUseJson = $(this).attr('data-dont-usejson');
            var values = ACC.global.getFormValue($("form", currentWindowsBox));
            var actionUrl = $(this).attr("action");
            if(dontUseJson == 'true'){
                $.ajax({
                    url: actionUrl,
                    type: "POST",
                    dataType: "json",
                    data: values,
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
            }else{
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
            }
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
                    ACC.VEHICLE.bindInsuranceForm($(data.selector))
                }
            });
        });
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
                    ACC.CUSTOMER.bindSearchCustomerPopupForRelated($(data.selector));
                    ACC.VEHICLE.bindRelatedForm($(data.selector))
                }
            });
        });
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
    }
}


$(function(){
    $.each(ACC.VEHICLE._autoload,function(i, item){
        ACC.VEHICLE[item]();
    })
});
