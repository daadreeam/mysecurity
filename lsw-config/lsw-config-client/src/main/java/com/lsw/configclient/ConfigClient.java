package com.lsw.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 解决bus kafka 自动更新配置的文章
 * https://blog.csdn.net/miaodichiyou/article/details/104468617/
 */
@SpringBootApplication
public class ConfigClient {
    public static void main(String[] args) {
        System.out.println("config client 启动");
        SpringApplication.run(ConfigClient.class, args);
    }
}
