ACC.SERVICEORDER = {
    _autoload: [
        "initserviceorder",
        "bindMutiStepServiceOrder",
        "bindSplitOrder",
        "calculationPrice"
    ],
    initserviceorder:function(){
        $('.changebox').hide();
        $('.yd_change').on('click',function(){
            $('.changebox').show();
            $('.showbox').hide();
        })
        $('.yd_cancle').on('click',function(){
            $('.changebox').hide();
            $('.showbox').show();
        })
        var form = $("#increment-order-update");

        $('.yd_submit', form).unbind('click').on("click", function () {
            form.attr("action", form.attr('updateaction'))
            form.submit();
        });

        $('.btn_refund').on("click", function () {
            if($(".changeEntryBox input.operationBox:checked").length<=0){
                layer.msg("请选中行项目",{
                    icon:2,
                    btn: ['关闭']
                });
                return false;
            }
            form.attr("action", form.attr('returnaction'))
            form.submit();
        });
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
            if($('.confirm-entries-table tbody tr',this).length>0){
                return true;
            }
            layer.msg("请添加行项目",{
                icon:2,
                btn: ['关闭']
            });
            return false;
        });
    },

    calculationPrice: function(){
        $('.able-split-list input.split-selection-box:checked').each(function(i,item){
            var selectedTr = $(this).parents('tr');
            var totalprice = parseFloat($(".totalprice", selectedTr).text());
            var remainSum = parseFloat($(".remainSum", selectedTr).text());
            var priceDiscount = 0;
            var rate = 0;

            if($('.selectedSplitType').attr('name') == 'splitByPrice'){
                var priceDiscount = parseFloat($('.selectedSplitType').val());
                priceDiscount = totalprice>=priceDiscount?priceDiscount:totalprice;
                rate = priceDiscount/totalprice*100;
            }
            if($('.selectedSplitType').attr('name') == 'splitByRate'){
                rate = parseFloat($('.selectedSplitType').val());
                priceDiscount = rate/100*totalprice;
            }
            if(priceDiscount>remainSum){
                priceDiscount = remainSum;
                remainSum = 0;
            }else{
                remainSum -= priceDiscount;
            }
            $(".remainSum", selectedTr).text(remainSum.toFixed(2));
            $(".splitPriceBox", selectedTr).text(priceDiscount.toFixed(2));
            $(".splitRateBox", selectedTr).text(rate.toFixed(2));
            $('.splitPriceValue',selectedTr).val(priceDiscount.toFixed(2))
            $('.splitRateValue',selectedTr).val(rate.toFixed(2))
            if(priceDiscount<=0){
                $(this).attr("checked",false);
            }
        })
    },

    bindReviewSplitOrder:function(){
        $('.deleted-split-order').on("click",function(){
            var itemlength = $('.already-split-order .split-preview-order').length;
            var item = $(this).parents("div.split-preview-order");
            item.remove();
            if(itemlength<=1) {
                //back
            }
        })

        $('.deleted-split-tr').on("click",function(){
            var item = $(this).parents("tr");
            item.remove();
        })
    },

    bindSplitOrder: function(){
        $(".split-order-box .work_middle").click(function(){
            $(this).css({
                "color":"#ffffff",
                "backgroundColor":"#C71B1E"
            }).children().children().children("input").addClass('selectedSplitType').removeAttr("disabled").focus();
            $(this).parent().siblings().children().css({
                "color":"#333333",
                "backgroundColor":"#ffffff"
            }).children().children().children("input").removeClass('selectedSplitType').attr("disabled","disabled").val("")
        });

        $('.preview-btn').on('click', function(){
            var itemlength = $('.already-split-order .split-preview-order').length;
            ACC.SERVICEORDER.bindReviewSplitOrder();
            if(itemlength>0) {
                $('.preview-split-order-box').removeClass('hide');
                $('.split-order-box').addClass('hide')
            }else{
                layer.msg("无预览信息")
            }
        })

        //preview-split-order-box
        $('.back-split-btn').on('click', function(){
            $('.preview-split-order-box').addClass('hide');
            $('.split-order-box').removeClass('hide')
        })

        $('.split-btn').on('click',function(){
            ACC.SERVICEORDER.calculationPrice();

            var checkrow = $('.able-split-list input.split-selection-box:checked');
            var checkrowLength = checkrow.length;
            if(checkrowLength<=0) {
                layer.msg("无可拆分的元素")
                return false;
            }

            $('.split-preview-template tbody').html("");
            checkrow.each(function(i,item){
                var selectedTr = $(this).parents('tr');
                if(checkrowLength>1) {
                    $('.split-preview-template tbody').append("<tr>" + ACC.SERVICEORDER.formhtml(selectedTr) +
                        "<td><a href=\"#\" class=\"deleted-split-tr\">删除</a></td></tr>");
                }else if(checkrowLength==1){
                    $('.split-preview-template tbody').append("<tr>" + ACC.SERVICEORDER.formhtml(selectedTr) +
                        "<td>-</td></tr>");
                }else{
                    layer.msg("无可拆分的元素")
                }
                $('.split-preview-template td.need-remove').remove();
                $('.split-preview-template td').show();
            });

            if(checkrowLength<=0) {
                return false;
            }

            var subTypeValue = $('.sub-type-box .seleted').attr('data-value')
            var subTypeid = $('.sub-type-box .seleted').attr('data-id')
            $('.split-preview-template input[name="suborder[x].serviceSubTypeCode"]').val(subTypeid)
            $('.split-preview-template .subtypelabel').text(subTypeValue)

            var senderName = $('input[name="sender.name"]').val()
            var sendUid = $('input[name="serviceOrderData.sender.uid"]').val()
            $('.split-preview-template .buyerlabel').text(senderName)
            $('.split-preview-template input[name="suborder[x].buyer"]').val(sendUid)

            var defualtSequence = $('.already-split-order .split-preview-order').length+1;
            $('.split-preview-template .preview-sequence').text(defualtSequence);
            var splitHtml = $('.split-preview-template').html();
            splitHtml = splitHtml.replace(new RegExp("\\[x\\]",'g'),"["+defualtSequence+"]");
            $('.already-split-order').append(splitHtml);

            $(".dismanting_click").unbind('click').on('click', function(){
                $(this).siblings().children(".dismanting_none").slideToggle("slow");
            });

            layer.msg("已拆分",{
                icon:1,
                btn: ['关闭']
            });
            return false;
        })


    },

    formhtml : function(selectedtr) {
        $("input,textarea,button", selectedtr).each(function () {
            this.setAttribute('value', this.value);
        });
        $(":radio,:checkbox", selectedtr).each(function () {
            if (this.checked){
                this.setAttribute('checked', 'checked');
            }
            else{
                this.removeAttribute('checked');
            }
        });
        $("option", selectedtr).each(function () {
            if (this.selected) {
                this.setAttribute('selected', 'selected');
            } else {
                this.removeAttribute('selected');
            }
        });
        return selectedtr.html();
    }
}


$(function(){
    $.each(ACC.SERVICEORDER._autoload,function(i, item){
        ACC.SERVICEORDER[item]();
    })
});
