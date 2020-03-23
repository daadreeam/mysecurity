package com.lsw.lswcommon.redis.lock;

//@Configuration
//@ConfigurationProperties(prefix = "redisson")
//@ConditionalOnProperty("redisson.password")
//@Data
public class RedissonProperties {

    private int timeout = 3000;

    private String address;

    private String password;

    private int database = 0;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

}
