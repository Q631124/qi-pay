<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="lt-ie9 no-js" xml:lang="en">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pay2.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/system/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/payorder.js"></script>
</head>
<body ng-app="xsteachApp">
<div id="bd" style="min-height: 673px;">
    <input name="phone" type="hidden" value="${param.phone}" />
    <input name="pay_type" type="hidden" value="${param.pay_type}" />
    <input name="name" type="hidden" value="${param.name}" />
    <input name="classItem" type="hidden" value="${param.classItem}" />
    <input name="price" type="hidden" value="${param.price}" />

    <div class="order-msg">
        <dl>
            <dt><i class="iconfont icon-success"></i>订单提交成功，请您尽快付款</dt>
            <dd class="first">
                应付金额：<span class="orange">￥${param.price == null ? 1.0 : param.price}</span>
            </dd>
            <dd class="grey">
                请您在提交订单后 <span class="orange">2小时内</span> 完成支付，否则订单将会自动取消
            </dd>
        </dl>
    </div>
    <div class="pay-ol pay-ol-tab o-wp xs-tab" id="payment_ol">
        <div class="hd">
            <ul class="tab-hd cl">
                <li class="tab-btn wxpay xs-active">
                    <a href="javascript:;">使用微信支付</a>
                </li>
            </ul>
        </div>
        <div class="bd">
            <div class="tab-bd">
                <p class="pay-tips mb10">请使用微信扫一扫，扫描下面的二维码支付。</p>
                <div class="pay-wx">
                    <div id="wx_pay_qrcode"><img src="" width="280" height="280"/></div>
                    <p>请使用微信扫一扫<br>扫描二维码支付</p>
                </div>
                <img class="scan-tips" src="<%=request.getContextPath()%>/img/wx_scan.jpg" alt="请打开微信扫一扫">
            </div>
        </div>
    </div>
</div>
</body>
</html>
