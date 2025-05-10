package com.example.cryptosim.mapping;

import com.example.cryptosim.entity.TransactionEntity;
import utills.model.types.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<TransactionEntity> {
    @Override
    public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionType type = "SELL".equals(rs.getString("type")) ? TransactionType.SELL : TransactionType.BUY;
        return new TransactionEntity(
                type,
                rs.getString("crypto_symbol"),
                rs.getDouble("quantity"),
                rs.getDouble("unit_price"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}
