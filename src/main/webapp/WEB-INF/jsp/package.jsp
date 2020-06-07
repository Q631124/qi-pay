<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="lt-ie9 no-js" xml:lang="en">
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" class="no-js">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/pay.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/system/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/package.js"></script>
</head>
<body>
<div class="container">
    <div class="title"></div>
    <div class="fruit">
        <div class="banana-box">
            <label>
                <div class="banana">
                    <input name="fruit" type="radio" value="1" style="display: none"/>
                    <div class="pic"><img width="400" height="300" src="<%=request.getContextPath()%>/img/banana.jpg"/>
                    </div>
                    <div class="detail">
                        <div class="name">香蕉</div>
                        <div class="price">价格：￥<span>1</span></div>
                        <div class="clear"></div>
                    </div>
                </div>
            </label>
        </div>

    </div>
    <div class="model">

        <a class="buy" href="#">结算</a>

        <div class="sum-price">
            合计:
            <span class="price">￥1</span>
        </div>
        <div class="clear"></div>
    </div>
</div>
</body>
</html>