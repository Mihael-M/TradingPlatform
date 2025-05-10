package com.example.cryptosim.repository;

import com.example.cryptosim.converters.TransactionConverter;
import com.example.cryptosim.entity.TransactionEntity;
import com.example.cryptosim.mapping.TransactionRowMapper;
import utills.model.UserTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Making a new transaction...

    public boolean createTransaction(UserTransaction transaction) {
        TransactionEntity entity = TransactionConverter.convertToEntity(transaction);
        String sql = "INSERT INTO transactions (id, type, crypto_symbol, quantity, unit_price, total_value, timestamp) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                entity.getId(),
                entity.getType().toString(),
                entity.getCryptoSymbol(),
                entity.getQuantity(),
                entity.getTimestamp()
        ) == 1;
    }

    public List<UserTransaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions";
        List<TransactionEntity> entities = jdbcTemplate.query(sql, new TransactionRowMapper());
        return entities.stream().map(TransactionConverter::convertToUser).toList();
    }

    public Optional<UserTransaction> getTransaction(UUID id) {
        try {
            String sql = "SELECT * FROM transactions WHERE id = ?";
            TransactionEntity entity =jdbcTemplate.queryForObject(sql, new TransactionRowMapper(), id);
            return Optional.of(TransactionConverter.convertToUser(entity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
