package com.example.cryptosim.entity;

import java.util.UUID;

public class HoldingEntity {
    private final UUID id;
    private final String crypto;
    private Double quantity;
    private Double totalValue;
    private UUID accountId;

    public HoldingEntity(UUID holdingId,String crypto, Double quantity,Double totalValue, UUID id) {
        this.id = holdingId;
        this.crypto = crypto;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.accountId = id;
    }
    public UUID getId() {
        return id;
    }
    public String getCrypto() {
        return crypto;
    }

    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}