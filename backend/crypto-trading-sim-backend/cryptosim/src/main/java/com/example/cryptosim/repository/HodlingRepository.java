package com.example.cryptosim.repository;

import com.example.cryptosim.converters.HoldingConverter;
import com.example.cryptosim.entity.HoldingEntity;
import com.example.cryptosim.mapping.HoldingRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utills.model.UserHolding;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HodlingRepository {
    private final JdbcTemplate jdbcTemplate;

    public HodlingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean createHolding(UserHolding holding) {
        HoldingEntity entity = HoldingConverter.convertToEntity(holding);
        String sql = "INSERT INTO holdings (id, crypto_symbol, amount) " +
                "VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                entity.getId(),
                entity.getCrypto(),
                entity.getAmount()
        ) == 1;
    }

    public List<UserHolding> getAllHoldings() {
        String sql = "SELECT * FROM holdings";
        List<HoldingEntity> entities = jdbcTemplate.query(sql, new HoldingRowMapper());
        return entities.stream().map(HoldingConverter::convertToUser).toList();
    }

    public Optional<UserHolding> getHolding(UUID id) {
        try {
            String sql = "SELECT * FROM holdings WHERE id = ?";
            HoldingEntity entity =jdbcTemplate.queryForObject(sql, new HoldingRowMapper(), id);
            return Optional.of(HoldingConverter.convertToUser(entity));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
