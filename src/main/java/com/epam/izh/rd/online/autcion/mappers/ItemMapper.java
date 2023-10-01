package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {

  @Override
  public Item mapRow(ResultSet resultSet, int i) throws SQLException {
    Item item = new Item();
    item.setItemId(resultSet.getLong("item_id"));
    item.setTitle(resultSet.getString("title"));
    item.setDescription(resultSet.getString("description"));
    item.setStartPrice(resultSet.getDouble("start_price"));
    item.setBidIncrement(resultSet.getDouble("bid_increment"));
    item.setStartDate(resultSet.getDate("start_date").toLocalDate());
    item.setStopDate(resultSet.getDate("stop_date").toLocalDate());
    item.setBuyItNow(resultSet.getBoolean("buy_it_now"));
    item.setUserId(resultSet.getLong("user_id"));
    return item;
  }

  public Item mapRow(ResultSet resultSet, String prefix) throws SQLException {
    Item item = new Item();
    item.setItemId(resultSet.getLong(prefix + "item_id"));
    item.setTitle(resultSet.getString("title"));
    item.setDescription(resultSet.getString("description"));
    item.setStartPrice(resultSet.getDouble("start_price"));
    item.setBidIncrement(resultSet.getDouble("bid_increment"));
    item.setStartDate(resultSet.getDate("start_date").toLocalDate());
    item.setStopDate(resultSet.getDate("stop_date").toLocalDate());
    item.setBuyItNow(resultSet.getBoolean("buy_it_now"));
    item.setUserId(resultSet.getLong(prefix + "user_id"));
    return item;
  }
}
