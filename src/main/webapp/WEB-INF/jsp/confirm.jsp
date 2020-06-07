<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html class="lt-ie9 no-js" xml:lang="en">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/system/jquery-1.9.1.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pay2.css" />
</head>
<body>

<form id="frm" action="<%=request.getContextPath()%>/pay/alipay.do" method="post">
    <input type="hidden" name="price" value="${param.price}" />
    <input type="hidden" name="classItem" value="${param.classItem}" />
    <div id="bd" style="background: pink">
        <div class="user-inform o-wp">
            <div class="hd">
                <h2>用户信息</h2>
            </div>
            <div class="bd">
                <div class="inform-box">
                    姓名：<input id="name" name="name" type="text" required
                              oninvalid="setCustomValidity('请输入姓名');" oninput="setCustomValidity('');" /><span
                        class="error-box">请输入姓名</span>
                </div>
                <div class="inform-box">
                    手机号：<input id="phone" type="text" name="phone" pattern="1\d{10}" required
                               oninvalid="setCustomValidity('请输入11位有效手机号');" oninput="setCustomValidity('');" />
                </div>
            </div>
        </div>
        <div class="order-pay o-wp o-wp-l" id="pay_method">
            <div class="hd">
                <span style="font-size: 16px"><strong>订单支付方式</strong></span><span style="color: red;font-size: 8px">(支付宝支付请用浏览器打开该页面)</span>
            </div>
            <div class="bd">
                <ul class="list pay-type">
                    <div class="pay-way" style="display: block;">
                        <label>
                            <input id="wechat" name="pay-way" value="0" type="radio" style="display: none" />
                            <img class="wechat" src="<%=request.getContextPath()%>/img/WePayLogo.png" />
                        </label>
                        <label>
                            <input id="alipay" name="pay-way" value="1" type="radio" style="display: none" />
                            <img class="alipay" src="<%=request.getContextPath()%>/img/alipay.png" />
                        </label>
                    </div>
                </ul>
                <input type="hidden" id="payment-group" name="Payment[group]" value="0">
            </div>
        </div>
            <div class="o-wp o-wp-l order-submit clearfix" id="order_action">
                <div class="fr">
                    <span class="vtf5">应付总额：</span> <span class="mr20 total-amount"
                                                          id="pay_amount"> ￥1.00 </span>
                    <input class="btn btn-order submit" type="submit" value="去支付"/>
                </div>
            </div>
    </div>
</form>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/confirm.js"></script>
</html>