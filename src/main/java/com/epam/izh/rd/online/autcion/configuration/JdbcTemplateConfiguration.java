package com.epam.izh.rd.online.autcion.configuration;

import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import com.epam.izh.rd.online.autcion.repository.JdbcTemplatePublicAuction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
public class JdbcTemplateConfiguration {
    @Autowired
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);

    }

    @Bean
    public DataSource dataSource() {
        ResourceBundle prop = ResourceBundle.getBundle("application");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(prop.getString("spring.datasource.driverClassName"));
        dataSourceBuilder.url(prop.getString("spring.datasource.url"));
        dataSourceBuilder.username(prop.getString("spring.datasource.username"));
        dataSourceBuilder.password(prop.getString("spring.datasource.password"));
        return dataSourceBuilder.build();
    }

    @Bean
    public BidMapper bidMapper() {
        return new BidMapper();
    }

    @Bean
    public ItemMapper itemMapper() {
        return new ItemMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public JdbcTemplatePublicAuction jdbcTemplatePublicAuction(JdbcTemplate jdbcTemplate, BidMapper bidMapper, ItemMapper itemMapper, UserMapper userMapper) {
        return new JdbcTemplatePublicAuction(jdbcTemplate, bidMapper, itemMapper, userMapper);
    }
}
