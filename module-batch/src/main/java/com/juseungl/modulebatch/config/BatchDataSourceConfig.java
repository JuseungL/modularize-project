package com.juseungl.modulebatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * BatchDataSourceConfig 클래스는 Spring Boot 애플리케이션에서 두 개의 데이터소스 빈을 정의하는 설정 클래스
 * - 하나는 배치 작업에 사용될 데이터소스
 * - 다른 하나는 주 애플리케이션(비즈니스 로직)에 사용될 데이터소스
 *
 * DataSourceBuilder를 사용하여 데이터소스를 생성 후, 설정 프리픽스를 사용하여 데이터소스 속성을 로드
 */
@Configuration
public class BatchDataSourceConfig {
    @Bean(name = "batchDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-meta")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "coreDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource coreDataSource() {
        return DataSourceBuilder.create().build();
    }
}
