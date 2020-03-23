package com.lsw.lswcommon.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;

public class RedissonTest {
    public static void main(String[] args) {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(
                        "redis://192.168.1.87:26379",
                        "redis://192.168.1.87:26380",
                        "redis://192.168.1.87:26381")
                .setMasterName("mymaster");
//                .setReadMode(ReadMode.SLAVE)
//                .setFailedAttempts(redisProperties.getSentinel().getFailMax())
//                .setTimeout(redisProperties.getTimeout())
//                .setMasterConnectionPoolSize(redisProperties.getPool().getSize())
//                .setSlaveConnectionPoolSize(redisProperties.getPool().getSize());

//        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
//            serverConfig.setPassword(redisProperties.getPassword());
//        }


        RedissonClient redissonClient = Redisson.create(config);


//        String   masterName = "mymaster";
//
//        Set<String> sentinels = new HashSet<>();
//
//        sentinels.add("192.168.1.87:26379");
//
//        sentinels.add("192.168.1.87:26380");
//
//        sentinels.add("192.168.1.87:26381");
//
//
//
//        JedisSentinelPool pool = new JedisSentinelPool(masterName, sentinels);
//
//        Jedis jedis = pool.getResource();
//
//        jedis.set("key1", "value1");
//        String key1 = jedis.get("key1");
//        System.out.println("key1 = " + key1);
//
//        pool.close();
    }
}
