package com.bytedance.crm.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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
        String uri = request.getRequestURI().toLowerCase();
        if(uri.indexOf("login") != -1){
            filterChain.doFilter(request,response);
        }else {
            if(null==session.getAttribute("user")){
                response.sendRedirect(""+request.getContextPath()+"/login.jsp");
            }else {
                filterChain.doFilter(request,response);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
