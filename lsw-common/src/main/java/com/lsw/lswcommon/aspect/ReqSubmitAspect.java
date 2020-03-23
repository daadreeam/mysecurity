package com.lsw.lswcommon.aspect;

import com.lsw.lswcommon.ano.Ide;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 接口幂等性的 -- 分布式锁实现
 */
@Slf4j
@Aspect
@Component
public class ReqSubmitAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.lsw.lswcommon.ano.Ide)")
    public void idePointCut() {
    }

    @Around("idePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 使用分布式锁 机制-实现
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Ide ide = method.getAnnotation(Ide.class);
        int lockSeconds = ide.lockTime();

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();

        String busiId = request.getParameter("busiId");
//        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 获取请求的凭证，本项目中使用的JWT,可对应修改
        String token = request.getParameter("authToken");
        String requestURI = request.getRequestURI();


        // 获取锁
        String lockKey = getIdeKey(token, requestURI);
        RLock lock = redissonClient.getLock(lockKey);
//        log.info("tryLock lockKey = [{}], clientId = [{}]", lockKey, clientId);


//        log.info("tryLock success, lockKey = [{}], clientId = [{}]", lockKey, clientId);
        // 获取锁成功
        Object result;
        try {
            // 加锁
            lock.lock();
            String s = stringRedisTemplate.opsForValue().get(busiId);
            if (s == null) {
                System.out.println("重复请求");
                throw new RuntimeException("重复请求");
            }
            // 执行进程
            result = joinPoint.proceed();

            // 删除业务id的key表示业务已经执行完了
            stringRedisTemplate.delete(busiId);
        } catch (Exception e) {
            System.out.println("过程中出错了");
            e.printStackTrace();
            return "下单失败 过程中出错了 " + e.getMessage();
        } finally {
            // 解锁
            lock.unlock();
            System.out.println("解锁成功");
        }
        return result;
    }

    private String getIdeKey(String token, String requestURI) {
        return token + requestURI;
    }
}
