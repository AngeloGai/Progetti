package com.example.demo.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class PostgresDataSource {

    //Creazione di una classe HikariDataSource
    @Bean
    @ConfigurationProperties("app.datasource")
    public HikariDataSource HikariDataSource(){
        return DataSourceBuilder
        .create()
        .type(HikariDataSource.class)
        .build();
    }
}
