package com.lsw.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args) {
        /**
         * 这里整合有一个问题 java.lang.IllegalStateException: availableProcessors is already set to [8], rejecting [8]
         * 说是和netty有冲突
         */
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchApplication.class, args);
        System.out.println("全文检索模块单独启动 端口 8011");
    }

    /**
     * 这个才解决了上边那个问题
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
