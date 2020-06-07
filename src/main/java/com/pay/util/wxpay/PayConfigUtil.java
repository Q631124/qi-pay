package com.pay.util.wxpay;


public class PayConfigUtil {
    public final static String APP_ID = "xxxxxxxx";  //公众号appid
    public final static String MCH_ID = "xxxxx";   //商业号
    public final static String API_KEY = "xxxxxxxxxx";    //key

    public final static String NOTIFY_URL = "http://127.0.0.1/page/wxSuccess.do";  //返回请求的url

    public final static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";  //微信支付接口

    public final static String TRADE_TYPE_NATIVE = "NATIVE";
    public final static String TRADE_TYPE_JSAPI = "JSAPI";
    public final static String TRADE_TYPE_H5 = "MWEB";

    public final static String PAY_STATE_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

}
