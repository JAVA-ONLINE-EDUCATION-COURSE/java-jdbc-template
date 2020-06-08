package com.epam.izh.rd.online.autcion.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.epam.izh.rd.online.autcion")
public class JdbcTemplateConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(environment.getProperty("spring.datasource.driverClassName"));
        dataSourceBuilder.url(environment.getProperty("spring.datasource.url"));
        dataSourceBuilder.username(environment.getProperty("spring.datasource.username"));
        dataSourceBuilder.password(environment.getProperty("spring.datasource.password"));

        return dataSourceBuilder.build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
