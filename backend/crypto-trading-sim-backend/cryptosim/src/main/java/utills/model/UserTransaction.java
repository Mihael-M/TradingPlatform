package utills.model;

import utills.model.types.TransactionType;

import java.time.LocalDateTime;

public class UserTransaction {
    private TransactionType type;  // "BUY" or "SELL"
    private String crypto;         // e.g., "BTC"
    private double quantity;       // Amount of cryptocurrency bought/sold
    private double unitPrice;      // Price per unit at the time of transaction
    private double totalValue;     // Calculated value (quantity * price)
    private LocalDateTime timestamp; // Timestamp of the transaction

    public UserTransaction(TransactionType type, String crypto, double quantity, double unitPrice, LocalDateTime timestamp) {
        this.type = type;
        this.crypto = crypto;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalValue = quantity * unitPrice;
        this.timestamp = timestamp;
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
        recalculateTotal();
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        recalculateTotal();
    }

    public double getTotalValue() {
        return totalValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Method to recalculate total value when quantity or unit price changes
    private void recalculateTotal() {
        this.totalValue = this.quantity * this.unitPrice;
    }
}