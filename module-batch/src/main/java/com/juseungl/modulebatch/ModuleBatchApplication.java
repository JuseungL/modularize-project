package com.juseungl.modulebatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.juseungl")
public class ModuleBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleBatchApplication.class, args);
    }

}
