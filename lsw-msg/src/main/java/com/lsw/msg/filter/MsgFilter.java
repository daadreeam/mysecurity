package com.lsw.msg.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/**")
@Component
public class MsgFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MsgFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String mytoken = req.getHeader("msgToken");
        System.out.println("msgToken = " + mytoken);
        String mytoken2 = req.getHeader("mytoken2");
        if (mytoken2 != null && !mytoken2.equals("msgToken")) {
            resp.sendError(222,"msg服务觉得你没有访问权限");
        }
        System.out.println("MsgFilter doFilter");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("MsgFilter destroy");
    }
}
