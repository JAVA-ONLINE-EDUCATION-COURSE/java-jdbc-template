package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Component
public class JdbcTemplatePublicAuction implements PublicAuction {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatePublicAuction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bid> getUserBids(long id) {
        return jdbcTemplate.query("select * from bids where user_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Bid.class));
    }

    @Override
    public List<Item> getUserItems(long id) {
        return jdbcTemplate.query("select * from items where user_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public Item getItemByName(String name) {
        return jdbcTemplate.queryForObject("select * from items where title like '%?%'", new Object[]{name}, new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public Item getItemByDescription(String name) {
        return jdbcTemplate.queryForObject("select * from items where description like '%?%'", new Object[]{name}, new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        Map<User, Double> userAvgItemCost = new HashMap<>();
        List<User> users = jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<>(User.class));
        for (User user : users) {
            Double price = jdbcTemplate.queryForObject("select avg(start_price) from items where user_id = ?", Double.class, user.getUserId());
            userAvgItemCost.put(user, price);
        }
        return userAvgItemCost;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        Map<Item, Bid> maxBidsForEveryItem = new HashMap<>();
        List<Item> items = jdbcTemplate.query("select * from items", new BeanPropertyRowMapper<>(Item.class));
        for (Item item : items) {
            String sqlMaxBidValue = "select max(bid_value) from bids where item_id=?";
            Double maxBidValue = jdbcTemplate.queryForObject(sqlMaxBidValue, Double.class, item.getItemId());
            if (maxBidValue == null) continue;

            String sqlBidWithMaxValue = "select * from bids where bid_value = ?";
            Bid bid = jdbcTemplate.queryForObject(sqlBidWithMaxValue, new BeanPropertyRowMapper<>(Bid.class), maxBidValue.toString());
            maxBidsForEveryItem.put(item, bid);
        }
        return maxBidsForEveryItem;
    }

    @Override
    public boolean createUser(User user) {
        int creteUser = jdbcTemplate.update("insert into users values (?, ?, ?, ?, ?)",
                user.getUserId(), user.getBillingAddress(), user.getFullName(), user.getLogin(), user.getPassword());
        return creteUser > 0;
    }

    @Override
    public boolean createItem(Item item) {
        int createItem = jdbcTemplate.update("insert into items values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                item.getItemId(), item.getBidIncrement(), item.getBuyItNow(), item.getDescription(),
                item.getStartDate(), item.getStartPrice(), item.getStopDate(), item.getTitle(), item.getUserId());
        return createItem > 0;
    }

    @Override
    public boolean createBid(Bid bid) {
        int createBid = jdbcTemplate.update("insert into bids values (?, ?, ?, ?, ?)",
                bid.getBidId(), bid.getBidDate(), bid.getBidValue(), bid.getItemId(), bid.getUserId());
        return createBid > 0;
    }

    @Override
    public boolean deleteUserBids(long id) {
        int deleteUsersBids = jdbcTemplate.update("delete from bids where user_id=?", id);
        return deleteUsersBids > 0;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        int priceUpdate = jdbcTemplate.update("update items set start_price = start_price * 2 where user_id=?", id);
        return priceUpdate > 0;
    }
}
