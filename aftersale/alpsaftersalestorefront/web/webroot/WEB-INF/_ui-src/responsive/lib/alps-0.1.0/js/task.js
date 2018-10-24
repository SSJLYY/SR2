ACC.TASK = {
    _autoload: [
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
                            ACC.TASK.showclosedReason($(htmldata))
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
                            ACC.TASK.showclosedReason($(htmldata))
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
    $.each(ACC.TASK._autoload,function(i, item){
        ACC.TASK[item]();
    })
});
