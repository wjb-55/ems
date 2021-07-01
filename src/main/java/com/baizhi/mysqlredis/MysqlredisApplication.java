package com.baizhi.mysqlredis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.baizhi.mysqlredis.dao")
@SpringBootApplication
public class MysqlredisApplication {

    public static void main(String[] args) {

        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(MysqlredisApplication.class, args);
    }

}
