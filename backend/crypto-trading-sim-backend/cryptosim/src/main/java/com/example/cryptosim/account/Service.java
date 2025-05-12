package com.example.cryptosim.account;

import utills.model.Account;

public interface Service {
    boolean updateBalance(double amount);
    boolean resetAccount();
    Account getAccount();
}
