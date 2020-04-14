package com.lsw.lswcommon.controller;

import com.lsw.lswcommon.ano.Ide;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {


    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static int stock = 10;


    @GetMapping("/getOnlyId")
    public String getOnlyId(){
        String authToken001 = "authToken001";
        String busiId = "/order/busiId/" + authToken001;
//        redissonClient.getLock(authToken001);
        // 将唯一的id存到redis当中
        // 这里的话不管是多少的话都会设置成功因为这一步不管多少个请求设置成功都没关系，只要是后边的主要的操作保证幂等就行
        try {
            stringRedisTemplate.opsForValue().set(busiId,"1106",666, TimeUnit.SECONDS);
            System.out.println("获取唯一业务id 成功 busiId: " + busiId);
            return "获取唯一业务id 成功 busiId: " + busiId;
        } catch (Exception e) {
            System.out.println("获取唯一业务id 失败");
            e.printStackTrace();
            return "获取唯一业务id 失败";
        }
    }

    @GetMapping("/order")
    public String order(@RequestParam("busiId") String busiId, HttpServletRequest request,
                        @RequestParam("authToken") String authToken){

//        String authToken = request.getHeader("authToken");
        String lockKey = "/order/" + authToken;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            lock.lock();
            String string = stringRedisTemplate.opsForValue().get(busiId);
            if (string == null) {
                String msg = "下单失败 您这是重复操作啊";
                System.out.println(msg);
                return msg;
            }
            stock--;
            // 删除唯一的业务id
            stringRedisTemplate.delete(busiId);
            String msg2 = "下单成功，当前剩余库存为 " + stock;
            System.out.println(msg2);
            return msg2;
        } catch (Exception e) {
            System.out.println("redisson加锁失败");
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return "";
    }

    @GetMapping("/order2")
    @Ide
    public String order2(@RequestParam("busiId") String busiId, HttpServletRequest request,
                        @RequestParam("authToken") String authToken){

//        String authToken = request.getHeader("authToken");
//        String lockKey = "/order/" + authToken;
//        RLock lock = redissonClient.getLock(lockKey);

        stock--;
        System.out.println("减库存成功 剩余库存 " + stock);
        return "";
    }
}
