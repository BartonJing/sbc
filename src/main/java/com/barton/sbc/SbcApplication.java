package com.barton.sbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@SpringBootApplication
public class SbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbcApplication.class, args);
    }
}
