package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Repository
public class JdbcTemplatePublicAuction implements PublicAuction {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Bid> getUserBids(long id) {
        String sql = "SELECT * \n" +
                "FROM bids WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new Object[]{id} ,bidMapper);
    }

    @Override
    public List<Item> getUserItems(long id) {
        String sql = "SELECT * FROM items WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new Object[]{id}, itemMapper);
    }

    @Override
    public Item getItemByName(String name) {
        String sql = "SELECT * FROM items WHERE title LIKE '%" + name + "%' LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, itemMapper);
    }

    @Override
    public Item getItemByDescription(String name) {
        return new Item();
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        return emptyMap();
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        return emptyMap();
    }

    @Override
    public boolean createUser(User user) {
        return false;
    }

    @Override
    public boolean createItem(Item item) {
        return false;
    }

    @Override
    public boolean createBid(Bid bid) {
        return false;
    }

    @Override
    public boolean deleteUserBids(long id) {
        return false;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        return false;
    }
}
