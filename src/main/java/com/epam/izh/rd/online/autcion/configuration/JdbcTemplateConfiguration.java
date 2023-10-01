package com.epam.izh.rd.online.autcion.configuration;

import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfiguration {

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
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
  @ConfigurationProperties("spring.datasource.hikari")
  public DataSource dataSource(DataSourceProperties dataSourceProperties) {
    return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean
  public DataSourceProperties dataSourceProperties(){
    return new DataSourceProperties();
  }
}
