package com.lsw.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * 参考内容
 * http://www.ityouknow.com/springcloud/2018/02/02/spring-cloud-sleuth-zipkin.html
 * https://www.cnblogs.com/5ishare/p/11592534.html
 *
 * 踩坑
 * americaServer 空指针
 * https://www.cnblogs.com/commissar-Xia/p/11432709.html
 *
 * 解决maven依赖冲突
 * <exclusions>
 *     <exclusion>
 *         <groupId>org.springframework.boot</groupId>
 *         <artifactId>spring-boot-starter-log4j2</artifactId>
 *     </exclusion>
 * </exclusions>
 *
 * # 解决 There is already an existing meter named 'http_server_requests_seconds'
 * # 参考连接：https://www.cnblogs.com/commissar-Xia/p/11432635.html
 *
 *
 * zipkin中没有其他的服务 因为其他的服务引用了mq左右sleuth就发给mq了 能在rabbitmq的默认交换机里边能看到有消息
 * https://www.dazhuanlan.com/2019/10/04/5d976798172eb/
 *
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class SleuthApplication {


    public static void main(String[] args) {
        SpringApplication.run(SleuthApplication.class, args);
    }
}
