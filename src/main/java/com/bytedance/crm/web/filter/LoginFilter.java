package com.bytedance.crm.web.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");
        String requestURI =request.getRequestURI().toLowerCase();
        if(requestURI.contains("login")) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if(null != obj){
           filterChain.doFilter(servletRequest,servletResponse);
           return;
        }
        response.sendRedirect(request.getContextPath()+"/login.jsp");
}

    @Override
    public void destroy() {

    }
}
