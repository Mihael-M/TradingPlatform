package utills.model;

import java.util.UUID;

public class Holding {
    private final String id;
    private final String crypto;
    private Double quantity;
    private Double totalValue;
    private String accountId;

    public Holding(String id, String crypto, Double quantity, Double totalValue, String accountId) {
        this.id = id;
        this.crypto = crypto;
        this.accountId = accountId;
        this.totalValue = totalValue;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getCrypto() {
        return crypto;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}