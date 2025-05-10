package com.example.cryptosim.mapping;

import com.example.cryptosim.entity.HoldingEntity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;

public class HoldingRowMapper implements RowMapper<HoldingEntity> {
    @Override
    public HoldingEntity mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
        return new HoldingEntity(
                rs.getString("crypto"),
                rs.getDouble("amount")
        );
    }

}
