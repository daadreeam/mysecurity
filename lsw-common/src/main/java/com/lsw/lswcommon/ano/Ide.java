package com.lsw.lswcommon.ano;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ide{

    /**
     * 设置请求锁定时间,超时后自动释放锁
     *
     * @return
     */
    int lockTime() default 10;

}
