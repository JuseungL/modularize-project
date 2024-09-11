package com.juseungl.moduleexternalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.juseungl") // 타 모듈의 빈을 해당 모듈에서도 주입 받기 위해 필
public class ModuleExternalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleExternalApiApplication.class, args);
    }

}
