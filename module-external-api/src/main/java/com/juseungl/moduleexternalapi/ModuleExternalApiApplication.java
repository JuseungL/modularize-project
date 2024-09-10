package com.juseungl.moduleexternalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.juseungl")
public class ModuleExternalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleExternalApiApplication.class, args);
    }

}
