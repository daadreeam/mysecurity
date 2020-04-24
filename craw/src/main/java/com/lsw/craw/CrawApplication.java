package com.lsw.craw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(value = {"com.lsw.craw.mapper"})
public class CrawApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawApplication.class, args);
    }
}
