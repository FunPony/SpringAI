package com.xjtu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @MapperScan("com.xjtu.mapper")
@SpringBootApplication()
public class SpringAiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiTestApplication.class, args);
    }

}
