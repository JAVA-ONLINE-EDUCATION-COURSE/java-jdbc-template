package com.epam.izh.rd.online.auction.repository;

import com.epam.izh.rd.online.auction.entity.Bid;
import com.epam.izh.rd.online.auction.entity.Item;
import com.epam.izh.rd.online.auction.entity.User;
import com.epam.izh.rd.online.auction.mappers.BidMapper;
import com.epam.izh.rd.online.auction.mappers.ItemMapper;
import com.epam.izh.rd.online.auction.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplatePublicAuction implements PublicAuction {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePublicAuction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Bid> getUserBids(long id) {

        return jdbcTemplate.query("SELECT * FROM bids WHERE user_id=?;", new Object[]{id}, new BidMapper());
    }

    @Override
    public List<Item> getUserItems(long id) {
        return jdbcTemplate.query("SELECT * FROM items WHERE user_id=?", new Object[]{id}, new ItemMapper());
    }

    @Override
    public Item getItemByName(String name) {
        return jdbcTemplate.query("SELECT * FROM items WHERE title LIKE ?", new Object[]{name}, new ItemMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public Item getItemByDescription(String name) {
        return jdbcTemplate.query("SELECT * FROM items WHERE description LIKE ?", new Object[]{name}, new ItemMapper()).stream().findAny().orElse(null);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        Map<User, Double> map = new HashMap<>();
        List<User> users = jdbcTemplate.query("SELECT * FROM users", new UserMapper());
        for (User user : users) {
            if (jdbcTemplate.queryForObject("SELECT AVG(start_price) FROM items WHERE user_id=?", new Object[]{user.getUserId()}, Double.class) != null) {
                map.put(user, jdbcTemplate.queryForObject("SELECT AVG(start_price) FROM items WHERE user_id=?", new Object[]{user.getUserId()}, Double.class));
            }
        }
        return map;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        Map<Item, Bid> map = new HashMap<>();
        List<Item> items = jdbcTemplate.query("SELECT * FROM items", new ItemMapper());
        for (Item item : items) {
            if (jdbcTemplate.query("SELECT * FROM bids WHERE (bid_value = SELECT MAX(bid_value) FROM bids WHERE item_id = ?) ", new Object[]{item.getItemId()}, new BidMapper()).stream().findAny().orElse(null) != null) {
                map.put(item, jdbcTemplate.query("SELECT * FROM bids WHERE (bid_value = SELECT MAX(bid_value) FROM bids WHERE item_id = ?) ", new Object[]{item.getItemId()}, new BidMapper()).stream().findAny().orElse(null));
            }
        }
        return map;
    }

    @Override
    public List<Bid> getUserActualBids(long id) {
        return jdbcTemplate.query("SELECT * FROM bids INNER JOIN items ON bids.item_id = items.item_id WHERE items.buy_it_now = true AND items.user_id=?", new Object[]{id}, new BidMapper());
    }

    @Override
    public boolean createUser(User user) {
        return jdbcTemplate.update("INSERT INTO users VALUES(?,?,?,?,?)", user.getUserId(), user.getBillingAddress(), user.getFullName(), user.getLogin(), user.getPassword()) > 0;
    }

    @Override
    public boolean createItem(Item item) {
        return jdbcTemplate.update("INSERT INTO items VALUES(?,?,?,?,?,?,?,?,?)", item.getItemId(), item.getBidIncrement(), item.getBuyItNow(), item.getDescription(), item.getStartDate(), item.getStartPrice(), item.getStopDate(), item.getTitle(), item.getUserId()) > 0;
    }

    @Override
    public boolean createBid(Bid bid) {
        return jdbcTemplate.update("INSERT INTO bids VALUES(?,?,?,?,?)", bid.getBidId(), bid.getBidDate(), bid.getBidValue(), bid.getItemId(), bid.getUserId()) > 0;
    }

    @Override
    public boolean deleteUserBids(long id) {
        return jdbcTemplate.update("DELETE FROM bids WHERE user_id = ?", id) > 0;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        return jdbcTemplate.update("UPDATE items SET start_price = start_price * 2 WHERE user_id = ?", id) > 0;
    }
}
