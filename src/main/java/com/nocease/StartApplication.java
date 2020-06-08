package com.nocease;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
@MapperScan(basePackages = {"com.nocease.mapper"})
@EnableCaching  //开启redis缓存
@ServletComponentScan  //集成过滤器
public class StartApplication {
    /**
     * springBoot项目启动
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
        System.out.println("This project start at：http://127.0.0.1:8080/");
        System.err.println("start SUCCESS!");
    }
}
