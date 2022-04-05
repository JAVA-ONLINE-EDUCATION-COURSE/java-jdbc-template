package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.configuration.JdbcTemplateConfiguration;
import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
@Repository
public class JdbcTemplatePublicAuction implements PublicAuction {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatePublicAuction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bid> getUserBids(long id) {
        String query = "SELECT * FROM bids WHERE user_id =?;";
        return jdbcTemplate.query(query, new Object[]{id}, new BidMapper());
    }

    @Override
    public List<Item> getUserItems(long id) {
        String query = "SELECT * FROM items WHERE user_id =?;";
        return jdbcTemplate.query(query, new Object[]{id}, new ItemMapper());
    }

    @Override
    public Item getItemByName(String name) {
        String query = "Select * FROM items WHERE title = ?;";
        return jdbcTemplate.queryForObject(query, new Object[]{name}, new ItemMapper());
    }

    @Override
    public Item getItemByDescription(String name) {
        String queryItems = "SELECT * FROM items WHERE description = ?;";
        return jdbcTemplate.queryForObject(queryItems, new Object[]{name}, new ItemMapper());
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        String queryUser = "SELECT * FROM users;";
        String queryAvgPrice = "SELECT AVG(start_price) FROM items WHERE user_id = ?;";

        Map<User, Double> mapItem =new HashMap<>();
        List<User> listUser = jdbcTemplate.query(queryUser, new UserMapper());

        for(User user : listUser) {
            Double sqlItems = jdbcTemplate.queryForObject(queryAvgPrice, new Object[]{user.getUserId()}, Double.class);
            if(sqlItems != null) {
                mapItem.put(user, sqlItems);
            }
        }
        return mapItem;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        String sqlItems = "SELECT * FROM items where item_id in (SELECT item_id FROM bids)";
        String sqlQueryBids = "SELECT * FROM bids WHERE bid_value = (SELECT MAX(bid_value) FROM bids WHERE item_id = ?);";

        Map<Item, Bid> maxBids = new HashMap<>();
        List<Item> listItems = jdbcTemplate.query(sqlItems, new ItemMapper());

        for(Item item : listItems) {
            Bid bid = jdbcTemplate.queryForObject(sqlQueryBids, new Object[]{item.getItemId()}, new BidMapper());
            if(bid != null) {
                maxBids.put(item, bid);
                }
             }
           return maxBids;
        }


    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO users values(?, ?, ?, ?, ?);";
        if(user == null) {return false;}
        jdbcTemplate.update(query, user.getUserId(), user.getBillingAddress(), user.getFullName(), user.getLogin(), user.getPassword());
        return true;
    }

    @Override
    public boolean createItem(Item item) {
        String query = "INSERT INTO items values(?,?,?,?,?,?,?,?,?);";
        if(item == null) {
            return false;
        }
        jdbcTemplate.update(query, item.getItemId(), item.getBidIncrement(), item.getBuyItNow(),
                             item.getDescription(), item.getStartDate(), item.getStartPrice(),
                       item.getStopDate(), item.getTitle(), item.getUserId());
        return true;
    }

    @Override
    public boolean createBid(Bid bid) {
        String query = "INSERT INTO bids values(?,?,?,?,?)";
        if(bid == null) {
            return false;
        }
        jdbcTemplate.update(query, bid.getBidId(), bid.getBidDate(), bid.getBidValue(), bid.getItemId(), bid.getUserId());
        return true;
    }

    @Override
    public boolean deleteUserBids(long id) {
        String query = "DELETE FROM bids where user_id = ?;";
        if(id == 0) {
            return false;
        }
        jdbcTemplate.update(query, id);
        return true;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        String query = "UPDATE items SET start_price = start_price * 2 where user_id = ?;";
      if(id == 0) {
          return false;
      }
      jdbcTemplate.update(query, id);
        return true;
    }
}
