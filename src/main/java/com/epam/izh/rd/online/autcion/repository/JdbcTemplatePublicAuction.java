package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplatePublicAuction implements PublicAuction {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private BidMapper bidMapper;
    private ItemMapper itemMapper;
    private UserMapper userMapper;

    public JdbcTemplatePublicAuction(JdbcTemplate jdbcTemplate, BidMapper bidMapper, ItemMapper itemMapper, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bidMapper = bidMapper;
        this.itemMapper = itemMapper;
        this.userMapper = userMapper;
        this.dataSource = jdbcTemplate.getDataSource();
    }

    @Override
    public List<Bid> getUserBids(long id) {
        String sql = "SELECT * FROM BIDS WHERE USER_ID=" + id;


        return jdbcTemplate.query(sql, bidMapper);

    }

    @Override
    public List<Item> getUserItems(long id) {
        String sql = "SELECT * FROM ITEMS WHERE USER_ID=" + id;
        return jdbcTemplate.query(sql, itemMapper);
    }

    @Override
    public Item getItemByName(String name) {
        String sql = "SELECT * FROM ITEMS WHERE title like ? ";
        return jdbcTemplate.queryForObject(sql, itemMapper, name);
    }

    @Override
    public Item getItemByDescription(String name) {
        String sql = "SELECT * FROM ITEMS WHERE description like ?";
        return jdbcTemplate.queryForObject(sql, itemMapper, name);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        Map<User, Double> usersAndAvgCost = new HashMap<>();
        List<User> users = jdbcTemplate.query("select * from users", userMapper);
        for (User user : users) {
            String sql = "select avg(start_price) from items where user_id=?";
            Double price = jdbcTemplate.queryForObject(sql, Double.class, user.getUserId());
            if (price == null) {
                continue;
            }

            usersAndAvgCost.put(user, price);

        }
        return usersAndAvgCost;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        Map<Item, Bid> maxBidForEveryItem = new HashMap<>();
        List<Item> items = jdbcTemplate.query("select * from items", itemMapper);

        for (Item item : items) {

            String sqlGetMaxPrice = "select max(bid_value)  from bids where item_id=?";

            Double maxPrice = jdbcTemplate.queryForObject(sqlGetMaxPrice, Double.class, item.getItemId());
            if (maxPrice == null) {
                continue;
            }

            String sqlGetBidWithMaxPrice = "select * from bids where bid_value=?";
            Bid bidWithMaxPrice = jdbcTemplate.queryForObject(sqlGetBidWithMaxPrice, bidMapper, maxPrice.toString());

            maxBidForEveryItem.put(item, bidWithMaxPrice);
        }
        return maxBidForEveryItem;
    }

    @Override
    public boolean createUser(User user) {
        String sql = "insert into users values(" + user.getUserId() + ",'" + user.getBillingAddress() + "','" + user.getFullName() + "','" + user.getLogin() + "','" + user.getPassword() + "')";

        jdbcTemplate.execute(sql);

        return true;

    }

    @Override
    public boolean createItem(Item item) {
        String sql = "insert into items values('" + item.getItemId() + "','" + item.getBidIncrement() + "','" + item.getBuyItNow() + "','"
                + item.getDescription() + "','" + item.getStartDate() + "','" + item.getStartPrice() + "','" + item.getStopDate() + "','"
                + item.getTitle() + "','" + item.getUserId() + "')";
        jdbcTemplate.execute(sql);
        return true;
    }

    @Override
    public boolean createBid(Bid bid) {
        String sql = "insert into bids values('" + bid.getBidId() + "','" + bid.getBidDate() + "','" + bid.getBidValue() + "','"
                + bid.getItemId() + "','" + bid.getUserId() + "')";
        jdbcTemplate.execute(sql);
        return true;

    }

    @Override
    public boolean deleteUserBids(long id) {
        String sql = "delete from bids where user_id=" + id;
        jdbcTemplate.execute(sql);
        return true;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        String sql = "update items set start_price=start_price*2 where user_id=" + id;
        jdbcTemplate.execute(sql);
        return true;
    }
}
