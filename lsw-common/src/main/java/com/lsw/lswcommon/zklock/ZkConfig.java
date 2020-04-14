package com.lsw.lswcommon.zklock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkConfig {

    private final String ZK_URL = "47.93.198.65:2181";

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(ZK_URL, retryPolicy);
        return curatorFramework;
//        return CuratorFrameworkFactory.newClient(
//                wrapperZk.getConnectString(),
//                wrapperZk.getSessionTimeoutMs(),
//                wrapperZk.getConnectionTimeoutMs(),
//                new RetryNTimes(wrapperZk.getRetryCount(), wrapperZk.getElapsedTimeMs()));
    }
}
