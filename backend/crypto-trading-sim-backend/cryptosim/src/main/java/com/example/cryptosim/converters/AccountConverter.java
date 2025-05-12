package com.example.cryptosim.converters;

import com.example.cryptosim.entity.AccountEntity;
import utills.model.Account;

public class AccountConverter implements IAccountConverter {
    private IUUIDConverter uuidConverter;

    public AccountConverter(IUUIDConverter uuidConverter) {
        this.uuidConverter = uuidConverter;
    }

    @Override
    public Account convertToUser(AccountEntity entity) {
        return new Account(
                entity.getId().toString(),
                entity.getBalance(),
                entity.getEmail()
        );
    }

    @Override
    public AccountEntity convertToEntity(Account account) {
        return new AccountEntity(
                uuidConverter.convertFromString(account.getId()),
                account.getBalance(),
                account.getEmail()
        );
    }
}
