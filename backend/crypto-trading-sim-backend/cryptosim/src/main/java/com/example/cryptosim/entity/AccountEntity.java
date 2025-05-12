package com.example.cryptosim.entity;

import java.util.UUID;

public class AccountEntity {
    private UUID id;
    private Double balance;
    private String email;

    public AccountEntity(UUID id, Double balance, String email) {
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
