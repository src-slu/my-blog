package com.ls.springcloud;

import com.ls.springcloud.filter.MyZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulApplication {

//    @Bean
//    public MyZuulFilter myZuulFilter(){
//        return new MyZuulFilter();
//    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
