package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet resultSet, int i) throws SQLException {
    User user = new User();
    user.setUserId(resultSet.getLong("user_id"));
    user.setFullName(resultSet.getString("full_name"));
    user.setBillingAddress(resultSet.getString("billing_address"));
    user.setLogin(resultSet.getString("login"));
    user.setPassword(resultSet.getString("password"));
    return user;
  }
}
