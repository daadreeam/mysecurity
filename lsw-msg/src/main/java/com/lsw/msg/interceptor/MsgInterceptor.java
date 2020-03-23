package com.lsw.msg.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Slf4j
public class MsgInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("MsgInterceptor 拦截器 preHandle....");

        HandlerMethod handlerMethod =  (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        System.out.println("method.getName() = " + method.getName());
        System.out.println("method.getReturnType().getName() = " + method.getReturnType().getName());

        String mytoken3 = request.getHeader("mytoken3");
        if (!mytoken3.equals("mytoken3")) {
            response.sendError(222,"被拦截器拦截了mytoken3不对");
            response.setHeader("inter", "被拦截了");
            log.info("被拦截器拦截了mytoken3不对");
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("MsgInterceptor 拦截器 postHandle....");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("MsgInterceptor 拦截器 afterCompletion....");
    }
}
