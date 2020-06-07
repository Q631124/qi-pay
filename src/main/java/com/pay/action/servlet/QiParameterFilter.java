package com.pay.action.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class QiParameterFilter implements Filter
{
    public void init(FilterConfig filterConfig) throws ServletException
    {}

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {}

}

 