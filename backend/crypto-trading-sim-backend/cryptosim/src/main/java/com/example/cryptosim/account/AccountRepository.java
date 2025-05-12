package com.example.cryptosim.account;

import com.example.cryptosim.converters.IUUIDConverter;
import com.example.cryptosim.entity.AccountEntity;
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
        String sql = "INSERT INTO account (id, balance, email) VALUES ($1, $2, $3)";
        jdbcTemplate.update(sql,accountEntity.getId(),accountEntity.getBalance(),accountEntity.getEmail());
        return accountEntity;
    }

    public double getBalance()
    {
        String sql = "SELECT balance FROM account WHERE email = $1";
        try {

            return jdbcTemplate.queryForObject(sql, Double.class,"admin@gmail.com");
        }
        catch (NullPointerException e) {
            createAccount();
            return 100000.0;
        }
    }
    public boolean updateBalance(Double balance)
    {
        String sql = "UPDATE account SET balance = balance + $1 WHERE email = $2";
        return jdbcTemplate.update(sql, balance, "admin@gmail.com") >= 1;

    }
    public AccountEntity getAccount(UUID accountId) {
        String sql = "SELECT * FROM account WHERE id = $1";
        try {
            return jdbcTemplate.queryForObject(sql, AccountEntity.class, accountId);
        }
        catch (NullPointerException e) {
            return createAccount();
        }
    }
    public boolean resetAccount()
    {
        String sql = "DELETE FROM account WHERE email = $1";
        return jdbcTemplate.update(sql, "admin@gmail.com") >= 1;
    }
}
