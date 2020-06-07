$(document).ready(function () {
    setBtn();
});

function setBtn() {
    var phone = $('input[name=phone]').val();
    var pay_type = $('input[name=paytype]').val();
    var code = $('input[name=code]').val();
    var price = $('input[name=price]').val();
    var name = $('input[name=name]').val();
    var classItem = $('input[name=classItem]').val();
    $.ajax({
        url: '/pay/wechat.do',
        type: 'post',
        data: {
            phone: phone,
            pay_type: pay_type,
            code: code,
            name: name,
            phone: phone,
            price: price,
            classItem: classItem
        },
        dataType: 'JSON',
        success: function (data) {
            if(data != null){
                if(data.pay_type == "wx"){
                    if (typeof WeixinJSBridge == "undefined"){
                        if( document.addEventListener ){
                            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                        }else if (document.attachEvent){
                            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                        }

                    }else{
                        onBridgeReady();
                    }
                }else if(data.pay_type == "h5"){
                    window.location.href = data.mweb_url;
                }
                function onBridgeReady() {
                    WeixinJSBridge.invoke(
                        'getBrandWCPayRequest', {
                            "appId": data.appId,     //公众号名称，由商户传入
                            "timeStamp": data.timeStamp,         //时间戳，自1970年以来的秒数
                            "nonceStr": data.nonceStr, //随机串
                            "package": data.package,
                            "signType": "MD5",         //微信签名方式：
                            "paySign": data.paySign //微信签名
                        },
                        function (res) {
                            if (res.err_msg == "get_brand_wcpay_request:ok") {
                                window.location.href = "/page/paysuccess.do";
                            }else{
                                alert("支付失败");
                            }
                        });
                }
            }

        }
    });
}
