package com.example.cryptosim.account;

import utills.model.Account;

public class AccountService implements Service {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;

    }

    @Override
    public boolean updateBalance(double amount) {
        return accountRepository.updateBalance(amount);
    }

    @Override
    public boolean resetAccount() {
        return accountRepository.resetAccount();
    }

    @Override
    public Account getAccount() {
        return
    }
}
