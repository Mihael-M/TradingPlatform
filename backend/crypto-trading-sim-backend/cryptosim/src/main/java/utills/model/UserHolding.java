package utills.model;

public class UserHolding {
    private String crypto;
    private Double amount;

    public UserHolding(String crypto, Double amount) {
        this.crypto = crypto;
        this.amount = amount;
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
    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }
}