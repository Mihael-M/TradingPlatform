package com.example.cryptosim.holding;

import com.example.cryptosim.converters.IUUIDConverter;
import com.example.cryptosim.entity.HoldingEntity;
import com.example.cryptosim.mapping.HoldingRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public class HoldingRepository {
    private final JdbcTemplate jdbcTemplate;
    private IUUIDConverter uuidConverter;
    public HoldingRepository(JdbcTemplate jdbcTemplate, IUUIDConverter uuidConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.uuidConverter = uuidConverter;
    }

    public HoldingEntity createHolding(HoldingEntity holdingEntity) {
        String sql = "INSERT INTO holdings (id, crypto, quantity, total_value, account_id)" +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                holdingEntity.getId(),
                holdingEntity.getCrypto(),
                holdingEntity.getQuantity(),
                holdingEntity.getTotalValue(),
                holdingEntity.getAccountId()
        );
        return holdingEntity;
    }

    public List<HoldingEntity> getAllHoldings() {

        String sql = "SELECT id, crypto, quantity, total_value, account_id FROM holdings";
        List<HoldingEntity> entities = jdbcTemplate.query(sql, new HoldingRowMapper(uuidConverter));
        return entities.stream().toList();
    }

    public HoldingEntity getHolding(UUID id) {
        try {
            String sql = "SELECT id, crypto, quantity, total_value, account_id FROM holdings WHERE id = ?";
            HoldingEntity entity =jdbcTemplate.queryForObject(sql, new HoldingRowMapper(uuidConverter), id);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
    public HoldingEntity getHolding(String crypto) {
        try {
            String sql = "SELECT id, crypto, quantity, total_value, account_id FROM holdings WHERE crypto = ?";
            HoldingEntity entity =jdbcTemplate.queryForObject(sql, new HoldingRowMapper(uuidConverter), crypto);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
    public HoldingEntity updateHolding(HoldingEntity holdingEntity) {
        String sql = "UPDATE holdings SET quantity = ?, total_value = ? WHERE id = ?";
        jdbcTemplate.update(sql, holdingEntity.getQuantity(), holdingEntity.getTotalValue(), holdingEntity.getId());
        return holdingEntity;
    }
    public boolean deleteAllHoldings() {
        String sql = "DELETE FROM holdings";
        return jdbcTemplate.update(sql) >= 1;
    }

    public boolean deleteHolding(UUID id) {
        String sql = "DELETE FROM holdings WHERE id = ?";
        return jdbcTemplate.update(sql, id) >= 1;
    }
}
