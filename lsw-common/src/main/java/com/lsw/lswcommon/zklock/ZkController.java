package com.lsw.lswcommon.zklock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zk")
@Slf4j
public class ZkController {

    private static int stock = 20;

    @Autowired
    private CuratorFramework zkClient;

    @GetMapping("/deduct")
    public String deduct() throws Exception {
        String name = Thread.currentThread().getName();
        InterProcessMutex lock = new InterProcessMutex(zkClient, "/mylock");
        try {
//            boolean acquire = lock.acquire(10, TimeUnit.SECONDS);
//            if (acquire) {
            lock.acquire();

            log.info("===========================");
                log.info(name + " 获得锁");
                if (stock <= 0) {
                    String ss = name + " 抢锁成功但是购买失败 当前库存 " + stock;
                    log.info(ss);
//                    lock.release();
                    return ss;
                }

                log.info(name + " 购买中");
                Thread.sleep(500);
                stock--;
                String r = name + " 购买成功 当前库存 " + stock;
                log.info(r);
                return r;
//            } else {
//                String x = name + " 抢锁失败 购买失败";
//                log.info(x);
//                return x;
//            }
        } finally {
            try {
                lock.release();
                log.info("===========================");
            } catch (Exception e) {
                log.error(name + " 释放锁失败 " +  e.getMessage());
//                e.printStackTrace();
            }
        }

    }
}
