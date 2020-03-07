package com.epam.izh.rd.online.autcion.mappers;

import com.epam.izh.rd.online.autcion.entity.Bid;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BidMapper implements RowMapper<Bid> {

    @Override
    public Bid mapRow(ResultSet resultSet, int i) throws SQLException {
        Long bidId = resultSet.getLong(1);
        LocalDate bidDate = resultSet.getDate(2).toLocalDate();
        Double bidValue = resultSet.getDouble(3);
        Long itemId = resultSet.getLong(4);
        Long userId = resultSet.getLong(5);
        return new Bid(bidId, bidDate, bidValue, itemId, userId);

    }
}
