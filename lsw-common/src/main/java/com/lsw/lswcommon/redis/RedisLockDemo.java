package com.lsw.lswcommon.redis;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class RedisLockDemo {

    @Autowired
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static Integer stock = 10;

    @GetMapping("/deduct")
    public Boolean deduct() {
        //
        String key = "dec_store_lock_" + "iphoneid";
        RLock lock = redissonClient.getLock(key);
        try {
            //加锁 操作很类似Java的ReentrantLock机制
            // 所有线程到这里都只有一个线程可以加锁成功
            // 这个代码底层就是实现了redistemplate.ops.setifabsent这种的操作
            lock.lock();

            // 减库存
            if (stock > 0) {
                stock--;
                System.out.println("减库存成功, 当前剩余库存为： " + stock);
            } else {
                System.out.println("减库存失败，当前剩余库存 " + stock);
                return false;
            }
//            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
            //如果库存为空
//            if (productInfo.getProductStock() == 0) {
//                return false;
//            }
            //简单减库存操作 没有重新写其他接口了
//            productInfo.setProductStock(productInfo.getProductStock() - 1);
//            productInfoMapper.updateByPrimaryKey(productInfo);
        } catch (Exception e) {
            System.out.println("减库中出现问题");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                //解锁
                lock.unlock();
            } catch (Exception e) {
                System.out.println("解锁失败");
                e.printStackTrace();
            }
        }
        return true;

    }

    @GetMapping("/ds")
    public String testDs(@RequestParam("key") String key) {
        // string
        String value = "vvvvvv";
        User user = new User("bob", 11);
        stringObjectRedisTemplate.opsForValue().set(key, user);
        stringRedisTemplate.opsForValue().set(key + "t", "sssssss");
        User firstkey = (User) stringObjectRedisTemplate.opsForValue().get(key);
        String str = (String) stringRedisTemplate.opsForValue().get(key + "t");
        String x = key + " = " + firstkey;

        System.out.println(x);
        System.out.println("str = " + str);


        // hash
        stringObjectRedisTemplate.opsForHash().put("hashkey", "hashfield", user);
        stringRedisTemplate.opsForHash().put("hashkey", "hashfield2", "hashfieldvalue2");
        User o = (User) stringObjectRedisTemplate.opsForHash().get("hashkey", "hashfield");
        String o2 = (String) stringRedisTemplate.opsForHash().get("hashkey", "hashfield2");
        System.out.println("o = " + o + "   o2 = " + o2);

        // list

        // set

        // zset


        return JSONUtil.toJsonPrettyStr(o);
    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class User implements Serializable {
    private String name;
    private int age;
}
