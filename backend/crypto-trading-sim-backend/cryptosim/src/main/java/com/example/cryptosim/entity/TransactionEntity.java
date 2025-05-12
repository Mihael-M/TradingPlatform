package com.example.cryptosim.entity;

import utills.model.types.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionEntity {
    private UUID id;                  // Unique identifier for the transaction
    private TransactionType type;           // Transaction type: "BUY" or "SELL"
    private String cryptoSymbol;            // Symbol for the cryptocurrency (e.g., "BTC")
    private double quantity;                // Quantity of cryptocurrency
    private double unitPrice;
    private Double profitLoss;
    private LocalDateTime createdAt;        // Timestamp of the transaction
    private UUID accountId;


    public TransactionEntity(UUID id, TransactionType type, String cryptoSymbol, double quantity,
                             double unitPrice,double profitLoss,LocalDateTime createdAt, UUID accountId) {
        this.id =  id;
        this.type = type;
        this.cryptoSymbol = cryptoSymbol;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.createdAt = createdAt;
        this.accountId = accountId;
        this.profitLoss = profitLoss;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(Double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public void setCryptoSymbol(String cryptoSymbol) {
        this.cryptoSymbol = cryptoSymbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
