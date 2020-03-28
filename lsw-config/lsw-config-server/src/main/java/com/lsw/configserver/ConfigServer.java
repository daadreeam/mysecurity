package com.lsw.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * springcloud config 参考
 * https://www.jianshu.com/p/997600098e6c
 *
 * 参考了用kafka刷行配置
 * https://www.jianshu.com/p/cc8fa1d58e3f
 *
 * 🌟解决bus kafka 自动更新配置的文章
 * https://blog.csdn.net/miaodichiyou/article/details/104468617/
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}
