package com.example.cryptosim.entity;

import utills.model.types.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionEntity {
    private final UUID id;                  // Unique identifier for the transaction
    private TransactionType type;           // Transaction type: "BUY" or "SELL"
    private String cryptoSymbol;            // Symbol for the cryptocurrency (e.g., "BTC")
    private double quantity;                // Quantity of cryptocurrency
    private double unitPrice;
    private LocalDateTime timestamp;               // Timestamp of the transaction

    public TransactionEntity(TransactionType type, String cryptoSymbol, double quantity,
                             double unitPrice,LocalDateTime timestamp) {
        this.id = UUID.randomUUID();  // Automatically generate a unique UUID for each transaction
        this.type = type;
        this.cryptoSymbol = cryptoSymbol;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.timestamp = timestamp;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
