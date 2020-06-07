$(document).ready(function () {
    setBtn();
});

function setBtn() {

    $("#frm").submit(function(){
        return go();
    });
    //输入框输入事件
    $('.user-inform .inform-box').on('keyup', 'input', function () {
        if ($(this).val() != '') {
            $(this).next().hide();
        } else {
            $(this).next().show();
        }
    });

    //选择在线支付方式
    $('.pay-way').on('click', 'input', function () {
        $('.pay-way img').removeClass('checked');
        $('.pay-way input:checked').next().addClass('checked');
    });
    $('input[name=pay-way]:checked').next().addClass('checked');

    var item_price = '1';
    $('.total-amount').text('￥' + item_price);
}

function go() {////go
    var gg=true;
    if (gg) {
        var payway = $('input[name=pay-way]:checked').val();
        var phone = $('input[name=phone]').val();
        var isPC = 1;  //默认为电脑支付
        if ($('.pay-way input:checked').length < 1) {
            alert("请选择付款方式");
            gg=false;
        } else if (payway == 0) {
            //电脑端微信支付
            if(is_pc()){
                $("#frm").attr("action","/page/payorder.do?pay_type=pc");  //微信支付
                gg=true;
            }else{
                if(is_weixin()){
                    //手机端微信支付
                    gg = false;
                    //appid为服务号中的APPID
                    window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=xxxxxxxxx&redirect_uri="+encodeURIComponent('http://127.0.0.1/pay/getWxCode.do?pay_type=wx&phone='+phone)+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
                }else{
                    //手机端h5支付
                    gg=true;
                    $("#frm").attr("action","/pay/getWxCode.do?pay_type=h5&code=1");
                }
            }
        } else if (payway == 1) {
            if(is_weixin()){
                gg = false;
                alert("请点击右上角用手机浏览器打开该网页");
            }else{
                $("#frm").attr("action","/pay/alipay.do");   //支付宝支付
                gg=true;
            }
        }
    }
    return gg;
}

//是否为电脑支付，是返回true
function is_pc() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
        "SymbianOS", "Windows Phone",
        "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}
function is_weixin() {
    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    if (isWeixin) {
        return true;
    }else{
        return false;
    }
}


