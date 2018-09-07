package com.barton.sbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.barton.sbc.dao.auth")
@EnableTransactionManagement
public class SbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbcApplication.class, args);
    }
}
