package com.lsw.msg.interceptor;

import cn.hutool.json.JSONObject;
import com.lsw.msg.anno.AuthRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;

@Component
@Slf4j
public class AuthRequiredInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AuthRequiredInterceptor 拦截器 preHandle....");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // ①:START 方法注解级拦截器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        AuthRequired methodAnnotation = method.getAnnotation(AuthRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            Enumeration<String> headerNames = request.getHeaderNames();
            String authToken = request.getHeader("authToken");
            if (!methodAnnotation.authName().equals(authToken)) {
                // auth
                System.out.println("请求参数权限: " + authToken + " 实际需要权限: " + methodAnnotation.authName());
                response.setCharacterEncoding("utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", "222");
                jsonObject.put("msg", "failed");
                jsonObject.put("data", "请求参数权限: " + authToken + " 实际需要权限: " + methodAnnotation.authName());
                response.getWriter().write(jsonObject.toJSONString(4));
//                response.sendError(666);
                return false;
            }
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等
            System.out.println("methodAnnotation.authName() = " + methodAnnotation.authName());
            System.out.println("====================================");
            return true;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("AuthRequiredInterceptor 拦截器 preHandle....");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("AuthRequiredInterceptor 拦截器 preHandle....");
    }
}
