package com.team;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.base.mapper")
@ComponentScan({"com.base","com.team","com.http", "com.service"})
public class TeamApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamApplication.class,args);
    }
}
