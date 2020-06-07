package com.pay.action.servlet;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class QiSessionInterceptor
        extends HandlerInterceptorAdapter {
    private List<String> allowUrls;

    public void setAllowUrls(List<String> allowUrls) {
        this.allowUrls = allowUrls;
    }

    public List<String> getAllowUrls() {
        return this.allowUrls;
    }


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestUrl = request.getRequestURI();
        String orderNum = (String)request.getSession().getAttribute("orderNum");
        String oN = (String)request.getSession().getAttribute("ORDER_NUM");

        boolean isAllow = false;
        for (String url : this.allowUrls) {
            if (requestUrl.endsWith(url)) {
                isAllow = true;
            }
        }

        if ((orderNum != null) && (oN != null) && (orderNum.equals(oN))) {
            isAllow = true;
            request.getSession().removeAttribute("ORDER_NUM");
        }

        if (!isAllow) {
            throw new Exception();
        }

        return super.preHandle(request, response, handler);
    }
}
