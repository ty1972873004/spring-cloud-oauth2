package com.hxmec.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 功能描述: 注册中心启动项
 * @auther: Trazen
 * @date: 2020/6/22 10:33
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        log.info("=======启动服务注册中心 eureka-server 服务项目中=======");
        SpringApplication.run(EurekaServerApplication.class, args);
        log.info("=======启动服务注册中心 eureka-server 服务项目成功=======");
    }
}
