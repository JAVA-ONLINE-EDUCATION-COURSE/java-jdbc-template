package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Bid;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BidMapper implements RowMapper<Bid> {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");


    @Override
    public Bid mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Bid bid = new Bid();
        bid.setBidId(resultSet.getLong("BID_ID"));
        bid.setBidDate(resultSet.getDate("BID_DATE").toLocalDate());
        bid.setBidValue(resultSet.getDouble("BID_VALUE"));
        bid.setItemId(resultSet.getLong("ITEM_ID"));
        bid.setUserId(resultSet.getLong("USER_ID"));
        return bid;
    }
}
