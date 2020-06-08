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

import java.util.HashMap;
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
        String sql = "SELECT * FROM items\n" +
                "WHERE description LIKE '%" + name + "%' LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, itemMapper);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        String sql = "SELECT AVG(items.start_price), users.*\n" +
                "FROM items INNER JOIN users ON items.user_id = users.user_id\n" +
                "GROUP BY full_name;";
        Map<User, Double> avgItemCost = new HashMap<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            User user = new User();
            user.setUserId((Long) map.get("USER_ID"));
            user.setBillingAddress((String) map.get("BILLING_ADDRESS"));
            user.setFullName((String) map.get("FULL_NAME"));
            user.setLogin((String) map.get("LOGIN"));
            user.setPassword((String) map.get("PASSWORD"));
            avgItemCost.put(user, (Double) map.get("AVG(ITEMS.START_PRICE)"));
        }
        return avgItemCost;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        String sqlItems = "SELECT items.* FROM items;";
        String sqlBids = "SELECT bids.* FROM bids";
        List<Item> listItem = jdbcTemplate.query(sqlItems, itemMapper);
        List<Bid> listBid = jdbcTemplate.query(sqlBids, bidMapper);
        Map<Item, Bid> maxBids = new HashMap<>();
        double max = 0;
        for (Item item : listItem) {
            Bid bidMax = null;
            for (Bid bid : listBid) {
                if (item.getItemId().equals(bid.getItemId()) && max < bid.getBidValue()) {
                    max = bid.getBidValue();
                    bidMax = bid;
                }
            }
            if (bidMax != null) {
                maxBids.put(item, bidMax);
            }
        }
        return maxBids;
    }

    @Override
    public boolean createUser(User user) {
        String sqlCreateUser = "INSERT INTO users (user_id ,full_name, billing_address, login, password) VALUES " +
                               "(?, ?, ?, ?, ?);";
        return jdbcTemplate.update(sqlCreateUser,
                user.getUserId(),
                user.getFullName(),
                user.getBillingAddress(),
                user.getLogin(),
                user.getPassword()) == 1;

    }

    @Override
    public boolean createItem(Item item) {
       String sqlCreateItem = "INSERT INTO items (item_id, bid_increment, buy_it_now, description, " +
               "start_date, start_price, stop_date, title, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
       return jdbcTemplate.update(sqlCreateItem,
               item.getItemId(),
               item.getBidIncrement(),
               item.getBuyItNow(),
               item.getDescription(),
               item.getStartDate(),
               item.getStartPrice(),
               item.getStopDate(),
               item.getTitle(),
               item.getUserId()) == 1;

    }

    @Override
    public boolean createBid(Bid bid) {
        String sqlCreateBid = "INSERT INTO bids (bid_id, bid_date, bid_value, item_id, user_id) VALUES " +
                "(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sqlCreateBid,
                bid.getBidId(),
                bid.getBidDate(),
                bid.getBidValue(),
                bid.getItemId(),
                bid.getUserId()) == 1;

    }

    @Override
    public boolean deleteUserBids(long id) {
        String sqlDeleteBids = "DELETE FROM bids WHERE bids.user_id = ?;";
        return jdbcTemplate.update(sqlDeleteBids, id) == 1;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        String sqlDoubleStartPrice = "UPDATE items SET start_price = start_price * 2 WHERE user_id = ?";
        return jdbcTemplate.update(sqlDoubleStartPrice, id) == 1;
    }
}
