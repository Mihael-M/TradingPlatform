package com.example.cryptosim.account;

import com.example.cryptosim.converters.AccountConverter;
import org.springframework.stereotype.Component;
import utills.model.Account;

import java.util.UUID;

@Component
public class AccountService implements Service {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    public AccountService(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    @Override
    public boolean updateBalance(double amount) {
        return accountRepository.updateBalance(amount);
    }

    @Override
    public double getBalance() {
        return accountRepository.getBalance();
    }

    @Override
    public boolean resetAccount() {
        return accountRepository.resetAccount();
    }

    @Override
    public Account getAccount(UUID id) {
        return accountConverter.convertToUser(accountRepository.getAccount(id));
    }
}
