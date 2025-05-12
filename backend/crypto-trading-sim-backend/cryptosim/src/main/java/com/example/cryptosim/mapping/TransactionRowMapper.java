package com.example.cryptosim.mapping;

import com.example.cryptosim.converters.IUUIDConverter;
import com.example.cryptosim.converters.UUIDConverter;
import com.example.cryptosim.entity.TransactionEntity;
import com.example.cryptosim.generators.UUIDGenerator;
import utills.model.types.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<TransactionEntity> {

    private IUUIDConverter uuidConverter;

    public TransactionRowMapper(IUUIDConverter uuidConverter) {
       this.uuidConverter = uuidConverter;
    }
    @Override
    public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionType type = "SELL".equals(rs.getString("type")) ? TransactionType.SELL : TransactionType.BUY;
        return new TransactionEntity(
                uuidConverter.convertFromString(rs.getString("id")),
                type,
                rs.getString("crypto_symbol"),
                rs.getDouble("quantity"),
                rs.getDouble("unit_price"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                uuidConverter.convertFromString(rs.getString("account_id"))
        );
    }
}
