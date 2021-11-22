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

import java.util.*;

@Repository
public class JdbcTemplatePublicAuction implements PublicAuction {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BidMapper bidMapper;

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Bid> getUserBids(long id) {

        String sqlQuery = "select * from bids where user_id = ?;";

        return jdbcTemplate.query(sqlQuery, new Object[]{id}, bidMapper);
    }

    @Override
    public List<Item> getUserItems(long id) {

        String sqlQuery = "select * from items where user_id = ?;";
        return jdbcTemplate.query(sqlQuery, new Object[]{id}, itemMapper);
    }

    @Override
    public Item getItemByName(String name) {

        String sqlQuery = "select * from items where title like ? limit 1;";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{"%" + name + "%"}, itemMapper);
    }

    @Override
    public Item getItemByDescription(String name) {

        String sqlQuery = "select * from items where description like ? limit 1;";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{"%" + name + "%"}, itemMapper);
    }

    @Override
    public Map<User, Double> getAvgItemCost() {

        Map<User, Double> mapUserAvgItem = new TreeMap<>((o1, o2) -> o1.getUserId().compareTo(o2.getUserId()));
        String sqlQueryAllUsers = "select * from users;";
        List<User> listUser = jdbcTemplate.query(sqlQueryAllUsers, userMapper);

        for (User user : listUser) {
            String sqlQueryAvgCostByUser = "select AVG(items.start_price) from items where items.user_id = ?";
            Double valueAvgOfCost = jdbcTemplate.queryForObject(sqlQueryAvgCostByUser, new Object[]{user.getUserId()}, Double.class);
            if (valueAvgOfCost != null) {
                mapUserAvgItem.put(user, valueAvgOfCost);
            }
        }
        return mapUserAvgItem;
    }

    @Override
    public Map<Item, Bid> getMaxBidsForEveryItem() {
        String sqlQueryForMaxBidsForEveryItem = "select * from " +
                "bids inner join items " +
                "on items.item_id = bids.item_id " +
                "where bids.bid_value = " +
                "(select max(bids.bid_value) " +
                "from bids where bids.item_id = items.item_id) " +
                "order by items.item_id;";

        return jdbcTemplate.query(sqlQueryForMaxBidsForEveryItem, rs -> {
            Map<Item, Bid> mapItemByBid = new TreeMap<>((o1, o2) -> o1.getItemId().compareTo(o2.getItemId()));

            int i = 0;
            while (rs.next()) {
                mapItemByBid.put(itemMapper.mapRow(rs, i), bidMapper.mapRow(rs, i));
                i++;
            }
            return mapItemByBid;
        });
    }

    /**
     * Добавить нового пользователя
     */
    @Override
    public boolean createUser(User user) {
        String sqlQuery = "insert into users values (?, ?, ?, ?, ?);";

        return jdbcTemplate.update(
                sqlQuery,
                user.getUserId(),
                user.getBillingAddress(),
                user.getFullName(),
                user.getLogin(), user.getPassword()) > 0;
    }

    @Override
    public boolean createItem(Item item) {
        String sqlQuery = "insert into items values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        return jdbcTemplate.update(
                sqlQuery,
                item.getItemId(),
                item.getBidIncrement(),
                item.getBuyItNow(),
                item.getDescription(),
                item.getStartDate(),
                item.getStartPrice(),
                item.getStopDate(),
                item.getTitle(),
                item.getUserId()) > 0;
    }

    /**
     * Добавить новую ставку
     */
    @Override
    public boolean createBid(Bid bid) {
        String sqlQuery = "insert into bids values (?, ?, ?, ?, ?);";
        return jdbcTemplate.update(
                sqlQuery,
                bid.getBidId(),
                bid.getBidDate(),
                bid.getBidValue(),
                bid.getItemId(),
                bid.getUserId()) > 0;
    }

    /**
     * Увеличить стартовые цены товаров данного пользователя вдвое
     */
    @Override
    public boolean deleteUserBids(long id) {
        String sqlQuery = "delete from bids where user_id = ?;";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    /**
     * Увеличить стартовые цены товаров данного пользователя вдвое
     */
    @Override
    public boolean doubleItemsStartPrice(long id) {
        String sqlQuery = "update items as i " +
                "set i.start_price = i.start_price*2 " +
                "where i.user_id = ? ;";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }
}
