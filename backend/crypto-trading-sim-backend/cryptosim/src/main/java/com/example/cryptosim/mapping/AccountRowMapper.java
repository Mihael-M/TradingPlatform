package com.example.cryptosim.mapping;

import com.example.cryptosim.converters.IUUIDConverter;
import com.example.cryptosim.entity.AccountEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<AccountEntity> {
    private IUUIDConverter uuidConverter;

    public AccountRowMapper(IUUIDConverter uuidConverter) {
        this.uuidConverter = uuidConverter;
    }

    @Override
    public AccountEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AccountEntity(
                uuidConverter.convertFromString(rs.getString("id")),
                rs.getDouble("balance"),
                rs.getString("email")
        );
    }
}
