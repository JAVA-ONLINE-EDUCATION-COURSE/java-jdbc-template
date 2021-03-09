package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import com.epam.izh.rd.online.autcion.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Repository
public class JdbcTemplatePublicAuction implements PublicAuction {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplatePublicAuction(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Bid> getUserBids(long id) {
        return jdbcTemplate.query("SELECT * FROM bids WHERE user_id=?;", new Object[]{id}, new BeanPropertyRowMapper<>(Bid.class));
    }

    @Override
    public List<Item> getUserItems(long id) {
        return jdbcTemplate.query("SELECT * FROM items WHERE user_id=?;", new Object[]{id}, new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public Item getItemByName(String name) {
        return jdbcTemplate.query("SELECT * FROM items WHERE title LIKE ?", new Object[]{name}, new BeanPropertyRowMapper<>(Item.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Item getItemByDescription(String name) {
        return jdbcTemplate.query("SELECT * FROM items WHERE description LIKE ?", new Object[]{name}, new BeanPropertyRowMapper<>(Item.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {
        String sql = "SELECT AVG(items.start_price),users.user_id,users.billing_address," +
                "users.full_name,users.login,users.password FROM items  JOIN users ON " +
                "(items.user_id = users.user_id) group by users.user_id;";
        Map<User, Double> avgItemCost = new HashMap<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            User user = new User();
            user.setUserId((Long) map.get("user_id"));
            user.setBillingAddress((String) map.get("billing_address"));
            user.setFullName((String) map.get("full_name"));
            user.setLogin((String) map.get("login"));
            user.setPassword((String) map.get("password"));
            avgItemCost.put(user, (Double) map.get("AVG(items.START_PRICE)"));
        }
        return avgItemCost;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        List<Item> itemsList = jdbcTemplate.query("Select * from items", new BeanPropertyRowMapper<>(Item.class));
        List<Bid> bidsList = jdbcTemplate.query("Select * from bids", new BeanPropertyRowMapper<>(Bid.class));
        Map<Item, Bid> maxOfBids = new HashMap<>();
        double maxBid = 0;
        for (int i = 0; i < itemsList.size(); i++) {
            Bid maxBidForUser = null;
            for (int j = 0; j < bidsList.size(); j++) {
                if (itemsList.get(i).getItemId().equals(bidsList.get(j).getItemId())) {
                    if (bidsList.get(j).getBidValue() > maxBid) {
                        maxBid = bidsList.get(j).getBidValue();
                        maxBidForUser = bidsList.get(j);
                    }
                }
            }
            if (maxBidForUser != null) {
                maxOfBids.put(itemsList.get(i), maxBidForUser);
            }
        }

        return maxOfBids;
    }

    @Override
    public boolean createUser(User user) {
        return jdbcTemplate.update("INSERT into users values(?,?,?,?,?)", user.getUserId(), user.getBillingAddress(), user.getFullName(), user.getLogin(),
                user.getPassword()) != 0;
    }

    @Override
    public boolean createItem(Item item) {
        return jdbcTemplate.update("INSERT into items values(?,?,?,?,?,?,?,?,?)", item.getItemId(), item.getBidIncrement(), item.getBuyItNow(),
                item.getDescription(), item.getStartDate(), item.getStartPrice(), item.getStopDate(), item.getTitle(), item.getUserId()) != 0;

    }

    @Override
    public boolean createBid(Bid bid) {
        return jdbcTemplate.update("INSERT into bids values(?,?,?,?,?)", bid.getBidId(), bid.getBidDate(), bid.getBidValue(),
                bid.getItemId(), bid.getUserId()) != 0;
    }

    @Override
    public boolean deleteUserBids(long id) {
        return jdbcTemplate.update("Delete from bids where user_id=?", id) != 0;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        return jdbcTemplate.update("UPDATE items SET Start_price = start_price*2 where user_id=?", id) != 0;
    }
}
