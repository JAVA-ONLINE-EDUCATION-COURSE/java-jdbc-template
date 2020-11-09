package com.epam.izh.rd.online.auction.mappers;

import com.epam.izh.rd.online.auction.entity.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Item(resultSet.getLong("item_id"),
                resultSet.getDouble("bid_increment"),
                resultSet.getBoolean("buy_it_now"),
                resultSet.getString("description"),
                resultSet.getDate("start_date").toLocalDate(),
                resultSet.getDouble("start_price"),
                resultSet.getDate("stop_date").toLocalDate(),
                resultSet.getString("title"),
                resultSet.getLong("user_id"));
    }
    
}
