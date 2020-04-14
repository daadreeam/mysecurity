package com.lsw.mainservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value = "lsw-msg",configuration = FeignInterceptorConfig.class, fallback = MsgFeignFallback.class)
//@RequestMapping("/msg")
@Component
public interface MsgFeignInterface {

    @GetMapping("/msg/test")
    String test();

    @GetMapping("/msg/code")
    String getCode();
}
