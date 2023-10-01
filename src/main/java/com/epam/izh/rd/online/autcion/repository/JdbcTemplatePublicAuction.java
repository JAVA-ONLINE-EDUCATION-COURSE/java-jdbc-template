package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplatePublicAuction implements PublicAuction {

  private final NamedParameterJdbcTemplate npJdbcTemplate;
  private final BidMapper bidMapper;
  private final ItemMapper itemMapper;

  @Autowired
  public JdbcTemplatePublicAuction(
      NamedParameterJdbcTemplate npJdbcTemplate, BidMapper bidMapper, ItemMapper itemMapper) {
    this.npJdbcTemplate = npJdbcTemplate;
    this.bidMapper = bidMapper;
    this.itemMapper = itemMapper;
  }

  @Override
  public List<Bid> getUserBids(long id) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    String sqlQuery = "SELECT * FROM bids WHERE user_id = :id;";
    return npJdbcTemplate.query(sqlQuery, sqlParameterSource, bidMapper);
  }

  @Override
  public List<Item> getUserItems(long id) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    String sqlQuery = "SELECT * FROM items WHERE user_id = :id;";
    return npJdbcTemplate.query(sqlQuery, sqlParameterSource, itemMapper);
  }

  @Override
  public Item getItemByName(String name) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", name);
    String sqlQuery = "SELECT * FROM items WHERE title = :name;";
    return npJdbcTemplate.queryForObject(sqlQuery, sqlParameterSource, itemMapper);
  }

  @Override
  public Item getItemByDescription(String description) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("description", description);
    String sqlQuery = "SELECT * FROM items WHERE description = :description;";
    return npJdbcTemplate.queryForObject(sqlQuery, sqlParameterSource, itemMapper);
  }

  @Override
  public Map<User, Double> getAvgItemCost() {
    String sqlQuery =
        "SELECT users.user_id, users.billing_address, users.full_name, users.login, users.password, AVG(items.start_price) FROM items JOIN users ON items.user_id = users.user_id GROUP BY items.user_id, users.user_id, users.billing_address, users.full_name, users.login, users.password;";
    Map<User, Double> resultMap = new HashMap<>();
    npJdbcTemplate.query(
        sqlQuery,
        rowCallbackHandler -> {
          User user = new UserMapper().mapRow(rowCallbackHandler, rowCallbackHandler.getRow());
          double avgItemCost;
          avgItemCost = rowCallbackHandler.getDouble("avg");
          resultMap.put(user, avgItemCost);
        });
    return resultMap;
  }

  @Override
  public Map<Item, Bid> getMaxBidsForEveryItem() {
    String sqlQuery =
        "SELECT bids.bid_id, bids.bid_date, bids.bid_value, bids.item_id bids_item_id, bids.user_id as bids_user_id, items.item_id as items_item_id, items.bid_increment, items.buy_it_now, items.description, items.start_date, items.start_price, items.stop_date, items.title, items.user_id as items_user_id FROM bids JOIN items ON bids.item_id = items.item_id WHERE bids.bid_value IN (SELECT MAX(bid_value) AS bid_value FROM bids GROUP BY item_id);";
    Map<Item, Bid> resultMap = new HashMap<>();
    npJdbcTemplate.query(
        sqlQuery,
        rowCallbackHandler -> {
          Bid bid = new BidMapper().mapRow(rowCallbackHandler, "bids_");
          Item item = new ItemMapper().mapRow(rowCallbackHandler, "items_");
          resultMap.put(item, bid);
        });
    return resultMap;
  }

  public List<Bid> getUserActualBids(long userID) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("userID", userID);
    String sqlQuery = "SELECT * FROM bids WHERE user_id = :userID;";
    return npJdbcTemplate.query(sqlQuery, sqlParameterSource, bidMapper);
  }

  @Override
  public boolean createUser(User user) {
    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource()
            .addValue("userId", user.getUserId())
            .addValue("userFullName", user.getFullName())
            .addValue("userBilling", user.getBillingAddress())
            .addValue("userLogin", user.getLogin())
            .addValue("userLogin", user.getLogin())
            .addValue("userPassword", user.getPassword());
    String sqlQuery =
        "INSERT INTO users (user_id, full_name, billing_address, login, password) VALUES (:userId, :userFullName, :userBilling, :userLogin, :userPassword);";
    int executedFields = npJdbcTemplate.update(sqlQuery, mapSqlParameterSource);
    return 1 >= executedFields;
  }

  @Override
  public boolean createItem(Item item) {
    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource()
            .addValue("itemId", item.getItemId())
            .addValue("itemTitle", item.getTitle())
            .addValue("itemDescription", item.getDescription())
            .addValue("itemStartPrice", item.getStartPrice())
            .addValue("itemBidIncrement", item.getBidIncrement())
            .addValue("itemStartDate", item.getStartDate())
            .addValue("itemStopDate", item.getStopDate())
            .addValue("itemBuyItNow", item.getBuyItNow())
            .addValue("itemUserID", item.getUserId());
    String sqlQuery =
        "INSERT INTO items (item_id, title, description, start_price, bid_increment, start_date, stop_date, buy_it_now, user_id) VALUES (:itemId, :itemTitle, :itemDescription, :itemStartPrice, :itemBidIncrement, :itemStartDate, :itemStopDate, :itemBuyItNow, :itemUserID)";
    int executedFields = npJdbcTemplate.update(sqlQuery, mapSqlParameterSource);
    System.out.println(executedFields);
    return 1 >= executedFields;
  }

  @Override
  public boolean createBid(Bid bid) {
    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource()
            .addValue("bidId", bid.getBidId())
            .addValue("bidDate", bid.getBidDate())
            .addValue("bidValue", bid.getBidValue())
            .addValue("bidItemID", bid.getItemId())
            .addValue("bidUserID", bid.getUserId());
    String sqlQuery =
        "INSERT INTO bids (bid_id, bid_date, bid_value, item_id, user_id) VALUES (:bidId, :bidDate, :bidValue, :bidItemID, :bidUserID);";
    int executedFields = npJdbcTemplate.update(sqlQuery, mapSqlParameterSource);
    return 1 >= executedFields;
  }

  @Override
  public boolean deleteUserBids(long id) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    String sqlQuery = "DELETE FROM bids WHERE user_id = :id;";
    int executedFields = npJdbcTemplate.update(sqlQuery, sqlParameterSource);
    return 1 >= executedFields;
  }

  @Override
  public boolean doubleItemsStartPrice(long id) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    String sqlQuery = "UPDATE items SET start_price = start_price * 2 WHERE user_id = :id;";
    int executedFields = npJdbcTemplate.update(sqlQuery, sqlParameterSource);
    return 1 >= executedFields;
  }
}
