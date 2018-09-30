package com.barton.sbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.barton.sbc.dao")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbcApplication.class, args);
    }
}
