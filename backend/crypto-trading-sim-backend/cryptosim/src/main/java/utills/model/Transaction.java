package utills.model;

import utills.model.types.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private TransactionType type;
    private String crypto;
    private double quantity;
    private double unitPrice;
    private double profitLoss;
    private LocalDateTime timestamp;
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

    public void setId(String id) {
        this.id = id;
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