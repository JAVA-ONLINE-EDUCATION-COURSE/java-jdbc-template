package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        Long userId = resultSet.getLong(1);
        String billingAddress = resultSet.getString(2);
        String fullName = resultSet.getString(3);
        String login = resultSet.getString(4);
        String password = resultSet.getString(5);
        return new User(userId, billingAddress, fullName, login, password);
    }
}
