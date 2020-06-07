$(document).ready(function () {
    setBtn();
});

function setBtn() {
    var phone = $('input[name=phone]').val();
    var pay_type = $('input[name=pay_type]').val();
    var price = $('input[name=price]').val();
    var name = $('input[name=name]').val();
    var classItem = $('input[name=classItem]').val();;
    $.ajax({
        url: '/pay/wechat.do',
        type: 'post',
        data: {
            phone: phone,
            pay_type: pay_type,
            name: name,
            phone: phone,
            price: price,
            classItem: classItem
        },
        async: false,
        dataType: 'JSON',
        success: function (data) {
            if(data != null){
                var code_url = data.code_url;
                if(code_url != null){
                    $('#wx_pay_qrcode img').attr("src","/pay/createQRCode.do?code_url="+code_url);
                    var interval=setInterval(function(){
                        $.ajax({
                            url:'/pay/paystate.do',
                            type:'post',
                            data:{
                                out_trade_no: data.out_trade_no
                            },
                            success:function(dt){
                                if(dt=="ok"){
                                    clearInterval(interval);
                                    window.location.href = "http://127.0.0.1/page/paysuccess.do";
                                }
                            }
                        });
                    },3000);
                }

            }
        }
    });

}
