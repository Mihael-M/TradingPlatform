package utills.model;

import com.example.cryptosim.entity.HoldingEntity;

import java.util.List;

public class Account {
    private String id;
    private Double balance;
    private String email;

    public Account(String id,Double balance, String email) {
        this.id = id;
        this.balance = balance;
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
