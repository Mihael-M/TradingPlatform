package utills.model;

import utills.model.types.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private final String id;
    private TransactionType type;  // "BUY" or "SELL"
    private String crypto;         // e.g., "BTC"
    private double quantity;       // Amount of cryptocurrency bought/sold
    private double unitPrice;       // Price per unit at the time of transaction
    private double profitLoss;
    private LocalDateTime timestamp; // Timestamp of the transaction
    private String accountId;

    public Transaction(String id, TransactionType type, String crypto, double quantity, double unitPrice,double profitLoss, LocalDateTime timestamp, String accountId) {
        this.id = id;
        this.type = type;
        this.crypto = crypto;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.timestamp = timestamp;
        this.accountId = accountId;
        this.profitLoss = profitLoss;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getCrypto() {
        return crypto;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}