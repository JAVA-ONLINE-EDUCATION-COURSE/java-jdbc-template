package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        Item item = new Item();
        item.setItemId(resultSet.getLong("ITEM_ID"));
        item.setBidIncrement(resultSet.getDouble("BID_INCREMENT"));
        item.setBuyItNow(resultSet.getBoolean("BUY_IT_NOW"));
        item.setDescription(resultSet.getString("DESCRIPTION"));
        item.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
        item.setStartPrice(resultSet.getDouble("START_PRICE"));
        item.setStopDate(resultSet.getDate("STOP_DATE").toLocalDate());
        item.setTitle(resultSet.getString("TITLE"));
        item.setUserId(resultSet.getLong("USER_ID"));

        return item;
    }
}
