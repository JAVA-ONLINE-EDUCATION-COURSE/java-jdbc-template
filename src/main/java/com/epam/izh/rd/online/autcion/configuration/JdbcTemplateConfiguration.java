package com.epam.izh.rd.online.autcion.configuration;

import com.epam.izh.rd.online.autcion.repository.JdbcTemplatePublicAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@ComponentScan("com.epam.izh.rd.online.autcion")
@PropertySource("classpath:application.properties")
public class JdbcTemplateConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.driverClassName}")
    String driver;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.jpa.database-platform}")
    String platform;

    @Value("${spring.h2.console.enabled}")
    String consoleEnabled;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

}
