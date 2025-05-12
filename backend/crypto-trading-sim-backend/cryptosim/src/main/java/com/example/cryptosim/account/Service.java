package com.example.cryptosim.account;

import utills.model.Account;

import java.util.UUID;

public interface Service {
    boolean updateBalance(double amount);
    double getBalance();
    boolean resetAccount();
    Account getAccount(UUID accountId);
}
