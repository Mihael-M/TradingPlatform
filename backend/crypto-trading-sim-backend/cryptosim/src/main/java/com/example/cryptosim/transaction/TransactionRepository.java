package com.example.cryptosim.transaction;

import com.example.cryptosim.converters.IUUIDConverter;
import com.example.cryptosim.entity.TransactionEntity;
import com.example.cryptosim.mapping.TransactionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final IUUIDConverter uuidConverter;

    public TransactionRepository(JdbcTemplate jdbcTemplate, IUUIDConverter uuidConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.uuidConverter = uuidConverter;
    }

    public TransactionEntity createTransaction(TransactionEntity entity) {
        String sql = "INSERT INTO transactions (id, type, crypto_symbol, quantity, unit_price, profit_loss, created_at, account_id) VALUES (?, ?::transaction_type, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                entity.getId(),
                entity.getType().toString(),
                entity.getCryptoSymbol(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getProfitLoss(),
                entity.getCreatedAt(),
                entity.getAccountId()
        );
        return entity;
    }

    public List<TransactionEntity> getAllTransactions() {
        String sql = "SELECT id, type, crypto_symbol, quantity, unit_price, profit_loss, created_at, account_id FROM transactions";
        List<TransactionEntity> entities = jdbcTemplate.query(sql, new TransactionRowMapper(uuidConverter));
        return entities.stream().toList();
    }

    public TransactionEntity getTransaction(String id) {
        try {
            String sql = "SELECT id, type, crypto_symbol, quantity, unit_price, profit_loss, created_at,account_id FROM transactions WHERE id = ?";
            TransactionEntity entity = jdbcTemplate.queryForObject(sql, new TransactionRowMapper(uuidConverter), id);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteAllTransactions() {
        String sql = "DELETE FROM transactions";
        return jdbcTemplate.update(sql) >= 1;
    }

}
