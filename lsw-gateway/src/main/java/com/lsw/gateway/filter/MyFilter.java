package com.lsw.gateway.filter;

import cn.hutool.json.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 登陆过滤器
 */
@Component
public class MyFilter extends ZuulFilter {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${myFilter.path.login}")
    private String LOGIN_PATH;

    @Override
    public String filterType() {
        System.out.println("zuul过滤器 filterType");
        return "pre";
    }

    @Override
    public int filterOrder() {
        System.out.println("zuul过滤器 filterOrder");
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("zuul过滤器 shouldFilter");
        // 什么要拦截什么不要拦截
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // 不要拦截的接口
        if (request.getRequestURI().endsWith(LOGIN_PATH)) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // todo 登陆校验逻辑可以写在这里
        System.out.println("zuul过滤器 run");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        System.out.println("request.getRequestURI().endsWith(TOKEN_PATH) = " + request.getRequestURI().endsWith(LOGIN_PATH));
//        System.out.println("request.getParameter(\"lsw\") = " + request.getParameter("lsw"));
        String mytoken = request.getHeader("zuulToken");
        System.out.println("zuulToken = " + mytoken);
        /**
         * todo 校验逻辑
         * 1。auth服务的login logout等可以直接放行
         * 2。除此之外的要进行token校验 校验成功的可以将请求放行到微服务 各微服务之间不用在对token进行解析校验了？直接去去redis内容？
         *  2。1 如果token认证通过-》放行
         *  2。2 token校验不通过 -》返回权限不够
         *  todo 2.2 校验token不通过且redis里边没有的就是校验不通过，如果redis里边有的就要刷新token
         */

        if (mytoken != null && !mytoken.equals("zuulToken")) {
            // 可以注入bean
            String forObject = restTemplate.getForObject("http://www.baidu.com", String.class, (Object) null);
            System.out.println("forObject = " + forObject);
            // 校验不通过
            response.setCharacterEncoding("utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",200);
            jsonObject.put("msg", "身份认证失败");
            jsonObject.put("data", "unauthorized token");
            response.setContentType("application/json");
            currentContext.setResponseBody(jsonObject.toStringPretty());
            currentContext.setResponse(response);
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            // todo 这里设置为false之后就没有再到其他服务去了
            currentContext.setSendZuulResponse(false);
            System.out.println("currentContext.sendZuulResponse() = " + currentContext.sendZuulResponse());
        }
        System.out.println("request = " + request);
        return null;
    }


//    @Override
//    public Object run() throws ZuulException {
//        // todo登陆校验逻辑可以写在这里
//        System.out.println("zuul过滤器 run");
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//        System.out.println("request.getRequestURI() = " + request.getRequestURI());
//        System.out.println("request.getContextPath() = " + request.getContextPath());
//        System.out.println("request.getMethod() = " + request.getMethod());
//        System.out.println("request.getHeaderNames() = " + request.getHeaderNames());
//        Enumeration<String> headerNames = request.getHeaderNames();
//        System.out.println("request.getParameter(\"lsw\") = " + request.getParameter("lsw"));
//        String mytoken = request.getHeader("mytoken");
//        System.out.println("mytoken = " + mytoken);
//        if (mytoken != null && !mytoken.equals("lswtoken")) {
//            currentContext.setResponseStatusCode(222);
//            currentContext.setResponseBody("你的token校验没通过");
//            currentContext.sendZuulResponse();
//        }
//        System.out.println("request = " + request);
//        return null;
//    }
}
