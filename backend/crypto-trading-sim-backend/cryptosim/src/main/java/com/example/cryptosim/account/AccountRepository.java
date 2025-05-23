package com.example.cryptosim.account;

import com.example.cryptosim.converters.IUUIDConverter;
import com.example.cryptosim.entity.AccountEntity;
import com.example.cryptosim.mapping.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import utills.model.Account;

import java.util.UUID;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;
    private final IUUIDConverter uuidConverter;

    public AccountRepository(JdbcTemplate jdbcTemplate,IUUIDConverter uuidConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.uuidConverter = uuidConverter;
    }

    public AccountEntity createAccount() {
        AccountEntity accountEntity = new AccountEntity(UUID.randomUUID(),100000.0,"admin@gmail.com");
        String sql = "INSERT INTO account (id, balance, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,accountEntity.getId(),accountEntity.getBalance(),accountEntity.getEmail());
        return accountEntity;
    }

    public double getBalance()
    {
        String sql = "SELECT balance FROM account WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Double.class,"admin@gmail.com");
        }
        catch (org.springframework.dao.EmptyResultDataAccessException e) {
            createAccount();
            return 100000.0;
        }
    }
    public boolean updateBalance(Double balance)
    {
        String sql = "UPDATE account SET balance = balance + ? WHERE email = ?";
        return jdbcTemplate.update(sql, balance, "admin@gmail.com") >= 1;

    }
    public AccountEntity getAccount() {
        String sql = "SELECT id, balance, email FROM account WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new AccountRowMapper(uuidConverter), "admin@gmail.com");
    }
    public boolean resetAccount()
    {
        String deleteTransactionsSql = "DELETE FROM transactions WHERE account_id = (SELECT id FROM account WHERE email = ?)";
        jdbcTemplate.update(deleteTransactionsSql, "admin@gmail.com");

        String deleteHoldingsSql = "DELETE FROM holdings WHERE account_id = (SELECT id FROM account WHERE email = ?)";
        jdbcTemplate.update(deleteHoldingsSql, "admin@gmail.com");

        String sql = "DELETE FROM account WHERE email = ?";
        boolean reset;
        reset = jdbcTemplate.update(sql, "admin@gmail.com") >= 1;
        createAccount();
        return reset;

    }
}
