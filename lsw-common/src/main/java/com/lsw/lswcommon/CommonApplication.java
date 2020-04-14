package com.lsw.lswcommon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan("com.lsw.lswcommon.redis.*")
public class CommonApplication {
    public static void main(String[] args) {
        System.out.println("common组件可以单独启动 port 9999");
        SpringApplication.run(CommonApplication.class, args);
    }
}
