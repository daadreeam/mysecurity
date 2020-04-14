package com.lsw.mainservice.feignclient;

import org.springframework.stereotype.Component;

/**
 * 降级后备方法
 */
@Component
public class MsgFeignFallback implements MsgFeignInterface {
    @Override
    public String test() {
        return "feign test方法降级了这是后备方法";
    }

    @Override
    public String getCode() {
        return "feign getcode方法降级了这是后备方法";
    }
}
