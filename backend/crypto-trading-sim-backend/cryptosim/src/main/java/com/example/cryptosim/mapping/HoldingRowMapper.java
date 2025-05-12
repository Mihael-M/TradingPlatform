package com.example.cryptosim.mapping;

import com.example.cryptosim.converters.IUUIDConverter;
import com.example.cryptosim.entity.HoldingEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class HoldingRowMapper implements RowMapper<HoldingEntity> {
    private IUUIDConverter uuidConverter;
    public HoldingRowMapper(IUUIDConverter uuidConverter) {
        this.uuidConverter = uuidConverter;
    }
    @Override
    public HoldingEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HoldingEntity(
               uuidConverter.convertFromString(rs.getString("id")),
                rs.getString("crypto"),
                rs.getDouble("quantity"),
                rs.getDouble("total_value"),
                rs.getDouble("profit_loss"),
                uuidConverter.convertFromString(rs.getString("account_id"))

        );
    }

}
