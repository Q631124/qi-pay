<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="lt-ie9 no-js" xml:lang="en">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" class="no-js">
<head>
    <meta charset="utf-8" />
    <title>支付</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pay2.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/system/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="http://res2.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
</head>
<body>
<input name="code" type="hidden" value="${param.code}">
<input name="phone" type="hidden" value="${param.phone}">
<input name="paytype" type="hidden" value="${param.pay_type}">
<input name="name" type="hidden" value="${param.name}" />
<input name="classItem" type="hidden" value="${param.classItem}" />
<input name="price" type="hidden" value="${param.price}" />
<div style="min-height: 673px; height: 500px">

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

</div>


</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wxcode.js"></script>