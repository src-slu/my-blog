package com.ls.springcloud.mqpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MqPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqPayApplication.class, args);
    }

}
