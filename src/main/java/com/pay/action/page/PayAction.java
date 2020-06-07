package com.pay.action.page;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.pay.domain.User;
import com.pay.util.identity.ClassItemUtil;
import com.pay.util.wxpay.PayToolUtil;
import com.pay.util.identity.IDGeneratorUtil;
import com.pay.util.wxpay.*;
import com.swetake.util.Qrcode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/pay"})
public class PayAction {

    /**
     * 获取微信支付链接
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/wechat"})
    public Map<String,String> getWeChat(User user, String code, String pay_type, HttpServletRequest request, HttpServletResponse response){
        String code_url = "";
        Map<String,String> returnMap = new HashMap<String,String>();
        try {
            String phone = user.getPhone();
            String out_trade_no = IDGeneratorUtil.getID("DD");
            request.getSession().setAttribute("ORDER_NUM", out_trade_no);
            request.getSession().setAttribute("phone", phone);
            request.getSession().setAttribute("orderNum", out_trade_no);
            String body = ClassItemUtil.getClassName(user.getClassItem());
            int price = (int)(user.getPrice() * 100); //价格，单位为分
            String classItem = user.getClassItem();
            String name = user.getName();

            // 账号信息
            String appid = PayConfigUtil.APP_ID;  // appid
            String mch_id = PayConfigUtil.MCH_ID; // 商业号
            String key = PayConfigUtil.API_KEY; // key

            String currTime = PayToolUtil.getCurrTime();
            String timeStamp = PayToolUtil.getTimeStamp();
            String nonce_str = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
            // 终端IP
            String spbill_create_ip = getIpAddr(request);
            // 回调接口
            String notify_url = PayConfigUtil.NOTIFY_URL;
            String trade_type = "";
            String openId = "";
            String scene_info = "";
            if("pc".equals(pay_type)){
                trade_type = PayConfigUtil.TRADE_TYPE_NATIVE;
                packageParams.put("product_id",currTime);
            }else if("wx".equals(pay_type)){
                trade_type = PayConfigUtil.TRADE_TYPE_JSAPI;
                //页面获取openId接口
                String getopenid_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
                String  param="appid="+appid+"&secret=xxxxxxxxx"+"&code="+code+"&grant_type=authorization_code";
                //向微信服务器发送get请求获取openIdStr
                String openIdStr = HttpRequest.sendGet(getopenid_url, param);
                JSONObject json = JSONObject.parseObject(openIdStr);//转成Json格式
                openId = json.getString("openid");//获取openId
                packageParams.put("openid",openId);
            }else if("h5".equals(pay_type)){
                trade_type = PayConfigUtil.TRADE_TYPE_H5;
                scene_info = "{\"h5_info\":{\"type\": \"Wap\",\"wap_url\": \"http://127.0.0.1/page/confirm.do\",\"wap_name\": \"水果大甩卖\"}}";
                packageParams.put("scene_info",scene_info);
            }
            String params = out_trade_no + "," + phone + "," + name + "," + classItem + "," + user.getPrice();


            String attach = java.net.URLEncoder.encode(params,"UTF-8");
            packageParams.put("appid", appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);  //（调整为自己的名称）
            packageParams.put("out_trade_no", out_trade_no);
            packageParams.put("total_fee", price); //价格的单位为分
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", notify_url);
            packageParams.put("trade_type", trade_type);
            packageParams.put("attach",attach);
            packageParams.put("sign_type","MD5");
            String sign = PayToolUtil.createSign("UTF-8", packageParams,key);
            packageParams.put("sign", sign);
            String requestXML = PayToolUtil.getRequestXml(packageParams);

            String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);

            Map map = XMLUtil4jdom.doXMLParse(resXml);
            String return_code = (String)map.get("return_code");
            String return_msg = (String)map.get("return_msg");
            String prepay_id = "";
            String mweb_url = "";
            if("SUCCESS".equals(return_code)){
                code_url = (String) map.get("code_url");
                if("wx".equals(pay_type)){
                    prepay_id = (String) map.get("prepay_id");
                }else if("h5".equals(pay_type) && "OK".equals(return_msg)){
                    mweb_url = (String) map.get("mweb_url");//调微信支付接口地址
                    String redirect_url = "http://127.0.0.1/";
                    String redirect_urlEncode =  URLEncoder.encode(redirect_url,"utf-8");//对上面地址urlencode
                    mweb_url = mweb_url + "&redirect_url=" + redirect_urlEncode;
                    returnMap.put("mweb_url",mweb_url);
                }
            }else {
                throw new Exception("返回失败");
            }
            if("wx".equals(pay_type)){
                returnMap.put("appId",appid);
                returnMap.put("timeStamp",timeStamp);
                returnMap.put("nonceStr",nonce_str);
                returnMap.put("package","prepay_id=" + prepay_id);
                returnMap.put("signType","MD5");
                SortedMap<Object,Object> returnParams = new TreeMap<Object,Object>();
                returnParams.put("appId",appid);
                returnParams.put("timeStamp",timeStamp);
                returnParams.put("nonceStr",nonce_str);
                returnParams.put("package","prepay_id=" + prepay_id);
                returnParams.put("signType","MD5");
                String paySign = PayToolUtil.createSign("UTF-8", returnParams,key);
                returnMap.put("paySign",paySign);
            }
            returnMap.put("pay_type",pay_type);
            returnMap.put("code_url",code_url);
            returnMap.put("out_trade_no", out_trade_no);
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnMap;
    }

    @ResponseBody
    @RequestMapping({"/createQRCode"})
    public void createQRCode(String code_url,HttpServletResponse response) {
        try {
            Qrcode handler = new Qrcode();
            handler.setQrcodeErrorCorrect('M');
            handler.setQrcodeEncodeMode('B');
            handler.setQrcodeVersion(7);
            byte[] contentBytes = code_url.getBytes("UTF-8");
            int imgSize = 280;
            BufferedImage bufImg = new BufferedImage(imgSize, imgSize, 1);
            Graphics2D gs = bufImg.createGraphics();
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, imgSize, imgSize);
            gs.setColor(Color.BLACK);
            int pixoff = 2;

            if ((contentBytes.length > 0) && (contentBytes.length < 800)) {
                boolean[][] codeOut = handler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 6 + pixoff, i * 6 + pixoff, 6, 6);
                        }
                    }
                }
            }
            gs.dispose();
            bufImg.flush();
            ImageIO.write(bufImg, "jpg", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //支付宝支付,需要正确的参数才能调用成功
    @ResponseBody
    @RequestMapping(value={"/alipay"})
    public void createQRCode(User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            String orderNum = IDGeneratorUtil.getID("ZX");
            request.getSession().setAttribute("ORDER_NUM", orderNum);  //订单编号
            request.getSession().setAttribute("phone", user.getPhone());
            request.getSession().setAttribute("orderNum", orderNum);
            String subject = user.getClassItem();
            float price = user.getPrice();
            String params = orderNum + "," + user.getPhone() + "," + user.getName() + "," + user.getClassItem() + "," + price;
            //支付携带参数，会原样返回
            String passback_params = java.net.URLEncoder.encode(params,"UTF-8");
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                    "xxxxxxxx",
                    "xxxxxxxxxxxxxxxxxxxxxx",
                    "json", "utf-8",
                    "xxxxxxxxxxxxxxxxx",
                    "RSA2");
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            //通知地址
            alipayRequest.setReturnUrl("http://127.0.0.1/");
            //支付成功后调用地址，支付成功后的业务要写在该逻辑中
            alipayRequest.setNotifyUrl("https://127.0.0.1/page/success.do");
            alipayRequest.setBizContent("{    \"out_trade_no\":\"" + orderNum + "\"," +
                    "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," + "    \"total_amount\":\"" + price + "\"," +
                    "    \"subject\":\"" + subject + "\"," + "    \"body\":\"" + subject + "\"," +
                    "    \"passback_params\":\" " + passback_params + " \"," +
                    "    \"extend_params\":{" + "    \"sys_service_provider_id\":\"xxxxxxxx\"" + "    }," +
                    "    \"timeout_express\":\"30m\"" + "  }");

            String form = ((AlipayTradePagePayResponse)alipayClient.pageExecute(alipayRequest)).getBody();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取微信支付状态，以流的方式返回结果
     * @param request
     * @param response
     */
    @RequestMapping({"/paystate"})
    public void payState(HttpServletRequest request, HttpServletResponse response) {
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        try {

            String out_trade_no = request.getParameter("out_trade_no");
            String appid = PayConfigUtil.APP_ID;  // appid
            String mch_id = PayConfigUtil.MCH_ID; // 商业号
            String key = PayConfigUtil.API_KEY; // key

            String nonce_str = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            // 回调接口
            packageParams.put("appid",appid);
            packageParams.put("mch_id",mch_id);
            packageParams.put("nonce_str",nonce_str);
            packageParams.put("out_trade_no",out_trade_no);
            String sign = PayToolUtil.createSign("UTF-8", packageParams,key);
            packageParams.put("sign", sign);
            String requestXML = PayToolUtil.getRequestXml(packageParams);
            String resXml = HttpUtil.postData(PayConfigUtil.PAY_STATE_URL, requestXML);
            Map map = XMLUtil4jdom.doXMLParse(resXml);
            String return_code = (String)map.get("return_code");
            if("SUCCESS".equals(return_code)){
                String result_code = (String)map.get("result_code");
                String trade_state = (String)map.get("trade_state");
                if("SUCCESS".equals(result_code) && "SUCCESS".equals(trade_state)){
                    response.getWriter().write("ok");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("no");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    @RequestMapping("/getWxCode")
    public String getWxCode(HttpServletRequest request, HttpServletResponse response) {
        return "wxcode";
    }

    /**
     * 获取用户IP地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


}


