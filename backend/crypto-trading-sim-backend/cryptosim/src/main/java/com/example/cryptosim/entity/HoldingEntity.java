package com.example.cryptosim.entity;

import java.util.UUID;

public class HoldingEntity {
    private final UUID id;
    private final String crypto;
    private Double amount;

    public HoldingEntity(String crypto, Double amount) {
        this.id = UUID.randomUUID();
        this.crypto = crypto;
        this.amount = amount;
    }
    public UUID getId() {
        return id;
    }
    public String getCrypto() {
        return crypto;
    }

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

}