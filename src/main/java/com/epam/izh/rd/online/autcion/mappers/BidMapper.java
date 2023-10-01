package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Bid;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidMapper implements RowMapper<Bid> {
  @Override
  public Bid mapRow(ResultSet resultSet, int i) throws SQLException {
    Bid bid = new Bid();
    bid.setBidId(resultSet.getLong("bid_id"));
    bid.setBidDate(resultSet.getDate("bid_date").toLocalDate());
    bid.setBidValue(resultSet.getDouble("bid_value"));
    bid.setItemId(resultSet.getLong("item_id"));
    bid.setUserId(resultSet.getLong("user_id"));
    return bid;
  }

  public Bid mapRow(ResultSet resultSet, String prefix) throws SQLException {
    Bid bid = new Bid();
    bid.setBidId(resultSet.getLong("bid_id"));
    bid.setBidDate(resultSet.getDate("bid_date").toLocalDate());
    bid.setBidValue(resultSet.getDouble("bid_value"));
    bid.setItemId(resultSet.getLong(prefix + "item_id"));
    bid.setUserId(resultSet.getLong(prefix + "user_id"));
    return bid;
  }
}
