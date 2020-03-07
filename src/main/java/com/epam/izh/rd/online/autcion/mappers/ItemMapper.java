package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        Long itemId = resultSet.getLong(1);
        Double bidIncrement = resultSet.getDouble(2);
        Boolean buyItNow = resultSet.getBoolean(3);
        String description = resultSet.getString(4);
        LocalDate startDate = resultSet.getDate(5).toLocalDate();
        Double startPrice = resultSet.getDouble(6);
        LocalDate stopDate = resultSet.getDate(7).toLocalDate();
        String title = resultSet.getString(8);
        Long userId = resultSet.getLong(9);
        return new Item(itemId, bidIncrement, buyItNow, description, startDate, startPrice, stopDate, title, userId);
    }
}
