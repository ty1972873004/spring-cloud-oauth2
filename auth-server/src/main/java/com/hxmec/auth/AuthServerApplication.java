package com.hxmec.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 功能描述:
 * @author  Trazen
 * @date  2020/7/14 13:45
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.hxmec.auth.mapper")
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
