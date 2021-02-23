package com.epam.izh.rd.online.auction.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")

public class JdbcTemplateConfiguration {
    @Value("${spring.datasource.driverClassName}")
    private String dataSourceDriverClassName;
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.username}")
    private String dataSourceUserName;
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName(dataSourceDriverClassName);
        dataSource.username(dataSourceUserName);
        dataSource.password(dataSourcePassword);
        dataSource.url(dataSourceUrl);
        return dataSource.build();

    }
}
