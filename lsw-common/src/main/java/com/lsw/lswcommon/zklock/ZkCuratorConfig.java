package com.lsw.lswcommon.zklock;

//@Data
//@Configuration
//@ConfigurationProperties(prefix = "curator")
public class ZkCuratorConfig {

        private int retryCount;

        private int elapsedTimeMs;

        private String connectString;

        private int sessionTimeoutMs;

        private int connectionTimeoutMs;
}
