ACC.ADDPRODUCT = {
    _autoload: [
        "bindEntryOperationPopup"
    ],

    calculatePrice: function(databox){
        var priceBox = $('#entry-product-price', databox);
        var rateBox = $('#entry-discount-rate', databox);
        var actualPriceBox = $('#entry-actual-price', databox);

        $('#entry-discount-rate', databox).on("change",function(){
            var price = parseInt(priceBox.text());
            var actualprice = $(this).val()/100*price;
            actualPriceBox.val(Math.round(actualprice*100)/100);
        })
        $('#entry-actual-price', databox).on("change",function(){
            var price =parseInt(priceBox.text());
            var rate = $(this).val()/price*100;
            rateBox.val(Math.round(rate*10)/10);
        })
    },

    confirmAddProductBtn: function(databox, noAdditional){
        $('.btn-confirm', databox).on("click",function(){
            var entrysequence= $(".entry-operation-box table tbody tr").length;
            entrysequence++;
            var serviceTypeHtml = $("#entry-type option:selected",databox).html();
            var serviceTypeVal = $("#entry-type option:selected",databox).attr("value");
            var itemCategoryHtml = $("#entry-itemCategory option:selected",databox).html();
            var itemCategoryVal = $("#entry-itemCategory option:selected",databox).attr("value");
            var productName = $("#entry-product-name",databox).val();
            var price = $("#entry-product-price",databox).text();
            var productVal = $("#entry-product-id",databox).val();
            var quantity = $("#entry-quantity",databox).val();
            var rate = $("#entry-discount-rate",databox).val();
            var actualPrice = $("#entry-actual-price",databox).val();

            var replacebox = null
            $('.entry-operation-box table td.product-code input').each(function(i,item){
                if($(this).val() == productVal){
                    entrysequence = i+1;
                    replacebox = $(this).parents("tr");
                    if(noAdditional == false) {
                        var quantityInputName = $(this).attr('name');
                        quantityInputName = quantityInputName.replace('code', 'quantity');
                        var oldquantity = $('input[name="' + quantityInputName + '"]', replacebox).val();
                        oldquantity > 0 ? quantity = parseInt(quantity) + parseInt(oldquantity) : '';
                    }
                    return;
                }
            })

            var html = "<td>"+entrysequence+"</td>";
            if(serviceTypeHtml && serviceTypeHtml!='') {
                html += "<td>" + serviceTypeHtml + "<input type='hidden' name='entries[" + entrysequence + "].serviceTypeCode' value='" + serviceTypeVal + "'/></td>";
            }
            if(itemCategoryHtml && itemCategoryHtml!='') {
                html += "<td>" + itemCategoryHtml + "<input type='hidden' name='entries[" + entrysequence + "].categoryCode' value='" + itemCategoryVal + "'/></td>";
            }
            if($(".entry-operation-box table").hasClass("changeEntryBox")){
                html += "<td class='product-code'><a href=\"#\">"+productVal+"</a><input type='hidden' name='entries["+entrysequence+"].code' value='"+productVal+"'/></td>";
            }else{
                html += "<td class='product-code'>"+productVal+"<input type='hidden' name='entries["+entrysequence+"].code' value='"+productVal+"'/></td>";
            }

            html += "<td>"+productName+"<input type='hidden' name='entries["+entrysequence+"].name' value='"+productName+"'/></td>" +
                "<td>"+quantity+"<input type='hidden' name='entries["+entrysequence+"].quantity' value='"+quantity+"'/></td>" +
                "<td>"+price+"<input type='hidden' name='entries["+entrysequence+"].price' value='"+price+"'/></td>" +
                "<td>"+rate+"%<input type='hidden' name='entries["+entrysequence+"].rate' value='"+rate+"'/></td>" +
                "<td>"+actualPrice+"<input type='hidden' name='entries["+entrysequence+"].actualPrice' value='"+actualPrice+"'/></td>" +
                "<td>"+(actualPrice*quantity)+"<input type='hidden' name='entries["+entrysequence+"].rowTotal' value='"+(actualPrice*quantity)+"'/></td>" +
                "<td><input type=\"checkbox\" class=\"operationBox\" name=\"entries[${status.index+1}].operationBox\" value=\""+productVal+"\"/></td>";

            if(replacebox){
                replacebox.html(html);
            }else {
                $(".entry-operation-box table tbody").append("<tr sequence-data='"+entrysequence+"'>"+html+"</tr>");
            }

            $('.delete-entry').on("click",function(){
                $(this).parent().parent().remove();
                return false;
            })

            ACC.ADDPRODUCT.bindChangeBtn();
            layer.close($(databox).attr('times'))
        })
    },

    bindEntryOperationPopup: function(){
        $('.entry-operation-box .delete-all-selected').on('click',function(){
            $('.entry-operation-box table input.operationBox:checked').each(function(i,domele){
                $(this).parent().parent().remove();
            })
        })
        $('.entry-operation-box .add-entry').on('click', function () {
            var index = layer.open({
                type: 1,
                title: $('.add-entry-popup-wrap-box .headline').html(),
                content: $('.add-entry-popup-wrap-box .content-box').html(),
                area:'600px',
                shadeClose: true,
                success:function (data, indexNumber) {
                    databox = $(data.selector);
                    ACC.ADDPRODUCT.bindChoseProductPopup(databox)
                    ACC.ADDPRODUCT.calculatePrice(databox);
                    ACC.ADDPRODUCT.confirmAddProductBtn(databox,false);
                }
            })
        })
        $('.add-multiple-entries').on('click', function(){
            var parentPageBlock = $(this).parents('.parent-page-block');
            parentPageBlock.hide();
            $('.add-multiple-entries tbody tbody').html("");
            $('.multi-sp-block').show();
            $('.close-multi-sp-block').on('click',function(){
                parentPageBlock.show()
                $('.multi-sp-block').hide();
            })
        })
        $('.multi-sp-block .multi-tab-product-switch input').on('click',function(){
            var categoryValue = $(this).attr('data-value');
            $('.multi-sp-block .table').hide();
            $('.multi-sp-block .'+categoryValue+'-table').show();

        })
        $('.multi-sp-block .yd-search-product').on('click',function(){
            var categoryValue = $('input[name="addentry.itemCategory"]').val();
            var searchText = $('input[name="addentry.searchText"]').val();
            if(categoryValue==""){
                layer.msg('无可用分类', {
                    icon:2,
                    btn: ['关闭']
                });
            }
            var actionUrl=$(this).attr('data-request-url');
            ACC.ADDPRODUCT.getProductPopupData(actionUrl,
                categoryValue,
                searchText,
                function(actionUrl, categoryValue, data){
                    ACC.ADDPRODUCT.ProcessProductBoxForMutiSP(actionUrl, categoryValue, data);
                });
        })
        $('.multi-sp-block .yb-confirm').on('click',function() {
            var sequence = $(".confirm-entries-table tr").length;
            var typelength = $('select[name="addentry.type"]').length;
            var type, typeText;
            if (typelength > 0) {
                type = $('select[name="addentry.type"]').val();
                typeText = $('select[name="addentry.type"] option:selected').text();
            }
            var categoryValue = $('input[name="addentry.itemCategory"]').val();
            var categoryText = $('input[name="addentry.itemCategory"]').parent().children('.seleted').val();

            $(".multi-sp-block table input.operationBox:checked").each(function (i, item) {
                var tr = $(this).parents('tr');
                var productCode = $('input[name="entries[x].code"]', tr).val();
                var hasEntry = false;
                $(".confirm-entries-table td.product-code input").each(function (i, item) {
                    if ($(this).val() == productCode) {
                        var confirmTr = $(this).parents('tr');
                        var oldQuantity = parseInt($('td.entry-quantity input', confirmTr).val());
                        var addQuantity = parseInt($('td.entry-quantity input', tr).val());
                        var discountRate = $('td.discountRate input', tr).val();
                        var price = $('td.actualPrice input', tr).val();
                        var quantity = addQuantity + oldQuantity;
                        var total = price * quantity;
                        $('td.entry-quantity input', confirmTr).attr('value', addQuantity + oldQuantity);
                        $('td.entry-quantity span', confirmTr).text(addQuantity + oldQuantity);
                        $('td.discountRate input', confirmTr).attr('value', discountRate);
                        $('td.actualPrice input', confirmTr).attr('value', price);
                        $('td.discountRate span', confirmTr).text(discountRate);
                        $('td.actualPrice span', confirmTr).text(price);
                        $('.total-price', confirmTr).text(total.toFixed(2));
                        hasEntry = true;
                        return;
                    }
                });
                if (hasEntry) {
                    return;
                }

                var htmlString = tr.html();
                var globalHtml = "<td class=\"sequence-box\" sequence-data=\"" + sequence + "\">" + sequence + "</td>";
                if (typelength > 0){
                    globalHtml += "<td>" + typeText + "<input type=\"hidden\" name=\"entries[x].serviceTypeCode\" value=\"" + type + "\"></td>";
                }
                globalHtml += "<td>"+categoryText+"<input type=\"hidden\" name=\"entries[x].categoryCode\" value=\""+categoryValue+"\"></td>";
                htmlString = htmlString.replace(new RegExp("^<td class=\"sequence-box.*</td>",'g'),globalHtml);
                htmlString = htmlString.replace(new RegExp("\\[x\\]",'g'),"["+sequence+"]");
                $(".confirm-entries-table tbody").append("<tr>"+htmlString+"</tr>");
                $(".confirm-entries-table tbody tr td").show();
                sequence++;
            })
            $('.parent-page-block').show();
            $('.multi-sp-block').hide();
        })
        ACC.ADDPRODUCT.bindChangeBtn();
    },

    ProcessProductBoxForMutiSP:function(actionUrl, categoryValue, data){
        var discountRate = $('input[name="addentry.discountRate"]').val();
        $('.multi-sp-block .'+categoryValue+'-table tbody').html("");
        $.each(data.productList, function (i, product) {
            var html = "<tr data-uid=\"" + product.code + "\" data-price=\"" + product.price + "\" data-name=\"" + product.name + "\">" +
                "<td class='sequence-box'>" + (i+1) + "</td>\n" +
                "<td class='product-code'>" + product.code + "<input type=\"hidden\" name=\"entries[x].code\" value=\""+product.code+"\"></td>\n" +
                "<td>" + product.name + "</td>\n" +
                "<td class=\"td_border td_not_padding entry-quantity\"><input class=\"td-input\"";
            if((i+1)%2==1) {
                html += " style='background-color: #FFFAFA;'";
            }
            html += " value=\"1\" name=\"entries[x].quantity\" type=\"text\"></td>";
            var actualPrice = product.price*discountRate/100;
            actualPrice.toFixed(2);
            html += "<td>" + product.price + "<input type=\"hidden\" name=\"entries[x].price\" value=\""+product.price+"\"></td>" +
                "<td class='discountRate'><span>" + discountRate + "</span>%<input type=\"hidden\" name=\"entries[x].rate\" value=\""+discountRate+"\"></td>" +
                "<td class='actualPrice'><span>" + actualPrice + "</span><input type=\"hidden\" name=\"entries[x].actualPrice\" value=\""+actualPrice+"\"></td>" +
                "<td class='total-price' style='display: none'>" + actualPrice + "</td>" +
                "<td><input type=\"checkbox\" class=\"operationBox\" name=\"entries[x].operationBox\" value=\"JT2H15258\"></td>" +
                "</tr>";
            $('.multi-sp-block .'+categoryValue+'-table tbody').append(html);
        })
        $('.multi-sp-block .'+categoryValue+'-table input[name="entries[x].quantity"]').unbind('change').on('change',function(){
            var quantity = $(this).val();
            $(this).attr("value",quantity);
            var tr = $(this).parents('tr');
            var price = $('input[name="entries[x].actualPrice"]',tr).val();
            var total = quantity*price;
            $('.total-price',tr).text(total.toFixed(2));
            $('.operationBox',tr).attr('checked',true);
        })
    },

    bindChangeBtn:function(){
        $(".entry-operation-box table.changeEntryBox .product-code a").unbind('click').on('click', function(){
            var sequenceData = $(this).parents('tr').attr('sequence-data');
            var serviceTypeCode = $('input[name="entries['+sequenceData+'].serviceTypeCode"]').val();
            var categoryCode = $('input[name="entries['+sequenceData+'].categoryCode"]').val();
            var productCode = $('input[name="entries['+sequenceData+'].code"]').val();
            var productName = $('input[name="entries['+sequenceData+'].name"]').val();
            var quantity = $('input[name="entries['+sequenceData+'].quantity"]').val();
            var price = $('input[name="entries['+sequenceData+'].price"]').val();
            var rate = $('input[name="entries['+sequenceData+'].rate"]').val();//actualPrice
            var actualPrice = $('input[name="entries['+sequenceData+'].actualPrice"]').val();

            var index = layer.open({
                type: 1,
                title: $('.add-entry-popup-wrap-box .headline').html(),
                content: $('.add-entry-popup-wrap-box .content-box').html(),
                area:'600px',
                shadeClose: true,
                success:function (data, indexNumber) {
                    databox = $(data.selector);
                    $('#entry-type',databox).val(serviceTypeCode);
                    $('#entry-itemCategory',databox).val(categoryCode)
                    $('#entry-product-name',databox).val(productName);
                    $('#entry-product-price',databox).text(price);
                    $('#entry-product-name',databox).attr("disabled","disabled");
                    $('#entry-product-id',databox).val(productCode);
                    $('#entry-quantity',databox).val(quantity);
                    $('#entry-discount-rate',databox).val(rate);
                    $('#entry-actual-price',databox).val(actualPrice);

                    ACC.ADDPRODUCT.calculatePrice(databox);
                    ACC.ADDPRODUCT.confirmAddProductBtn(databox,true);
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
            $('.product-search-popup-box').attr('entry-actual-price', $(this).parent().attr('entry-actual-price'));

            if(categoryValue=="" || categoryValue==null){
                layer.close(index);
                layer.msg('无可用分类', {
                    icon:2,
                    btn: ['关闭']
                });
                return;
            }
            ACC.ADDPRODUCT.getProductPopupData(actionUrl,
                categoryValue,
                "",
                function(actionUrl, categoryValue, data){
                    layer.close(index);
                    ACC.ADDPRODUCT.ProcessProductPopup(actionUrl, categoryValue, data, currentBox);
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
            area:['600px', '500px'],
            content: $('.product-search-popup-wrap-box .content-box').html(),
            success:function (data) {
                var popBox = $(data.selector);
                $('.close-btn',popBox).on('click',function(){
                    layer.close(popBox.attr('times'))
                })
                ACC.ADDPRODUCT.fillProductDataIntoTable(productData, popBox, parentWindowsBox);
                $("#product-search-form", $(data.selector)).on("submit", function(){
                    ACC.ADDPRODUCT.getProductPopupData(actionUrl,
                        categoryValue,
                        $(".request-product-searchText", $(data.selector)).val(),
                        function(actionUrl, categoryValue, productData){
                            ACC.ADDPRODUCT.fillProductDataIntoTable(productData, popBox, parentWindowsBox);
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
                var actualPricekey = attrdiv.attr('entry-actual-price');

                $('' + nameBoxName, parentWindowsBox).val(choseElement.attr('data-name'));
                $('' + idBoxName, parentWindowsBox).val(choseElement.attr('data-uid'));
                $('' + productPrice, parentWindowsBox).text(choseElement.attr('data-price'));
                $('' + actualPricekey, parentWindowsBox).val(choseElement.attr('data-price'));
                $('' + actualPricekey, parentWindowsBox).trigger("change");
                $('table tbody', box).html("");
                layer.close(box.attr('times'))
            } else {
                layer.msg("请选择",{
                    icon:2,
                    btn: ['关闭']
                });
            }
        })
    }
}


$(function(){
    $.each(ACC.ADDPRODUCT._autoload,function(i, item){
        ACC.ADDPRODUCT[item]();
    })
});
