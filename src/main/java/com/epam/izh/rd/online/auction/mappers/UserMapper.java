package com.epam.izh.rd.online.auction.mappers;

import com.epam.izh.rd.online.auction.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("user_id"));
        user.setPassword(resultSet.getString("password"));
        user.setBillingAddress(resultSet.getString("billing_address"));
        user.setLogin(resultSet.getString("login"));
        user.setFullName(resultSet.getString("full_name"));
        return user;
    }
}
