package com.mapabc.signal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [SpringBoot启动类]
 * Created on 2019/4/16 11:24
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.mapabc"})
@ServletComponentScan(basePackages = {"com.mapabc"})
@MapperScan(basePackages = "com.mapabc.signal.dao.mapper")
@EnableScheduling
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

