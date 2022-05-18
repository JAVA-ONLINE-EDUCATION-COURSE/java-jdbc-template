package com.epam.izh.rd.online.autcion.repository;

import com.epam.izh.rd.online.autcion.entity.Bid;
import com.epam.izh.rd.online.autcion.entity.Item;
import com.epam.izh.rd.online.autcion.entity.User;
import com.epam.izh.rd.online.autcion.mappers.BidMapper;
import com.epam.izh.rd.online.autcion.mappers.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class JdbcTemplatePublicAuction implements PublicAuction {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public List<Bid> getUserBids(long id) {
        String sql = "SELECT * FROM BIDS WHERE USER_ID= ?";
        return jdbcTemplateObject.query(sql, new Object[]{id},new BidMapper());
    }

    @Override
    public List<Item> getUserItems(long id) {

        String sql = "SELECT * FROM ITEMS WHERE USER_ID = ?";
        return jdbcTemplateObject.query(sql,new Object[]{id}, new ItemMapper());
    }

    @Override
    public Item getItemByName(String name) {

        String sql = "SELECT * FROM ITEMS WHERE TITLE LIKE ?";
        return jdbcTemplateObject.queryForObject(sql, new Object[]{name+"%"}, new ItemMapper());
    }

    @Override
    public Item getItemByDescription(String name) {
        String sql = "SELECT * FROM items WHERE DESCRIPTION like ?";
        return jdbcTemplateObject.queryForObject(sql, new Object[]{name+"%"}, new ItemMapper() );
    }


    // функция не реализована.
    @Override
    public Map<User, Double> getAvgItemCost() {
        //SELECT avg(START_PRICE) FROM ITEMS GROUP BY USER_ID
//        Map<User, Double> usersMap = new HashMap<>();
//        Integer usersCount = jdbcTemplateObject.query("SELECT COUNT(USER_ID) FROM USERS" );
//        String sql = "SELECT START_PRICE FROM ITEMS";
//        for(int i =  1; i <=usersCount; i++){
//            usersMap.put(new UserMapper(), jdbcTemplateObject.query(sql) );
//        }


        return Collections.emptyMap();
    }


    // функция не реализована
    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {

        //    String sql = "SELECT ITEM_ID , MAX(bid_value) FROM BIDS GROUP BY ITEM_ID";

        return Collections.emptyMap();
    }


    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO users VALUES (?,?,?,?,?)";
        jdbcTemplateObject.update(sql, user.getUserId(), user.getBillingAddress(),
                user.getFullName(), user.getLogin(), user.getPassword());
        return true;
    }

    @Override
    public boolean createItem(Item item) {
        String sql = "INSERT INTO items VALUES (?,?,?,?,?,?,?,?,?) ";
        jdbcTemplateObject.update(sql, item.getItemId(), item.getBidIncrement(), item.getBuyItNow(),
                item.getDescription(), item.getStartDate(), item.getStartPrice(), item.getStopDate(),
                item.getTitle(), item.getUserId());
        return true;
    }

    @Override
    public boolean createBid(Bid bid) {
        String sql = "INSERT INTO bids VALUES (?,?,?,?,?)";
        jdbcTemplateObject.update(sql, bid.getBidId(), bid.getBidDate(), bid.getBidValue(), bid.getItemId(),
                bid.getUserId());
        return true;
    }

    @Override
    public boolean deleteUserBids(long id) {
        String sql = "DELETE FROM BIDS WHERE USER_ID = ? ";
        jdbcTemplateObject.update(sql, id) ;
        return true;
    }

    @Override
    public boolean doubleItemsStartPrice(long id) {
        String sql = "UPDATE items SET START_PRICE = START_PRICE * 2 WHERE  USER_ID = ? ";
        jdbcTemplateObject.update(sql, id);
        return true;
    }
}
