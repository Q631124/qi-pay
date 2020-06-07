package com.pay.action.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.util.identity.ClassItemUtil;
import com.pay.util.identity.Enande;
import com.pay.util.wxpay.PayConfigUtil;
import com.pay.util.wxpay.PayToolUtil;
import com.pay.util.wxpay.XMLUtil4jdom;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/page")
public class PageAction {
    private static Logger logger = Logger.getLogger(PageAction.class);

    @RequestMapping("/package")
    public String pack(HttpServletRequest request, HttpServletResponse response)
    {
        return "package";
    }

    @RequestMapping("/confirm")
    public String confirm(HttpServletRequest request, HttpServletResponse response) {
        return "confirm";
    }

    @RequestMapping("/payorder")
    public String payorder(HttpServletRequest request, HttpServletResponse response) {
        return "payorder";
    }

    @RequestMapping("/paysuccess")
    public String paysuccess(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

    /**
     *  支付宝支付成功返回
     * @param request
     * @param response
     */
    @RequestMapping("/success")
    public void success(HttpServletRequest request, HttpServletResponse response) {
        String passback_params = request.getParameter("passback_params");
        String[] params = null;
        try {
            if(!"".equals(passback_params) && passback_params != null){
                passback_params = java.net.URLDecoder.decode(passback_params,"UTF-8");
                params = passback_params.split(",");
            }
            addUser(params,request);
            response.getWriter().print("success");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().print("fail");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * 微信支付成功返回
     * @param request
     * @param response
     */
    @RequestMapping(value="/wxSuccess")
    public void wxSuccess(HttpServletRequest request, HttpServletResponse response) {
        String[] params = null;
        BufferedReader in = null;
        InputStream inputStream = null;
        BufferedOutputStream out = null;
        try{
            //读取参数
            StringBuffer sb = new StringBuffer();
            inputStream = request.getInputStream();

            String s = "";
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null){
                sb.append(s);
            }
            in.close();
            inputStream.close();
            //解析xml成map
            Map<String, String> map = new HashMap<String, String>();
            map = XMLUtil4jdom.doXMLParse(sb.toString());

            //过滤空 设置 TreeMap
            SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = map.get(parameter);

                String value = "";
                if(null != parameterValue) {
                    value = parameterValue.trim();
                }
                packageParams.put(parameter, value);
            }

            // 账号信息
            String key = PayConfigUtil.API_KEY; //key

            //判断签名是否正确
            if(PayToolUtil.isTenpaySign("UTF-8", packageParams,key)) {
                String resXml = "";
                if("SUCCESS".equals((String)packageParams.get("result_code"))){
                    String attach = (String)packageParams.get("attach");
                    if(!"".equals(attach) && attach != null){
                        attach = java.net.URLDecoder.decode(attach,"UTF-8");
                        params = attach.split(",");
                    }
                    addUser(params,request);

                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                } else {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }

                out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            } else{
                logger.info("通知签名验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addUser(String[] params,HttpServletRequest request) throws Exception {
        String orderNum = "";  //订单号
        String phone = "";      //学员电话
        String name = "error";       //学员姓名
        String classItem = "114";   //课程编号
        String sellName = "David.zhu";   //销售姓名
        String sellId = "TL0102";    //销售Id
        Float price = 0.0F;

        if(params != null && params.length > 2){
            orderNum = params[0];
            phone = params[1];
            name = params[2];
            classItem = params[3];
            price = Float.valueOf(params[4]);

            request.getSession().setAttribute("ORDER_NUM", orderNum);
            request.getSession().setAttribute("orderNum", orderNum);
            request.setAttribute("orderNum",orderNum);
            request.setAttribute("phone",phone);
        }

    }

}


